package eu.deltasource.internship.abankingsystem.exception;

public class InsufficientAmountWithdrawException extends RuntimeException{
    public InsufficientAmountWithdrawException (String str) {
        super(str);
    }
}
