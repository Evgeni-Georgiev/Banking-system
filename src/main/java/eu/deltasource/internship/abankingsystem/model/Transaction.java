package eu.deltasource.internship.abankingsystem.model;

import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.enums.TransactionType;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Create certain type of transaction(transfer, deposit, withdraw).
 */
public class Transaction {

    private int id;

    private final String sourceAccountIban;

    private final String targetAccountIban;

    private final BankInstitution sourceBank;

    private final BankInstitution targetBank;

    private final double amount;

    private final Currency sourceCurrency;

    private final Currency targetCurrency;

    private TransactionType transactionType;

    private final LocalDate timestamp;

    private Transaction(TransactionBuilder transactionBuilder) {
        this.id = transactionBuilder.id;
        this.sourceAccountIban = transactionBuilder.sourceAccountIban;
        this.targetAccountIban = transactionBuilder.targetAccountIban;
        this.sourceBank = transactionBuilder.sourceBank;
        this.targetBank = transactionBuilder.targetBank;
        this.amount = transactionBuilder.amount;
        this.sourceCurrency = transactionBuilder.sourceCurrency;
        this.targetCurrency = transactionBuilder.targetCurrency;
        this.timestamp = transactionBuilder.timestamp;
    }

    public String getSourceAccountIban() {
        return sourceAccountIban;
    }

    public String getTargetAccountIban() {
        return targetAccountIban;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public String operationsStringOutput() {
        if (Objects.equals(getTransactionType(), TransactionType.DEPOSIT) || Objects.equals(getTransactionType(), TransactionType.WITHDRAW)) {
            return String.format(
                """
                     
                     Source account: %s(%s)
                     Transferred amount: (%s)%,.2f
                     Source currency: %s
                     Transaction type: %s
                     Timestamp: %s %n""",
                sourceAccountIban,
                getTargetBank().getName(),
                getSourceCurrency(),
                getAmount(),
                getSourceCurrency(),
                getTransactionType(),
                getTimestamp());
        } else {
            return String.format(
                """
                     
                     Source account: %s(%s)
                     Target account: %s(%s)
                     Transferred amount: (%s)%,.2f
                     Source currency: %s
                     Target currency: %s
                     Transaction type: %s
                     Timestamp: %s
                     """,
                sourceAccountIban,
                getSourceBank().getName(),
                targetAccountIban,
                getTargetBank().getName(),
                getSourceCurrency(),
                getAmount(),
                getSourceCurrency(),
                getTargetCurrency(),
                getTransactionType(),
                getTimestamp());
        }
    }

    public static class TransactionBuilder {

        private int id;

        private final String sourceAccountIban;

        private String targetAccountIban;

        private final BankInstitution sourceBank;

        private BankInstitution targetBank;

        private final double amount;

        private final Currency sourceCurrency;

        private Currency targetCurrency;

        private final LocalDate timestamp;

        public TransactionBuilder(String sourceAccountIban, BankInstitution sourceBank, double amount, Currency sourceCurrency, LocalDate timestamp) {
            this.sourceAccountIban = sourceAccountIban;
            this.sourceBank = sourceBank;
            this.amount = amount;
            this.sourceCurrency = sourceCurrency;
            this.timestamp = timestamp;
        }

        public TransactionBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public TransactionBuilder setTargetAccount(String targetAccountIban) {
            this.targetAccountIban = targetAccountIban;
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
