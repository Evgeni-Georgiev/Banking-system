package eu.deltasource.internship.abankingsystem.model;

import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableList;

public class BankInstitution {

    private final String name;

    private final String address;

    private final Map<ExchangeRate, Double> exchangeRates;

    private final List<BankAccount> bankAccounts = new ArrayList<>();

    private int dayCountTime = 1;

    public BankInstitution(String name, String address, Map<ExchangeRate, Double> exchangeRate) {
        this.name = name;
        this.address = address;
        this.exchangeRates = exchangeRate;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
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
        return unmodifiableList(bankAccounts);
    }


    @Override
    public String toString() {
        return String.format("%nBank Details: %n " +
                "Name: %s %n " +
                "Address: %s %n " +
            getName(),
            getName(),
            getAddress()
        );
    }
}
