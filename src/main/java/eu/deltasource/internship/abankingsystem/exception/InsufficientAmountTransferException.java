package eu.deltasource.internship.abankingsystem.exception;

import eu.deltasource.internship.abankingsystem.enums.MessageTemplate;

public class InsufficientAmountTransferException extends RuntimeException {
    public InsufficientAmountTransferException (String message) {
        super(MessageTemplate.INSUFFICIENT_AMOUNT_TO_TRANSFER.getMessageTemplate() + message);
    }
}
