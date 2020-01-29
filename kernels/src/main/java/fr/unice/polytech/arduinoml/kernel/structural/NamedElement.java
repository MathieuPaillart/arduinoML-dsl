package fr.unice.polytech.arduinoml.kernel.structural;

/**
 * Abstraction of a NamedElement into an Arduino application.
 */
public interface NamedElement {

	/**
	 * Name getter.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Name setter.
	 *
	 * @param name the name
	 */
	void setName(String name);

}
