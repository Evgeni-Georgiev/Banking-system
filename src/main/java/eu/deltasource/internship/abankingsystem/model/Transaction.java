package eu.deltasource.internship.abankingsystem.model;

import eu.deltasource.internship.abankingsystem.enums.Currency;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {

    private final BankAccount sourceAccount;

    private BankAccount targetAccount;

    private final BankInstitution sourceBank;

    private BankInstitution targetBank;

    private final double amount;

    private final Currency sourceCurrency;

    private Currency targetCurrency;

    private String transactionType; // Should be an enum

    private final LocalDate timestamp;

    public Transaction(BankAccount sourceAccount, BankAccount targetAccount, BankInstitution sourceBank, BankInstitution targetBank, double amount, Currency sourceCurrency, Currency targetCurrency, LocalDate timestamp) {
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.sourceBank = sourceBank;
        this.targetBank = targetBank;
        this.amount = amount;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.timestamp = timestamp;
    }

    public Transaction(BankAccount sourceAccount, BankInstitution sourceBank, double amount, Currency sourceCurrency, LocalDate timestamp) {
        this.sourceAccount = sourceAccount;
        this.sourceBank = sourceBank;
        this.amount = amount;
        this.sourceCurrency = sourceCurrency;
        this.timestamp = timestamp;
    }

    public BankInstitution getSourceBank() {
        return sourceBank;
    }

    public BankInstitution getTargetBank() {
        return targetBank == null ? getSourceBank() : targetBank;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public String operationsStringOutput() {
        if(Objects.equals(getTransactionType(), "Deposit") || Objects.equals(getTransactionType(), "Withdraw")) {
            return String.format(
                "%n  Source account: %s(%s) %n " +
                    " Transferred amount: (%s)%,.2f %n  Source currency: %s %n " +
                    " Transaction type: %s %n " +
                    " Timestamp: %s %n",
                sourceAccount.getIban(),
                getTargetBank().getName(),
                getSourceCurrency(),
                getAmount(),
                getSourceCurrency(),
                getTransactionType(),
                getTimestamp());
        } else {
            return String.format(
                "%n  Source account: %s %n  Target account: %s %n " +
                    " Transferred amount: (%s)%,.2f %n  Source currency: %s %n " +
                    " Target currency: %s %n " +
                    " Transaction type: %s %n " +
                    " Timestamp: %s %n",
                sourceAccount.getIban(),
//                bankAccountToBankMapping.getBank(sourceAccount).getName(),
                targetAccount.getIban(),
//                bankAccountToBankMapping.getBank(sourceAccount).getName(),
                getSourceCurrency(),
                getAmount(),
                getSourceCurrency(),
                getTargetCurrency(),
                getTransactionType(),
                getTimestamp());
        }
    }

    @Override
    public String toString() {
        return operationsStringOutput();
    }

}
