package eu.deltasource.internship.abankingsystem.exception;

public class TransferBetweenNotCurrentAccountsException extends Exception{
    public TransferBetweenNotCurrentAccountsException (String str) {
        super(str);
    }
}
