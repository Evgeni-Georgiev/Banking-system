package eu.deltasource.internship.abankingsystem.exception;

import eu.deltasource.internship.abankingsystem.enums.MessageTemplate;

/**
 * Case: If try to transfer amount from one current to ane savings account or vice versa, this exception will throw.
 */
public class TransferBetweenNotCurrentAccountsException extends RuntimeException {
    public TransferBetweenNotCurrentAccountsException (String message) {
        super(MessageTemplate.ACCOUNTS_NOT_CURRENT.getMessageTemplate() + message);
    }
}
