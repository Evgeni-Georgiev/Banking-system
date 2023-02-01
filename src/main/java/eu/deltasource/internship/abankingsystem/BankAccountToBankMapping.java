package eu.deltasource.internship.abankingsystem;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.service.BankInterface;
import eu.deltasource.internship.abankingsystem.service.BankService;

import java.util.HashMap;
import java.util.Map;

public class BankAccountToBankMapping {
    public static Map<BankAccount, BankInstitution> mapping = new HashMap<>();

    public Map<BankAccount, BankInstitution> getMapping() {
        return mapping;
    }

    public void addMapping(BankAccount bankAccount, BankInstitution bank) {
//        transactionImpl.checkIfOwnerExists(bankAccount);
        mapping.put(bankAccount, bank);
    }

    public BankInstitution getBank(BankAccount bankAccount) {
        return mapping.get(bankAccount);
    }

}
