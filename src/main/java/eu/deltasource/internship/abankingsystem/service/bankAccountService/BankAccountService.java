package eu.deltasource.internship.abankingsystem.service.bankAccountService;

import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;

public interface BankAccountService {

    /**
     * Create account from model instance
     *
     * @param owner
     * @param iban
     * @param currency
     * @param amountAvailable
     * @param accountKey
     */
    void createBankAccount(Owner owner, String iban, Currency currency, double amountAvailable, char accountKey);

    /**
     * Get existing Bank Account by it's Iban
     *
     * @param iban
     * @return
     */
    BankAccount getBankAccountByIban(String iban);
}
