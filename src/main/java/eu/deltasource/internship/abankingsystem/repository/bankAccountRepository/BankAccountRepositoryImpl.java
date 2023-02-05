package eu.deltasource.internship.abankingsystem.repository.bankAccountRepository;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;
import eu.deltasource.internship.abankingsystem.model.Transaction;

import java.time.LocalDate;
import java.util.*;

public class BankAccountRepositoryImpl implements BankAccountRepository {

    private final Map<String, BankAccount> bankAccountMap = new HashMap<>();

    private static final List<BankAccount> accounts = new ArrayList<>();

    private final List<Transaction> transferStatement = new LinkedList<>();

    private static BankAccountRepository instance = null;

    public static void addAccountCount(BankAccount bankAccount) {
        accounts.add(bankAccount);
    }

    public static void countOfAccountOwnerHas(Owner owner) {
        List<BankAccount> newListOwner = new ArrayList<>();
        for (var singleAccount : accounts) {
            if (singleAccount.getOwner().getName().equals(owner.getName())) {
                newListOwner.add(singleAccount);
            }
        }
        System.out.println(newListOwner);
    }

    @Override
    public List<Transaction> getTransferStatement(BankAccount bankAccount) {

        return transferStatement.stream()
            .filter(currentTransactionStatement -> (currentTransactionStatement.getSourceAccount().equals(bankAccount)
                || currentTransactionStatement.getTargetAccount() != null && currentTransactionStatement.getTargetAccount().equals(bankAccount))
            )
            .toList();
    }

    @Override
    public List<Transaction> getTransferStatementLocal(LocalDate startDate, LocalDate endDate) {
        return transferStatement.stream().filter(currentTransaction -> {
            final LocalDate transactionDate = currentTransaction.getTimestamp();
            return !transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate);
        }).toList();
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transferStatement.add(transaction);
    }

    @Override
    public void addBankAccountToMap(final BankAccount bankAccount) {
        if (bankAccount != null) {
            bankAccountMap.put(bankAccount.getIban(), bankAccount);
        }
    }

    @Override
    public BankAccount getByIban(final String iban) {
        return bankAccountMap.get(iban);
    }

    private BankAccountRepositoryImpl() {}

    public static BankAccountRepository getInstance() {
        if (instance == null) {
            instance = new BankAccountRepositoryImpl();
        }

        return instance;
    }
}
