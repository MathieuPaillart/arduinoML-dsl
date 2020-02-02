package fr.unice.polytech.arduinoml.kernel.generator.core;

import fr.unice.polytech.arduinoml.kernel.App;
import fr.unice.polytech.arduinoml.kernel.behavioral.*;
import fr.unice.polytech.arduinoml.kernel.structural.*;
import fr.unice.polytech.arduinoml.kernel.structural.components.remote.Keyboard;
import fr.unice.polytech.arduinoml.kernel.structural.components.simple.SimpleComponent;

/**
 * Visitor to support the generation of the core Arduino C code.
 */
public class ArduinoCoreCodeGenVisitor extends CoreCodeGenVisitor<StringBuffer> {

    /**
     * No condition flag.
     */
    private boolean noCondition = false;

    /**
     * Default constructor.
     */
    public ArduinoCoreCodeGenVisitor() {
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

        writeCode("long time = 0; long debounce = 200;\n");

        for (State state : app.getStates()) {
            state.acceptCoreGen(this);
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
    public void visitActionRemote(ActionAssignmentFromRemote action) {
        if (action.getAssignableComponent() instanceof SimpleComponent) {
            writeCode("  while(true) {");
            writeCode("    if(Serial.available()>0) {");
            writeCode("      String in = Serial.readStringUntil('\\n');");
            writeCode(String.format("      if(in == \"%s\") {", action.getValue().getValueHigh()));
            writeCode(String.format("        digitalWrite(%d,%s);", ((SimpleComponent) action.getAssignableComponent()).getPin(), SIGNAL.HIGH));
            writeCode(String.format("      } else if (in == \"%s\") {", action.getValue().getValueLow()));
            writeCode(String.format("        digitalWrite(%d,%s);", ((SimpleComponent) action.getAssignableComponent()).getPin(), SIGNAL.LOW));
            writeCode("      }");
            writeCode("      break;");
            writeCode("    }");
            writeCode("  }");
        }
        //TODO: Exception
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitState(State state) {
        writeCode(String.format("void state_%s() {", state.getName()));
        for (Action action : state.getActions()) {
            action.acceptCoreGen(this);
        }

        // If there is no transition, no need to put the guard.
        if (!state.getTransitions().isEmpty()) {
            writeCode("  boolean guard = millis() - time > debounce;");
            writeCode("  ", false);
            for (Transition transition : state.getTransitions()) {
                transition.acceptCoreGen(this);
            }
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitActionNumeric(ActionAssignmentFromNumeric actionNumericAssignment) {
        if(actionNumericAssignment.getAssignableComponent() instanceof SimpleComponent) {
            writeCode(String.format("  digitalWrite(%d,%s);", ((SimpleComponent) actionNumericAssignment.getAssignableComponent()).getPin(), actionNumericAssignment.getValue()));
        }
        //TODO: Exception
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitActionDisplay(ActionDisplay actionLcd) {
        writeCode("  delay(30);");
        writeCode(String.format("  %s.setCursor(0, 0);", actionLcd.getAssignableComponent().getName()));
        writeCode(String.format("  %s.clear();", actionLcd.getAssignableComponent().getName()));

        // Switch on the value to retrieve the right output
        if (actionLcd.getValue() instanceof String) {
            writeCode(String.format("  %s.print(\"%s\");", actionLcd.getAssignableComponent().getName(), actionLcd.getValue()));
        } else if (actionLcd.getValue() instanceof SimpleComponent) {
            writeCode(String.format("  %s.print(digitalRead(%s));", actionLcd.getAssignableComponent().getName(), ((SimpleComponent) actionLcd.getValue()).getPin()));
        } else if (actionLcd.getValue() instanceof Keyboard) {
            writeCode(String.format("  %s.print(\"Waiting input ?\");", actionLcd.getAssignableComponent().getName()));
            writeCode("  while(true) {");
            writeCode("    if(Serial.available()>0) {");
            writeCode(String.format("      %s.setCursor(0, 0);", actionLcd.getAssignableComponent().getName()));
            writeCode(String.format("      %s.clear();", actionLcd.getAssignableComponent().getName()));
            writeCode(String.format("      %s.print(Serial.readStringUntil('\\n'));", actionLcd.getAssignableComponent().getName()));
            writeCode(String.format("      break;", actionLcd.getAssignableComponent().getName()));
            writeCode("    }");
            writeCode("  }");
        }
    }

}
