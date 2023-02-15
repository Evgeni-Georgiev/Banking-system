package eu.deltasource.internship.abankingsystem.exception;

import eu.deltasource.internship.abankingsystem.enums.MessageTemplate;

/**
 * Case: If try to withdraw from bank account amount that is higher than the balance of the account, this exception will throw.
 */
public class InsufficientAmountWithdrawException extends RuntimeException {
    public InsufficientAmountWithdrawException (String message) {
        super(MessageTemplate.INSUFFICIENT_AMOUNT_TO_WITHDRAW.getMessageTemplate() + message);
    }
}
