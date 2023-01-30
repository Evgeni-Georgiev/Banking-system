package eu.deltasource.internship.abankingsystem;

import eu.deltasource.internship.abankingsystem.service.BankService;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {

    private BankService transactionService = new BankService();

    private BankAccount sourceAccount;

    private BankAccount targetAccount;

    private String sourceIban;

    private String targetIban;

    private BankInstitution sourceBank;

    private BankInstitution targetBank;

    private double amount;

    private String sourceCurrency;

    private String targetCurrency;

    private String exchangeRate;

    private String TransactionType;

    private final LocalDate timestamp;

//    public Transaction(String sourceIban, String targetIban, BankInstitution sourceBank, BankInstitution targetBank, double amount, String sourceCurrency, String targetCurrency, String timestamp) {
//        this.sourceIban = sourceIban;
//        this.targetIban = targetIban;
//        this.sourceBank = sourceBank;
//        this.targetBank = targetBank;
//        this.amount = amount;
//        this.sourceCurrency = sourceCurrency;
//        this.targetCurrency = targetCurrency;
////        this.exchangeRate = exchangeRate;
//        this.timestamp = timestamp;
//    }
//
//    public Transaction(String sourceIban, BankInstitution sourceBank, double amount, String sourceCurrency, String timestamp) {
//        this.sourceIban = sourceIban;
//        this.sourceBank = sourceBank;
//        this.amount = amount;
//        this.sourceCurrency = sourceCurrency;
//        this.timestamp = timestamp;
//    }

    public Transaction(BankAccount sourceAccount, BankAccount targetAccount, BankInstitution sourceBank, BankInstitution targetBank, double amount, String sourceCurrency, String targetCurrency, LocalDate timestamp) {
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.sourceBank = sourceBank;
        this.targetBank = targetBank;
        this.amount = amount;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
//        this.exchangeRate = exchangeRate;
        this.timestamp = timestamp;
    }

    public Transaction(BankAccount sourceAccount, BankInstitution sourceBank, double amount, String sourceCurrency, LocalDate timestamp) {
        this.sourceAccount = sourceAccount;
        this.sourceBank = sourceBank;
        this.amount = amount;
        this.sourceCurrency = sourceCurrency;
        this.timestamp = timestamp;
    }

    public BankAccount getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(BankAccount sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public BankAccount getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(BankAccount targetAccount) {
        this.targetAccount = targetAccount;
    }


    public String getSourceIban() {
        return sourceIban;
    }

    public String getTargetIban() {
        return targetIban;
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

    public String getExchangeRate() {
        return exchangeRate;
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

//    public void printTransferStatementsForAccount(String iban) {
//        for (Transaction transaction : bankAccount) {
//            if (transaction.getSourceIban() || transaction.getTargetIban())) {
//                System.out.println("Source account: " + transaction.getSourceAccount().getIban());
//                System.out.println("Target account: " + transaction.getTargetAccount().getIban());
//                System.out.println("Amount: " + transaction.getAmount() + " " + transaction.getSourceCurrency());
//                System.out.println("Exchange rate: " + transaction.getExchangeRate());
//                System.out.println("Timestamp: " + transaction.getTimestamp());
//                System.out.println("-----------------------------");
//            }
//        }
//    }

    @Override
    public String toString() {
        // Can use StringBuilder

        if(Objects.equals(getTransactionType(), "Deposit") || Objects.equals(getTransactionType(), "Withdraw")) {
            return String.format(
                "%n Source account: %s(%s) %n " +
                    "Transferred amount: (%s)%,.2f %n Source currency: %s %n " +
                    "Transaction type: %s %n " +
                    "Timestamp: %s %n",
                sourceAccount.getIban(),
                getTargetBank().getName(),
                getSourceCurrency(),
                getAmount(),
                getSourceCurrency(),
                getTransactionType(),
                getTimestamp());
        } else {
            return String.format(
                "%n Source account: %s(%s) %n Target account: %s(%s) %n " +
                    "Transferred amount: (%s)%,.2f %n Source currency: %s %n " +
                    "Target currency: %s %n " +
                    "Transaction type: %s %n " +
                    "Timestamp: %s %n",
                sourceAccount.getIban(),
                sourceAccount.getBankInstitution().getName(),
                targetAccount.getIban(),
                targetAccount.getBankInstitution().getName(),
                getSourceCurrency(),
                getAmount(),
                getSourceCurrency(),
                getTargetCurrency(),
                getTransactionType(),
                getTimestamp());
        }
    }

}
