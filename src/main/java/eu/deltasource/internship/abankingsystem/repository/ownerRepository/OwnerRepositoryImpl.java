package eu.deltasource.internship.abankingsystem.repository.ownerRepository;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwnerRepositoryImpl implements OwnerRepository {

    final Map<Integer, Owner> ownerMap = new HashMap<>();

    final List<BankAccount> bankAccounts = new ArrayList<>();

    int idCounter = 1;

    static OwnerRepositoryImpl instance = null;

    public static OwnerRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new OwnerRepositoryImpl();
        }

        return instance;
    }

    private OwnerRepositoryImpl() {}

    @Override
    public void addOwnerToMap(final Owner owner) {
        if(owner != null) {
            ownerMap.put(idCounter, owner);
            idCounter++;
        }
    }

    @Override
    public void addAccountToOwner(Owner owner, BankAccount bankAccount) {
        bankAccounts.add(bankAccount);
    }

    @Override
    public Owner getOwner(BankAccount bankAccount) {
        return bankAccounts.stream()
            .filter(ba -> ba.equals(bankAccount))
            .map(BankAccount::getOwner)
            .findAny()
            .orElse(null);
    }

    @Override
    public Owner getOwnerById(final int id) {
        return ownerMap.get(id);
    }
}
