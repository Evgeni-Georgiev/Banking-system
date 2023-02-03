package eu.deltasource.internship.abankingsystem.accountType;

import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;

public class CurrentAccount extends BankAccount {

    public CurrentAccount(Owner owner, String iban, Currency currency, double amountAvailable, char accountKey) {
        super(owner, iban, currency, amountAvailable, accountKey);
    }

    @Override
    protected String getAccountType() {
        return "Current Account";
    }

}
