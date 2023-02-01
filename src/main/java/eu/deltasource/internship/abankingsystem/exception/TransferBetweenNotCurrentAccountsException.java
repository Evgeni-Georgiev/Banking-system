package eu.deltasource.internship.abankingsystem.exception;

public class TransferBetweenNotCurrentAccountsException extends RuntimeException{
    public TransferBetweenNotCurrentAccountsException (String str) {
        super(str);
    }
}
