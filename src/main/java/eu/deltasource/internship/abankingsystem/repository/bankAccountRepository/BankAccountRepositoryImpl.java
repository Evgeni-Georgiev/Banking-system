package eu.deltasource.internship.abankingsystem.repository.bankAccountRepository;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;
import eu.deltasource.internship.abankingsystem.model.Transaction;

import java.time.LocalDate;
import java.util.*;

import static java.util.Collections.unmodifiableList;

public class BankAccountRepositoryImpl implements BankAccountRepository {

    final Map<String, BankAccount> bankAccountMap = new HashMap<>();

    static final List<BankAccount> accounts = new ArrayList<>();

    final List<Transaction> transferStatement = new LinkedList<>();

    static BankAccountRepositoryImpl instance = null;

    public static void addAccountCount(BankAccount bankAccount) {
        accounts.add(bankAccount);
    }

    public static List<BankAccount> countOfAccountOwnerHas(Owner owner) {
        List<BankAccount> newListOwner = new ArrayList<>();
        for (var singleAccount : accounts) {
            if (singleAccount.getOwner().getName().equals(owner.getName())) {
                newListOwner.add(singleAccount);
            }
        }
        return unmodifiableList(newListOwner);
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
    public void addBankAccountToMap(final BankAccount bankAccount) { // to not override the reference in the method
        if (bankAccount != null) {
            bankAccountMap.put(bankAccount.getIban(), bankAccount);
        }
    }

    @Override
    public BankAccount getByIban(final String iban) {
        return bankAccountMap.get(iban);
    }

    private BankAccountRepositoryImpl() {}

    public static BankAccountRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new BankAccountRepositoryImpl();
        }

        return instance;
    }
}
