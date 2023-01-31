package eu.deltasource.internship.abankingsystem.exception;

public class InsufficientAmountTransferException extends Exception{
    public InsufficientAmountTransferException (String str) {
        super(str);
    }
}
