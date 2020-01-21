package fr.unice.polytech.arduinoml.kernel.structural;

import fr.unice.polytech.arduinoml.kernel.NamedElement;
import fr.unice.polytech.arduinoml.kernel.generator.Visitable;
import lombok.Data;

/**
 * Representation of an arduino component.
 */
@Data
public abstract class Component extends NamedElement implements Visitable {

	/**
	 * Component pin.
	 */
	private int pin;

}