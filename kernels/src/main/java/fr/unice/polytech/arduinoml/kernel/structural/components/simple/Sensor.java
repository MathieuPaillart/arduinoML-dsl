package fr.unice.polytech.arduinoml.kernel.structural.components.simple;

import fr.unice.polytech.arduinoml.kernel.generator.core.CoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.SetupCodeGenVisitor;

/**
 * Arduino sensor.
 */
public class Sensor extends SimpleComponent {

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
		codeGenVisitor.visitSensor(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptCoreGen(CoreCodeGenVisitor codeGenVisitor) {

	}
}
