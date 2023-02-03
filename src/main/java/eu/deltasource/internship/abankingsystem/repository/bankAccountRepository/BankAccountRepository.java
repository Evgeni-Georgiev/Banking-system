package eu.deltasource.internship.abankingsystem.repository.bankAccountRepository;

import eu.deltasource.internship.abankingsystem.model.BankAccount;

public interface BankAccountRepository {
    /**
     * Saves the bank account model
     */
    void addBankAccountToMap(final BankAccount bankAccount);

    /**
     * Returns bank account by IBAN
     */
    BankAccount getByIban(final String iban);
}
