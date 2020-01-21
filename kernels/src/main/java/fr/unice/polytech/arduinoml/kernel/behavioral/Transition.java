package fr.unice.polytech.arduinoml.kernel.behavioral;

import fr.unice.polytech.arduinoml.kernel.structural.SIGNAL;
import fr.unice.polytech.arduinoml.kernel.structural.Sensor;
import fr.unice.polytech.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;
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
	public void accept(CodeGenVisitor codeGenVisitor) {
		codeGenVisitor.visitTransition(this);
	}
}
