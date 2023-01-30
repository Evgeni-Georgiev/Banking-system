package eu.deltasource.internship.abankingsystem.exception;

public class InvalidIbanException extends Exception {
    public InvalidIbanException (String str) {
        // calling the constructor of parent Exception
        super(str);
    }
}
