package eu.deltasource.internship.abankingsystem.model;

import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.abankingsystem.enums.Taxes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BankInstitution {

    private final String name;

    private final String address;

    private final List<Owner> customers = new ArrayList<>();

    private final Map<Taxes, Double> priceList; // (that describes all taxes and fees and all currency exchange rates)

    private final Map<ExchangeRate, Double> exchangeRates;

    private final List<BankAccount> bankAccounts = new ArrayList<>();

    private int dayCountTime = 1;

    public BankInstitution(String name, String address, Map<Taxes, Double> priceList, Map<ExchangeRate, Double> exchangeRate) {
        this.name = name;
        this.address = address;
        this.priceList = priceList;
        this.exchangeRates = exchangeRate;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Owner> getCustomers() {
        return customers;
    }

    public Map<Taxes, Double> getPriceList() {
        return priceList;
    }

    public Map<ExchangeRate, Double> getExchangeRates() {
        return exchangeRates;
    }

    public int getDayCountTime() {
        return dayCountTime;
    }

    public void setDayCountTime(int dayCountTime) {
        this.dayCountTime = dayCountTime;
    }

    public void addAccountToBank(final BankAccount bankAccount) {
        bankAccounts.add(bankAccount);
    }

    public List<BankAccount> getBankAccounts() {
        return Collections.unmodifiableList(bankAccounts);
    }


    @Override
    public String toString() {
        return String.format("%nBank Details: %n " +
                "Name: %s %n " +
                "Address: %s %n " +
//                "Customers: %s %n " +
//                "Taxes: %s %n " +
//                "Exchange rates available: %s %n " +
            getName(),
            getName(),
            getAddress()
//            getCustomers().size(),
//            getPriceList(),
//            getExchangeRates()
        );
    }
}
