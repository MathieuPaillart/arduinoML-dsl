package fr.unice.polytech.arduinoml.kernel.generator.imports;

import fr.unice.polytech.arduinoml.kernel.App;
import fr.unice.polytech.arduinoml.kernel.structural.components.Component;
import fr.unice.polytech.arduinoml.kernel.structural.components.bus.LCD;

import java.util.stream.Collectors;

/**
 * Visitor to support the generation of the import in Arduino C code.
 */
public class ArduinoImportCodeGenVisitor extends ImportCodeGenVisitor<StringBuffer> {

	/**
	 * Default constructor.
	 */
	public ArduinoImportCodeGenVisitor() {
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

		for(final Component component : app.getComponents()) {
			component.acceptImportGen(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visitLCD(LCD lcd) {
		writeCode("#include <LiquidCrystal.h>");

		String bus = lcd.getPins().stream().map(Object::toString).collect(Collectors.joining(","));
		writeCode(String.format("LiquidCrystal %s(%s);", lcd.getName(), bus));

		writeCode("", true);
	}
}
