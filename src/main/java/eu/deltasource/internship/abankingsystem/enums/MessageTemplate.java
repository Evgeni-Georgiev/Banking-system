package eu.deltasource.internship.abankingsystem.enums;

public enum MessageTemplate {

    DUPLICATE_IBAN("Account with this IBAN already exists: "),

    INSUFFICIENT_AMOUNT_TO_TRANSFER("Insufficient amount to transfer: "),

    INSUFFICIENT_AMOUNT_TO_WITHDRAW("Insufficient amount to withdraw: "),

    ACCOUNTS_NOT_CURRENT("Both accounts must be current: ");

    final String messageTemplate;

    MessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }
}
