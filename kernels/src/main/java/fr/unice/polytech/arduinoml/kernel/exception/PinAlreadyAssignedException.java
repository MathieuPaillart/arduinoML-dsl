package fr.unice.polytech.arduinoml.kernel.exception;

public class PinAlreadyAssignedException extends RuntimeException {
    public PinAlreadyAssignedException(String s) {
        super(s);
    }
}
