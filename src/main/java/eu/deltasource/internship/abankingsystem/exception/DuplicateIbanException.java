package eu.deltasource.internship.abankingsystem.exception;

public class DuplicateIbanException extends RuntimeException {

    public DuplicateIbanException(String message) {
        super(message);
    }
}
