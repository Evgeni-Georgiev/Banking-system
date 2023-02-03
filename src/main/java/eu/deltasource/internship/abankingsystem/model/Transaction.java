package eu.deltasource.internship.abankingsystem.model;

import eu.deltasource.internship.abankingsystem.enums.Currency;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {

    private int id;

    private final BankAccount sourceAccount;

    private final BankAccount targetAccount;

    private final BankInstitution sourceBank;

    private final BankInstitution targetBank;

    private final double amount;

    private final Currency sourceCurrency;

    private final Currency targetCurrency;

    private String transactionType;

    private final LocalDate timestamp;

    private Transaction(TransactionBuilder transactionBuilder) {
        this.id = transactionBuilder.id;
        this.sourceAccount = transactionBuilder.sourceAccount;
        this.targetAccount = transactionBuilder.targetAccount;
        this.sourceBank = transactionBuilder.sourceBank;
        this.targetBank = transactionBuilder.targetBank;
        this.amount = transactionBuilder.amount;
        this.sourceCurrency = transactionBuilder.sourceCurrency;
        this.targetCurrency = transactionBuilder.targetCurrency;
        this.timestamp = transactionBuilder.timestamp;
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

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public String operationsStringOutput() {
        if (Objects.equals(getTransactionType(), "Deposit") || Objects.equals(getTransactionType(), "Withdraw")) {
            return String.format(
                """
                    %n Source account: %s(%s)
                     Transferred amount: (%s)%,.2f
                     Source currency: %s
                     Transaction type: %s
                     Timestamp: %s %n""",
                sourceAccount.getIban(),
                getTargetBank().getName(),
                getSourceCurrency(),
                getAmount(),
                getSourceCurrency(),
                getTransactionType(),
                getTimestamp());
        } else {
            return String.format(
                """
                    %n Source account: %s
                     Target account: %s
                     Transferred amount: (%s)%,.2f
                     Source currency: %s
                     Target currency: %s
                     Transaction type: %s
                     Timestamp: %s %n""",
                sourceAccount.getIban(),
                targetAccount.getIban(),
                getSourceCurrency(),
                getAmount(),
                getSourceCurrency(),
                getTargetCurrency(),
                getTransactionType(),
                getTimestamp());
        }
    }

    public static class TransactionBuilder {

        private final BankAccount sourceAccount;

        private int id;

        private BankAccount targetAccount;

        private final BankInstitution sourceBank;

        private BankInstitution targetBank;

        private final double amount;

        private final Currency sourceCurrency;

        private Currency targetCurrency;

        private String transactionType;

        private final LocalDate timestamp;

        public TransactionBuilder(BankAccount sourceAccount, BankInstitution sourceBank, double amount, Currency sourceCurrency, LocalDate timestamp) {
            this.sourceAccount = sourceAccount;
            this.sourceBank = sourceBank;
            this.amount = amount;
            this.sourceCurrency = sourceCurrency;
            this.timestamp = timestamp;
        }

        public TransactionBuilder setTargetAccount(BankAccount targetAccount) {
            this.targetAccount = targetAccount;
            return this;
        }

        public TransactionBuilder setId(int id)
        {
            this.id = id;
            return this;
        }

        public TransactionBuilder setTargetBank(BankInstitution bank) {
            this.targetBank = bank;
            return this;
        }

        public TransactionBuilder setTargetCurrency(Currency currency) {
            this.targetCurrency = currency;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }

    @Override
    public String toString() {
        return operationsStringOutput();
    }

}
