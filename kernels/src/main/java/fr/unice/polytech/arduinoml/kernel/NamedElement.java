package fr.unice.polytech.arduinoml.kernel;

import lombok.Data;

/**
 * Abstraction of a NamedElement into an Arduino application.
 */
@Data
public abstract class NamedElement {

	/**
	 * Element's name.
	 */
	protected String name;

}
