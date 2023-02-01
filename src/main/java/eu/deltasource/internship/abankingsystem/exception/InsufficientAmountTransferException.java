package eu.deltasource.internship.abankingsystem.exception;

public class InsufficientAmountTransferException extends RuntimeException{
    public InsufficientAmountTransferException (String str) {
        super(str);
    }
}
