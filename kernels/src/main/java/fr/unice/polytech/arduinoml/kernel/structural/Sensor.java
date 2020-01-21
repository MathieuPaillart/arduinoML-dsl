package fr.unice.polytech.arduinoml.kernel.structural;

import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;

/**
 * Arduino sensor.
 */
public class Sensor extends Component {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(CodeGenVisitor codeGenVisitor) {
		codeGenVisitor.visitSensor(this);
	}
}
