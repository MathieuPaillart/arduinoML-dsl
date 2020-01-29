package fr.unice.polytech.arduinoml.kernel.structural.components.simple;

import fr.unice.polytech.arduinoml.kernel.generator.core.CoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.SetupCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.structural.Assignable;

/**
 * An Arduino actuator.
 */
public class Actuator extends SimpleComponent implements Assignable {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptImportGen(ImportCodeGenVisitor codeGenVisitor) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptSetupGen(SetupCodeGenVisitor codeGenVisitor) {
		codeGenVisitor.visitActuator(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptCoreGen(CoreCodeGenVisitor codeGenVisitor) {

	}
}
