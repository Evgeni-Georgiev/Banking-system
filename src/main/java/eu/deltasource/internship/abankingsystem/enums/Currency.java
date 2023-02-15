package eu.deltasource.internship.abankingsystem.enums;

/**
 * Store all currencies.
 */
public enum Currency {

    USD("USD"),

    BGN("BGN"),

    EUR("EUR");

    private final String currency;

    Currency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return this.currency;
    }
}
