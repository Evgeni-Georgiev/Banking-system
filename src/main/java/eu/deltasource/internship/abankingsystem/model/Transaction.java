package eu.deltasource.internship.abankingsystem.model;

import eu.deltasource.internship.abankingsystem.BankAccountToBankMapping;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {

    private BankAccountToBankMapping bankAccountToBankMapping;

    private final BankAccount sourceAccount;

    private BankAccount targetAccount;

    private final BankInstitution sourceBank;

    private BankInstitution targetBank;

    private final double amount;

    private final String sourceCurrency;

    private String targetCurrency;

    private String TransactionType;

    private final LocalDate timestamp;

    public Transaction(BankAccount sourceAccount, BankAccount targetAccount, BankInstitution sourceBank, BankInstitution targetBank, double amount, String sourceCurrency, String targetCurrency, LocalDate timestamp) {
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.sourceBank = sourceBank;
        this.targetBank = targetBank;
        this.amount = amount;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.timestamp = timestamp;
    }

    public Transaction(BankAccount sourceAccount, BankInstitution sourceBank, double amount, String sourceCurrency, LocalDate timestamp) {
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

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        // Can use StringBuilder

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
                "%n  Source account: %s(%s) %n  Target account: %s(%s) %n " +
                    " Transferred amount: (%s)%,.2f %n  Source currency: %s %n " +
                    " Target currency: %s %n " +
                    " Transaction type: %s %n " +
                    " Timestamp: %s %n",
                sourceAccount.getIban(),
                bankAccountToBankMapping.getBank(sourceAccount).getName(),
                targetAccount.getIban(),
                bankAccountToBankMapping.getBank(sourceAccount).getName(),
                getSourceCurrency(),
                getAmount(),
                getSourceCurrency(),
                getTargetCurrency(),
                getTransactionType(),
                getTimestamp());
        }
    }

}
