package eu.deltasource.internship.abankingsystem.enums;

/**
 * Store all default message templates used in Exceptions.
 */
public enum MessageTemplate {

    DUPLICATE_IBAN("Account with this IBAN already exists: "),

    INSUFFICIENT_AMOUNT_TO_TRANSFER("Insufficient amount to transfer: "),

    INSUFFICIENT_AMOUNT_TO_WITHDRAW("Insufficient amount to withdraw: "),

    ACCOUNTS_NOT_CURRENT("Both accounts must be current: "),

    BANK_INSTITUTION_DOES_NOT_EXIST("Requested bank institution does not exist: ");

    final String messageTemplate;

    MessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }
}
