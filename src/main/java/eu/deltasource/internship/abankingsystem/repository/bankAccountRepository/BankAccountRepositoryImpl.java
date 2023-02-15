package eu.deltasource.internship.abankingsystem.repository.bankAccountRepository;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Transaction;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BankAccountRepositoryImpl implements BankAccountRepository {

    final Map<String, BankAccount> bankAccountMap = new HashMap<>();

    final List<Transaction> transferStatement = new LinkedList<>();

    static BankAccountRepositoryImpl instance = null;

    @Override
    public List<Transaction> getTransferStatementsByAccount(String bankAccountIban) {
        return transferStatement.stream()
            .filter(currentTransactionStatement -> isTransactionStatementOnAccountIban(bankAccountIban, currentTransactionStatement))
            .toList();
    }

    /**
     * Check if IBAN is present in the transaction(matters not target or source).
     *
     * @param bankAccountIban
     * @param currentTransactionStatement
     * @return
     */
    private boolean isTransactionStatementOnAccountIban(String bankAccountIban, Transaction currentTransactionStatement) {
        return currentTransactionStatement.getSourceAccountIban().equals(bankAccountIban)
            || (currentTransactionStatement.getTargetAccountIban() != null
            && currentTransactionStatement.getTargetAccountIban().equals(bankAccountIban));
    }

    @Override
    public List<Transaction> getTransferStatementLocal(LocalDate startDate, LocalDate endDate) {
        return transferStatement.stream()
            .filter(currentTransaction -> transactionDateRange(startDate, endDate, currentTransaction))
            .toList();
    }

    private boolean transactionDateRange(LocalDate startDate, LocalDate endDate, Transaction currentTransaction) {
        final LocalDate transactionDate = currentTransaction.getTimestamp();
        return !transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transferStatement.add(transaction);
    }

    @Override
    public void addBankAccountToMap(final BankAccount bankAccount) { // to not override the reference in the method
        if (bankAccount != null) {
            if(bankAccount.getIban().isPresent()) {
                bankAccountMap.put(bankAccount.getIban().get(), bankAccount);
            }
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
