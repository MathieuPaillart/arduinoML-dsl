package fr.unice.polytech.arduinomldsl.exception;

public class PinAlreadyAssignedException extends RuntimeException {
    public PinAlreadyAssignedException(String s) {
        super(s);
    }
}
