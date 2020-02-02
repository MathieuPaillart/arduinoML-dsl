package fr.unice.polytech.arduinoml.kernel.exception;

/**
 * Exception when the user declare several keyboards.
 */
public class OnlyOneKeyboardException extends RuntimeException {
    public OnlyOneKeyboardException(String s) {
        super(s);
    }
}
