package eu.deltasource.internship.abankingsystem.service.bankAccountService;

import eu.deltasource.internship.abankingsystem.accountType.CurrentAccount;
import eu.deltasource.internship.abankingsystem.accountType.SavingsAccount;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;
import eu.deltasource.internship.abankingsystem.repository.bankAccountRepository.BankAccountRepository;

/**
 * Process business logic for Bank Accounts
 */
public class BankAccountServiceImpl implements BankAccountService{

    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public void createBankAccount(Owner owner, String iban, Currency currency, double amountAvailable, char accountKey) {
        BankAccount newAccount;
        if(accountKey == 'C') {
            newAccount = new CurrentAccount(owner, iban, currency, amountAvailable, accountKey);
        } else {
            newAccount = new SavingsAccount(owner, iban, currency, amountAvailable, accountKey);
        }
        // Add new account to the map(db) of it's repository
        bankAccountRepository.addBankAccountToMap(newAccount);
    }

    @Override
    public BankAccount getBankAccountByIban(String iban) {
        // Get the saved account by iban from the map
        return bankAccountRepository.getByIban(iban);
    }

}
