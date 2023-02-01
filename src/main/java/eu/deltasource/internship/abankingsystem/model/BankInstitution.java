package eu.deltasource.internship.abankingsystem.model;

import eu.deltasource.internship.abankingsystem.BankAccountToBankMapping;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.enums.Taxes;

import java.util.*;

public class BankInstitution {

//    private BankInterface transactionInterface;

    private final String name;

    private final String address;

    private final ArrayList<Owner> customers = new ArrayList<>();

    private final Map<Taxes, Double> priceList; // (that describes all taxes and fees and all currency exchange rates)

    private final Map<Currency, Double> exchangeRates;

//    private LinkedList<Transaction> transferHistory;

    private int dayCountTime = 1;

    public BankInstitution(String name, String address, HashMap<Taxes, Double> priceList, Map<Currency, Double> exchangeRate) {
        this.name = name;
        this.address = address;
        this.priceList = priceList;
        this.exchangeRates = exchangeRate;
//        this.transactionInterface = transactionInterface;
    }

    public void getAllCustomersOfBank(BankAccount bankAccount) {
        if(!BankAccountToBankMapping.mapping.get(bankAccount).getCustomers().contains(bankAccount.getOwner())) {
            BankAccountToBankMapping.mapping.get(bankAccount).getCustomers().add(bankAccount.getOwner());
        }
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

    public Map<Currency, Double> getExchangeRates() {
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
