package eu.deltasource.internship.abankingsystem.service.bankAccountService;

import eu.deltasource.internship.abankingsystem.enums.AccountType;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankAccount;

import java.util.Optional;

public interface BankAccountService {

    /**
     * Create account from model instance
     *
     * @param iban
     * @param currency
     * @param amountAvailable
     */
    void createBankAccount(Optional<String> iban, Currency currency, double amountAvailable, AccountType accountKey);

    /**
     * Get existing Bank Account by it's Iban
     *
     * @param iban
     * @return
     */
    BankAccount getBankAccountByIban(String iban);
}
