package eu.deltasource.internship.abankingsystem.repository.bankAccountRepository;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface BankAccountRepository {
    /**
     * Saves the bank account model
     */
    void addBankAccountToMap(final BankAccount bankAccount);

    /**
     * Returns bank account by IBAN
     */
    BankAccount getByIban(final String iban);

    List<Transaction> getTransferStatementByAccount(String bankAccount);

    void addTransaction(Transaction transaction);

    List<Transaction> getTransferStatementLocal(LocalDate startDate, LocalDate endDate);

}
