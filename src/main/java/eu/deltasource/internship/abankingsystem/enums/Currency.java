package eu.deltasource.internship.abankingsystem.enums;

public enum Currency {
    USD("USD"),
    BGN("BGN"),
    EUR("EUR");

    final String currencyString;

    Currency(String currencyString) {
        this.currencyString = currencyString;
    }

    public String getCurrency() {
        return this.currencyString;
    }

}
