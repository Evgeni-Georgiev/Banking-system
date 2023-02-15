package eu.deltasource.internship.abankingsystem.exception;

import eu.deltasource.internship.abankingsystem.enums.MessageTemplate;

/**
 * Case: If try to transfer to another account amount that is higher than the amount in the source account, this exception will throw.
 */
public class InsufficientAmountTransferException extends RuntimeException {

    public InsufficientAmountTransferException (String message) {
        super(MessageTemplate.INSUFFICIENT_AMOUNT_TO_TRANSFER.getMessageTemplate() + message);
    }
}
