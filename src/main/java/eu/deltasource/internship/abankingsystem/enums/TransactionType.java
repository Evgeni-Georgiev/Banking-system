package eu.deltasource.internship.abankingsystem.enums;

public enum TransactionType {
    TRANSFER("Transfer"),

    DEPOSIT("Deposit"),

    WITHDRAW("Withdraw");

    final String transactionType;

    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

}
