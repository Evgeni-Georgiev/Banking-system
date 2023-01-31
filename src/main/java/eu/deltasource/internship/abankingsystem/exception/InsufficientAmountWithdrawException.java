package eu.deltasource.internship.abankingsystem.exception;

public class InsufficientAmountWithdrawException extends Exception{
    public InsufficientAmountWithdrawException (String str) {
        super(str);
    }
}
