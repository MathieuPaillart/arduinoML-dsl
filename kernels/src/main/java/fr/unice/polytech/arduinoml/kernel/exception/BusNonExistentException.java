package fr.unice.polytech.arduinoml.kernel.exception;

/**
 * Bus non existent exception.
 */
public class BusNonExistentException extends RuntimeException {
    public BusNonExistentException(String s) {
        super(s);
    }
}
