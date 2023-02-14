package eu.deltasource.internship.abankingsystem.repository.ownerRepository;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class OwnerRepositoryImpl implements OwnerRepository {

    final Map<Integer, Owner> ownerMap = new HashMap<>();

    final Map<Owner, List<BankAccount>> ownerAccountMap = new HashMap<>();

    int idCounter = 1;

    static OwnerRepositoryImpl instance = null;

    @Override
    public Map<Owner, List<BankAccount>> getOwnerAccountMap() {
        return unmodifiableMap(ownerAccountMap);
    }

    public static OwnerRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new OwnerRepositoryImpl();
        }

        return instance;
    }

    @Override
    public List<BankAccount> getAccountsForOwner(Owner owner) {
        return unmodifiableMap(ownerAccountMap).get(owner);
    }

    @Override
    public void addOwnerToAccounts(Owner owner, List<BankAccount> bankAccounts) {
        List<BankAccount> accounts = ownerAccountMap.get(owner);
        if (accounts == null) {
            accounts = new ArrayList<>();
            ownerAccountMap.put(owner, accounts);
        }
        accounts.addAll(bankAccounts);
    }

    private OwnerRepositoryImpl() {}

    @Override
    public void addOwnerToMap(Owner owner) {
        if(owner != null) {
            ownerMap.put(idCounter, owner);
            idCounter++;
        }
    }

    @Override
    public Owner getOwnerById(final int id) {
        return ownerMap.get(id);
    }
}
