package eu.deltasource.internship.abankingsystem.exception;

import eu.deltasource.internship.abankingsystem.enums.MessageTemplate;

public class InsufficientAmountWithdrawException extends RuntimeException {
    public InsufficientAmountWithdrawException (String message) {
        super(MessageTemplate.INSUFFICIENT_AMOUNT_TO_WITHDRAW.getMessageTemplate() + message);
    }
}
