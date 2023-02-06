package eu.deltasource.internship.abankingsystem.model;

import eu.deltasource.internship.abankingsystem.enums.Currency;

import static eu.deltasource.internship.abankingsystem.repository.bankAccountRepository.BankAccountRepositoryImpl.addAccountCount;

public abstract class BankAccount {

    private final Owner owner;

    private final String iban;

    private final Currency currency;

    private Double amountAvailable;

    private final char accountKey;

//    private List<Transaction> transferStatement;

//    private static final List<BankAccount> accounts = new ArrayList<>();

    public BankAccount(Owner owner, String iban, Currency currency, double amountAvailable, char accountKey) {
        this.owner = owner;
        this.iban = normalizeIban(iban);
        this.currency = currency;
        this.amountAvailable = amountAvailable;
        this.accountKey = normalizeAccountKey(accountKey);
//        this.transferStatement = new LinkedList<>();
//        accounts.add(this);
        addAccountCount(this);
    }

//    public static void countOfAccountOwnerHas(Owner owner) {
//        List<BankAccount> newListOwner = new ArrayList<>();
//        for (var singleAccount : accounts) {
//            if (singleAccount.getOwner().getName().equals(owner.getName())) {
//                newListOwner.add(singleAccount);
//            }
//        }
//        System.out.println(newListOwner);
//    }

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

    private String normalizeIban(final String iban) {
        return iban != null ? iban.toUpperCase() : null;
    }

    public void setAmountAvailable(double amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

//    public List<Transaction> getTransferStatement() {
//        return unmodifiableList(transferStatement);
////        return transferStatement;
//    }

//    public void setTransferStatement(List<Transaction> transferStatement) {
//        this.transferStatement = transferStatement;
//    }
//
//    public void addTransaction(Transaction transaction) {
//        transferStatement.add(transaction);
//    }

//    public List<Transaction> getTransferStatementLocal(LocalDate startDate, LocalDate endDate) {
//        return transferStatement.stream().filter(currentTransaction -> {
//            final LocalDate transactionDate = currentTransaction.getTimestamp();
//            return !transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate);
//        }).toList();
//    }

    @Override
    public String toString() {
        return String.format(
                "%nBank Account Details: %n " +
                "Owner: %s %n " +
                "IBAN: %s %n " +
                "Currency: %s %n " +
                "Amount Available: %s %n " +
                "Account Key: %s %n " +
                "Account Type: %s %n ",
            getOwner().getName(),
            getIban(),
            getCurrency(),
            getAmountAvailable(),
            getAccountKey(),
            getAccountType()
        );
    }

}
