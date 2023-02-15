package eu.deltasource.internship.abankingsystem.model;

import eu.deltasource.internship.abankingsystem.enums.AccountType;
import eu.deltasource.internship.abankingsystem.enums.Currency;

import java.util.Optional;

/**
 * Create BankAccounts and adding specific properties to it depending on the required type of account.
 */
public class BankAccount {

    private final Optional<String> iban;

    private final Currency currency;

    private Double amountAvailable;

    private final AccountType accountKey;

    public BankAccount(Optional<String> iban, Currency currency, double amountAvailable, AccountType accountKey) {
        this.iban = normalizeIban(iban);
        this.currency = currency;
        this.amountAvailable = amountAvailable;
        this.accountKey = accountKey;
    }

    public Optional<String> getIban() {
        return iban;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Double getAmountAvailable() {
        return amountAvailable;
    }

    protected String getAccountType() {
        return "Undefined account type";
    }

    public AccountType getAccountKey() {
        return accountKey;
    }

    private Optional<String> normalizeIban(final Optional<String> iban) {
        return iban.isPresent() ? iban.map(String::toUpperCase) : Optional.empty();
    }

    public void setAmountAvailable(double amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    @Override
    public String toString() {
        return String.format(
                """
                    
                    Bank Account Details:
                    IBAN: %s
                    Currency: %s
                    Amount Available: %.2f
                    Account Type: %s
                    """,
            getIban().get(),
            getCurrency(),
            getAmountAvailable(),
            getAccountType()
        );
    }

}
