package eu.deltasource.internship.abankingsystem.service.bankAccountService;

import eu.deltasource.internship.abankingsystem.enums.AccountType;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankAccount;

import java.util.Optional;

public interface BankAccountService {

    /**
     * Create account from model instance.
     *
     * @param iban account's IBAN
     * @param currency default currency of the account
     * @param amountAvailable base amount for the account to be created with.
     */
    void createBankAccount(Optional<String> iban, Currency currency, double amountAvailable, AccountType accountKey);

    /**
     * Get existing Bank Account by it's IBAN.
     *
     * @param iban find existing account by this IBAN
     * @return found BankAccount
     */
    BankAccount getBankAccountByIban(String iban);
}
