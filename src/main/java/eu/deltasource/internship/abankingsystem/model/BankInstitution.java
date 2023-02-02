package eu.deltasource.internship.abankingsystem.model;

import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.abankingsystem.enums.Taxes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BankInstitution {

    private final String name;

    private final String address;

    private final ArrayList<Owner> customers = new ArrayList<>();

    private final Map<Taxes, Double> priceList; // (that describes all taxes and fees and all currency exchange rates)

    private final Map<ExchangeRate, Double> exchangeRates;

    private int dayCountTime = 1;

    public BankInstitution(String name, String address, HashMap<Taxes, Double> priceList, Map<ExchangeRate, Double> exchangeRate) {
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

    public ArrayList<Owner> getCustomers() {
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

    @Override
    public String toString() {
        return String.format("%nBank Details: %n " +
                "Name: %s %n " +
                "Address: %s %n " +
                "Customers: %s %n " +
                "Taxes: %s %n " +
                "Exchange rates available: %s %n " +
            getName(),
            getName(),
            getAddress(),
            getCustomers().size(),
            getPriceList(),
            getExchangeRates()
        );
    }
}
