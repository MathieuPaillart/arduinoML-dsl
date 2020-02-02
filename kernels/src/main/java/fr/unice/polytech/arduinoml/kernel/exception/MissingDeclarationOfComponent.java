package fr.unice.polytech.arduinoml.kernel.exception;

/**
 * Exception from component not declared.
 */
public class MissingDeclarationOfComponent extends RuntimeException {
    public MissingDeclarationOfComponent(String s) {
		super(s);
    }
}
