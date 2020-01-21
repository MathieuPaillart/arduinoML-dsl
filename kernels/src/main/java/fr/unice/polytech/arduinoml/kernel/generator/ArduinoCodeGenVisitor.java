package fr.unice.polytech.arduinoml.kernel.generator;

import fr.unice.polytech.arduinoml.kernel.App;
import fr.unice.polytech.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.arduinoml.kernel.structural.Actuator;
import fr.unice.polytech.arduinoml.kernel.structural.Component;
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
		writeCode(String.format("// Application name: %s\n", app.getName()));

		writeCode("void setup(){");
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
	public void visitAction(Action action) {
		writeCode(String.format("  digitalWrite(%d,%s);", action.getActuator().getPin(), action.getValue()));
	}

}
