package eu.deltasource.internship.abankingsystem.accountType;

import eu.deltasource.internship.abankingsystem.enums.AccountType;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankAccount;

import java.util.Optional;

public class SavingsAccount extends BankAccount {

    public SavingsAccount(Optional<String> iban, Currency currency, double amountAvailable, AccountType accountType) {
        super(iban, currency, amountAvailable, accountType);
    }

    @Override
    protected String getAccountType() {
        return "Savings Account";
    }
}
