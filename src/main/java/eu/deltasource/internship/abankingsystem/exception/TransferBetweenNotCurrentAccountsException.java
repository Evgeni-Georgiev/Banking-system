package eu.deltasource.internship.abankingsystem.exception;

import eu.deltasource.internship.abankingsystem.enums.MessageTemplate;

public class TransferBetweenNotCurrentAccountsException extends RuntimeException {
    public TransferBetweenNotCurrentAccountsException (String message) {
        super(MessageTemplate.ACCOUNTS_NOT_CURRENT.getMessageTemplate() + message);
    }
}
