package eu.deltasource.internship.abankingsystem.accountType;

import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;

public class SavingsAccount extends BankAccount {

    public SavingsAccount(Owner owner, String iban, Currency currency, double amountAvailable, char accountKey) {
        super(owner, iban, currency, amountAvailable, accountKey);
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }
}
