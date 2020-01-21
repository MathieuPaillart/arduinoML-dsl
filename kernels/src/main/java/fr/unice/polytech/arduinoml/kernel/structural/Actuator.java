package fr.unice.polytech.arduinoml.kernel.structural;

import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;

/**
 * An Arduino actuator.
 */
public class Actuator extends Component {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(CodeGenVisitor codeGenVisitor) {
		codeGenVisitor.visitActuator(this);
	}
}
