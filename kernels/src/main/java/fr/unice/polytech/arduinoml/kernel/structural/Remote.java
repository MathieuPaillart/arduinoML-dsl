package fr.unice.polytech.arduinoml.kernel.structural;

import fr.unice.polytech.arduinoml.kernel.NamedElement;
import fr.unice.polytech.arduinoml.kernel.generator.Visitable;
import lombok.Data;

@Data
public abstract class Remote extends NamedElement implements Visitable {

	/**
	 * Value high.
	 */
	protected String valueHigh;

	/**
	 * Value low.
	 */
	protected String valueLow;
}
