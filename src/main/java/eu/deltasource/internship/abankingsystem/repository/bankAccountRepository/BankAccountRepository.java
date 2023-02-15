package eu.deltasource.internship.abankingsystem.repository.bankAccountRepository;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface BankAccountRepository {

    /**
     * Saves the bank account model.
     * @param bankAccount bank account requested to be saved.
     */
    void addBankAccountToMap(final BankAccount bankAccount);

    /**
     * Get bank account by its IBAN.
     * @param iban IBAN requested to match existing account with it.
     */
    BankAccount getByIban(final String iban);

    /**
     * Get all transactions that include the operations connected with the requested bankAccount.
     *
     * @param bankAccount requested account.
     * @return all existing transactions connected to bank account.
     */
    List<Transaction> getTransferStatementsByAccount(String bankAccount);

    /**
     * Save created transaction.
     *
     * @param transaction instance of the transaction to be saved.
     */
    void addTransaction(Transaction transaction);

    /**
     * Get transfer statement in the certain range appointed.
     *
     * @param startDate stating date.
     * @param endDate ending date.
     * @return range of created transactions.
     */
    List<Transaction> getTransferStatementLocal(LocalDate startDate, LocalDate endDate);

}
