package eu.deltasource.internship.abankingsystem;

import java.util.ArrayList;
import java.util.List;

public class Owner {

    private String name;

    private List<BankAccount> bankAccounts = new ArrayList<>();

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    private List<BankAccount> ownerAccountCount = new ArrayList<>();

    public List<BankAccount> getOwnerAccountCount() {
        return ownerAccountCount;
    }

    public void setOwnerAccountCount(List<BankAccount> ownerAccountCount) {
        this.ownerAccountCount = ownerAccountCount;
    }

    public String getName() {
        return name;
    }

    public Owner(String name) {
        this.name = name;
//        bankAccounts.add(this);
    }

    @Override
    public String toString() {
        return String.format("%nOwner Details: " +
            "%n Name: %s %n " +
            "%n Count of accounts for this Owner: %s %n ", getName(), getOwnerAccountCount().size());
    }
}
