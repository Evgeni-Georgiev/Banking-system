package eu.deltasource.internship.abankingsystem.exception;

public class InvalidIbanException extends RuntimeException {

    public InvalidIbanException (String message) {
        super(message);
    }
}
