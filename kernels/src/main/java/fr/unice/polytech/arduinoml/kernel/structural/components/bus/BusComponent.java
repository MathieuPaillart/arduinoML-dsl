package fr.unice.polytech.arduinoml.kernel.structural.components.bus;

import fr.unice.polytech.arduinoml.kernel.structural.components.Component;
import lombok.Data;

import java.util.List;

/**
 * Representation of an arduino assignableComponent that uses a bus (ie, a list of pins).
 */
@Data
public abstract class BusComponent extends Component {

	/**
	 * Component pin.
	 */
	protected List<Integer> pins;

}
