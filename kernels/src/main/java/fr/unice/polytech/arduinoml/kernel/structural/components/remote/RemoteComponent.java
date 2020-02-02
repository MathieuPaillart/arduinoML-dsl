package fr.unice.polytech.arduinoml.kernel.structural.components.remote;

import fr.unice.polytech.arduinoml.kernel.structural.NamedElement;
import fr.unice.polytech.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.arduinoml.kernel.structural.components.Component;
import lombok.Data;

/**
 * Representation of an arduino remote component.
 */
@Data
public abstract class RemoteComponent extends Component implements Visitable, NamedElement {

	/**
	 * Value high.
	 */
	protected String valueHigh;

	/**
	 * Value low.
	 */
	protected String valueLow;
}
