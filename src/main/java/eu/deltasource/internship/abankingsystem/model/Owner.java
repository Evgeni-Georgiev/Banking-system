package eu.deltasource.internship.abankingsystem.model;

import java.util.ArrayList;
import java.util.List;

public class Owner {

    private final String name;

    private final List<BankAccount> ownerAccountCount = new ArrayList<>();

    public Owner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<BankAccount> getOwnerAccountCount() {
        return ownerAccountCount;
    }

    @Override
    public String toString() {
        return String.format("%nOwner Details: " +
            "%n Name: %s %n " +
            "%n Count of accounts for this Owner: %s %n ", getName(), getOwnerAccountCount().size());
    }
}
