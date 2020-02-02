package fr.unice.polytech.arduinoml.kernel.exception;

/**
 * Exception when component's pin collision.
 */
public class PinAlreadyAssignedException extends RuntimeException {
    public PinAlreadyAssignedException(String s) {
        super(s);
    }
}
