package eu.deltasource.internship.abankingsystem.enums;

/**
 * Const holding the type of account.
 */
public enum AccountType {

    CURRENT_ACCOUNT("Current Account"),

    SAVINGS_ACCOUNT("Savings Account");

    final String accountType;

    AccountType(String accountType) {
        this.accountType = accountType;
    }
}
