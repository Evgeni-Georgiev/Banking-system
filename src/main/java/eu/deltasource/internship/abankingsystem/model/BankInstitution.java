package eu.deltasource.internship.abankingsystem.model;

import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.enums.Taxes;

import java.util.*;

public class BankInstitution {

    private final String name;

    private final String address;

    private final ArrayList<Owner> customers = new ArrayList<>();

    private final Map<Taxes, Double> priceList; // (that describes all taxes and fees and all currency exchange rates)

    private final Map<Currency, Double> exchangeRates;

    // list of account
    private List<BankAccount> allAccounts = new ArrayList<>();

    private List<Transaction> allTransactions = new LinkedList<>();

    private int dayCountTime = 1;

    public BankInstitution(String name, String address, HashMap<Taxes, Double> priceList, Map<Currency, Double> exchangeRate) {
        this.name = name;
        this.address = address;
        this.priceList = priceList;
        this.exchangeRates = exchangeRate;
//        this.allAccounts = new ArrayList<>();
//        this.transactionInterface = transactionInterface;
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

    public List<BankAccount> getAllAccounts() {
        return allAccounts;
    }

    public List<Transaction> getAllTransactions() {
        return allTransactions;
    }

    public void addAllTransaction(Transaction transaction) {
        allTransactions.add(transaction);
    }

    public void setAllTransactions(List<Transaction> allTransactions) {
        this.allTransactions = allTransactions;
    }

    @Override
    public String toString() {
        return String.format("%nBank Details: %n " +
                "Name: %s %n " +
                "Address: %s %n " +
                "Customers: %s %n " +
                "Taxes: %s %n " +
                "Exchange rates available: %s %n " +
                "All Transactions: %s %n " +
            getName(),
            getName(),
            getAddress(),
            getCustomers().size(),
            getPriceList(),
            getExchangeRates(),
            allTransactions
        );
    }
}
