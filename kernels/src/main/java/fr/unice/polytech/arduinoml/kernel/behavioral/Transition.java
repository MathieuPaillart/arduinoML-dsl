package fr.unice.polytech.arduinoml.kernel.behavioral;

import fr.unice.polytech.arduinoml.kernel.generator.core.CoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.SetupCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.structural.SIGNAL;
import fr.unice.polytech.arduinoml.kernel.structural.components.simple.Sensor;
import fr.unice.polytech.arduinoml.kernel.generator.Visitable;
import lombok.Data;

/**
 * Transition between arduino's fsm states.
 */
@Data
public class Transition implements Visitable {

	/**
	 * Next state.
	 */
	private State next;

	/**
	 * Sensor in the condition.
	 */
	private Sensor sensor;

	/**
	 * Condition's value.
	 */
	private SIGNAL value;

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

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptCoreGen(CoreCodeGenVisitor codeGenVisitor) {
		codeGenVisitor.visitTransition(this);
	}
}
