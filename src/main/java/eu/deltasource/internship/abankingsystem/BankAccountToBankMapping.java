package eu.deltasource.internship.abankingsystem;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;

import java.util.HashMap;
import java.util.Map;

public class BankAccountToBankMapping {
    private final Map<BankAccount, BankInstitution> mapping = new HashMap<>();

    public void addMapping(BankAccount bankAccount, BankInstitution bank) {
        mapping.put(bankAccount, bank);
    }

    public BankInstitution getBank(BankAccount bankAccount) {
        return mapping.get(bankAccount);
    }
}
