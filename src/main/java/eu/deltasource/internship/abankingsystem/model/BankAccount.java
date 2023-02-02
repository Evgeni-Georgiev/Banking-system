package eu.deltasource.internship.abankingsystem.model;

import eu.deltasource.internship.abankingsystem.enums.Currency;

import java.time.LocalDate;
import java.util.*;

public abstract class BankAccount {

    private final Owner owner;

    private final String iban;

    private final Currency currency;

    private Double amountAvailable;

    private final char accountKey;

    private LinkedList<Transaction> transferStatement;

//    private final List<BankAccount> ownerList = new ArrayList<>();

    private static final ArrayList<BankAccount> accounts = new ArrayList<>();

    private static final List<String> existingIbans = new ArrayList<>();

//    public List<BankAccount> getOwnerList() {
//        return Collections.unmodifiableList(ownerList);
//    }

    public BankAccount(Owner owner, String iban, Currency currency, double amountAvailable, char accountKey) {
        if (existingIbans.contains(iban)) {
            throw new IllegalArgumentException("IBAN already exists");
        }
        this.owner = owner;
        this.iban = normalizeIban(iban);
        this.currency = currency;
        this.amountAvailable = amountAvailable;
        this.accountKey = normalizeAccountKey(accountKey);
        this.transferStatement = new LinkedList<>();
        accounts.add(this);
        existingIbans.add(iban);
    }

    public static void countOfAccountOwnerHas(Owner owner) {
        List<BankAccount> newListOwner = new ArrayList<>();
        for(var singleAccount : accounts) {
            if(singleAccount.getOwner().getName().equals(owner.getName())) {
                newListOwner.add(singleAccount);
            }
        }
        System.out.println(newListOwner);
    }

    public Owner getOwner() {
        return owner;
    }

    public String getIban() {
        return iban;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Double getAmountAvailable() {
        return amountAvailable;
    }

    protected abstract String getAccountType();


    public char getAccountKey() {
        return accountKey;
    }
    private char normalizeAccountKey(final char accountKey) {
        return Character.toUpperCase(accountKey);
    }


//    public static List<BankAccount> getAccounts() {
//        return Collections.unmodifiableList(accounts);
//    }

    private String normalizeIban(final String Iban) {
        return null != Iban ? Iban.toUpperCase() : null;
    }

    public void setAmountAvailable(double amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public LinkedList<Transaction> getTransferStatement() {
        return transferStatement;
    }

    public void setTransferStatement(LinkedList<Transaction> transferStatement) {
        this.transferStatement = transferStatement;
    }

    public void addTransaction(Transaction transaction) {
        transferStatement.add(transaction);
    }

    public List<Transaction> getTransferStatementLocal(LocalDate startDate, LocalDate endDate) {
        LinkedList<Transaction> statements = new LinkedList<>();
        for(Transaction transaction : transferStatement) {
            LocalDate datesFromTransaction = transaction.getTimestamp();
            if (!datesFromTransaction.isBefore(startDate) && !datesFromTransaction.isAfter(endDate)) {
                statements.add(transaction);
            }
        }
        return Collections.unmodifiableList(statements);
    }

    @Override
    public String toString() {
        return String.format("%nBank Account Details: %n " +
                "Owner: %s %n " +
                "IBAN: %s %n " +
                "Currency: %s %n " +
                "Amount Available: %s %n " +
                "Account Key: %s %n " +
                "Account Type: %s %n " +
                "All Transactions for this account: %s %n",
                getOwner().getName(),
            getIban(),
            getCurrency(),
            getAmountAvailable(),
            getAccountKey(),
            getAccountType(),
            transferStatement
        );
    }
}
