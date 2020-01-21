package fr.unice.polytech.arduinoml.kernel.behavioral;

import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.structural.SIGNAL;
import fr.unice.polytech.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.arduinoml.kernel.structural.Actuator;
import lombok.Data;

/**
 * Arduino action that modify an actuator value.
 */
@Data
public class Action implements Visitable {

	/**
	 * The signal.
	 */
	private SIGNAL value;

	/**
	 * The actuator
	 */
	private Actuator actuator;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(CodeGenVisitor codeGenVisitor) {
		codeGenVisitor.visitAction(this);
	}
}
