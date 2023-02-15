package eu.deltasource.internship.abankingsystem.enums;

/**
 * Store all types of bank operations available for banks.
 */
public enum TransactionType {
    TRANSFER("Transfer"),

    DEPOSIT("Deposit"),

    WITHDRAW("Withdraw");

    final String transactionType;

    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

}
