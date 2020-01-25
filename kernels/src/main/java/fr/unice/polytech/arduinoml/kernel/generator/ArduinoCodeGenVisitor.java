package fr.unice.polytech.arduinoml.kernel.generator;

import fr.unice.polytech.arduinoml.kernel.App;
import fr.unice.polytech.arduinoml.kernel.behavioral.*;
import fr.unice.polytech.arduinoml.kernel.structural.Actuator;
import fr.unice.polytech.arduinoml.kernel.structural.Component;
import fr.unice.polytech.arduinoml.kernel.structural.LCD;
import fr.unice.polytech.arduinoml.kernel.structural.Sensor;

/**
 * Quick and dirty visitor to support the generation of Arduino C code.
 */
public class ArduinoCodeGenVisitor extends CodeGenVisitor<StringBuffer> {

    /**
     * No condition flag.
     */
    private boolean noCondition = false;

    /**
     * Default constructor.
     */
    public ArduinoCodeGenVisitor() {
        this.result = new StringBuffer();
    }

    /**
     * Write the given code.
     *
     * @param code the code.
     */
    private void writeCode(final String code) {
        result.append(String.format("%s\n", code));
    }

    /**
     * Write the given code with a newline.
     *
     * @param code    the code.
     * @param newLine the newline boolean.
     */
    private void writeCode(final String code, final boolean newLine) {
        if (newLine) {
            writeCode(code);
        } else {
            result.append(String.format("%s", code));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitApp(final App app) {
        writeCode("// Wiring code generated from an ArduinoML model");
        writeCode(String.format("// Application name: %s%n", app.getName()));

        if (app.getComponents().stream().anyMatch(component -> component instanceof LCD)) {
            writeCode("#include <LiquidCrystal.h>");
            app.getComponents().stream()
                    .filter(component -> component instanceof LCD)
                    .forEach(lcd -> {
                        if (lcd.getPin() == 1) {
                            writeCode(String.format("LiquidCrystal %s(2, 3, 4, 5, 6, 7, 8);", lcd.getName()));
                        } else if (lcd.getPin() == 2) {
                            writeCode(String.format("LiquidCrystal %s(10, 11, 12, 13, 14, 15, 16);", lcd.getName()));
                        }
                    });
            writeCode("", true);
        }

        writeCode("void setup() {");
        for (Component component : app.getComponents()) {
            component.accept(this);
        }
        writeCode("}\n");

        writeCode("long time = 0; long debounce = 200;\n");

        for (State state : app.getStates()) {
            state.accept(this);
        }

        if (app.getInitial() != null) {
            writeCode("void loop() {");
            writeCode(String.format("  state_%s();", app.getInitial().getName()));
            writeCode("}");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitActuator(Actuator actuator) {
        writeCode(String.format("  pinMode(%d, OUTPUT); // %s [Actuator]", actuator.getPin(), actuator.getName()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitSensor(Sensor sensor) {
        writeCode(String.format("  pinMode(%d, INPUT);  // %s [Sensor]", sensor.getPin(), sensor.getName()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitState(State state) {
        writeCode(String.format("void state_%s() {", state.getName()));
        for (Action action : state.getActions()) {
            action.accept(this);
        }

        writeCode("  boolean guard = millis() - time > debounce;");
        writeCode("  ", false);
        for (Transition transition : state.getTransitions()) {
            transition.accept(this);
        }

        if (!noCondition) {
            if (!state.getTransitions().isEmpty()) {
                writeCode("{");
            }
            writeCode(String.format("    state_%s();", state.getName()));
            if (!state.getTransitions().isEmpty()) {
                writeCode("  }");
            }
        }
        writeCode("}\n");
    }

    @Override
    public void visit(LCD lcd) {
        writeCode(String.format("  %s.begin(16, 2); // set up the LCD's number of columns and rows", lcd.getName()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitTransition(Transition transition) {
        if (transition.getSensor() == null || transition.getValue() == null) {
            writeCode(String.format("state_%s();", transition.getNext().getName()));
            noCondition = true;
        } else {
            writeCode(String.format("if( digitalRead(%d) == %s && guard ) {", transition.getSensor().getPin(),
                    transition.getValue()));
            writeCode("    time = millis();");
            writeCode(String.format("    state_%s();", transition.getNext().getName()));
            writeCode("  } else ", false);
            noCondition = false;
        }
    }

    @Override
    public void visit(ActionNumericAssignment actionNumericAssignment) {
        writeCode(String.format("  digitalWrite(%d,%s);", actionNumericAssignment.getComponent().getPin(), actionNumericAssignment.getValue()));
    }

    @Override
    public void visit(ActionLcd actionLcd) {
        Object value;
        if (isNumeric(actionLcd.getValue())) {
            value = String.format("digitalRead(%s)", Integer.parseInt(actionLcd.getValue()));
        } else {
            value = String.format("\"%s\"", actionLcd.getValue());
        }
        writeCode("  delay(30);");
        writeCode(String.format("  %s.setCursor(0, 0);", actionLcd.getComponent().getName()));
        writeCode(String.format("  %s.clear();", actionLcd.getComponent().getName()));
        writeCode(String.format("  %s.print(%s);", actionLcd.getComponent().getName(), value));
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

}
