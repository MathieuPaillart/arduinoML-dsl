package fr.unice.polytech.arduinoml.kernel.structural.components.simple;

import fr.unice.polytech.arduinoml.kernel.structural.components.Component;
import lombok.Data;

/**
 * Representation of an arduino assignableComponent that uses only one pin.
 */
@Data
public abstract class SimpleComponent extends Component {

	/**
	 * Component pin.
	 */
	private int pin;

}
