package eu.deltasource.internship.abankingsystem.service.bankAccountService;

import eu.deltasource.internship.abankingsystem.accountType.CurrentAccount;
import eu.deltasource.internship.abankingsystem.accountType.SavingsAccount;
import eu.deltasource.internship.abankingsystem.enums.AccountType;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.repository.bankAccountRepository.BankAccountRepository;
import eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository.BankInstitutionRepository;

import java.util.Optional;

/**
 * Process business logic for Bank Accounts
 */
public class BankAccountServiceImpl implements BankAccountService{

    private final BankAccountRepository bankAccountRepository;

    private final BankInstitutionRepository bankInstitutionRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, BankInstitutionRepository bankInstitutionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankInstitutionRepository = bankInstitutionRepository;
    }

    @Override
    public void createBankAccount(Optional<String> iban, Currency currency, double amountAvailable, AccountType accountKey) {
        BankAccount newAccount;
        if(accountKey == AccountType.CURRENT_ACCOUNT) {
            newAccount = new CurrentAccount(iban, currency, amountAvailable, accountKey);
        } else {
            newAccount = new SavingsAccount(iban, currency, amountAvailable, accountKey);
        }
        bankInstitutionRepository.addAccount(newAccount);
        // Add new account to the map(db) of it's repository
        bankAccountRepository.addBankAccountToMap(newAccount);
    }

    @Override
    public BankAccount getBankAccountByIban(String iban) {
        // Get the saved account by iban from the map
        return bankAccountRepository.getByIban(iban);
    }

}
