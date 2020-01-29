package fr.unice.polytech.arduinoml.kernel.generator.setup;

import fr.unice.polytech.arduinoml.kernel.App;
import fr.unice.polytech.arduinoml.kernel.structural.components.simple.Actuator;
import fr.unice.polytech.arduinoml.kernel.structural.components.Component;
import fr.unice.polytech.arduinoml.kernel.structural.components.bus.LCD;
import fr.unice.polytech.arduinoml.kernel.structural.components.simple.Sensor;
import fr.unice.polytech.arduinoml.kernel.structural.components.remote.Keyboard;

public class ArduinoSetupCodeGenVisitor extends SetupCodeGenVisitor<StringBuffer> {

	/**
	 * Default constructor.
	 */
	public ArduinoSetupCodeGenVisitor() {
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
		writeCode("void setup() {");
		for (final Component component : app.getComponents()) {
			component.acceptSetupGen(this);
		}
		writeCode("}\n");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visitLCD(final LCD lcd) {
		writeCode(String.format("  %s.begin(16, 2); // set up the LCD's number of columns and rows", lcd.getName()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visitKeyboard(Keyboard keyboard) {
		writeCode(String.format("  Serial.begin(9600);"));
		// set up the LCD's number of columns and rows:
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

}
