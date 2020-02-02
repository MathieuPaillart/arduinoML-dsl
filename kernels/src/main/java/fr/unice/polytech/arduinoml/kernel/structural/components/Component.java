package fr.unice.polytech.arduinoml.kernel.structural.components;

import fr.unice.polytech.arduinoml.kernel.structural.NamedElement;
import fr.unice.polytech.arduinoml.kernel.generator.Visitable;
import lombok.Data;

/**
 * Representation of an arduino component.
 */
@Data
public abstract class Component implements Visitable, NamedElement {

	/**
	 * Element's name.
	 */
	protected String name;

}