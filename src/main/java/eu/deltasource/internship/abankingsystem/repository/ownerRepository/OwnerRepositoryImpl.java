package eu.deltasource.internship.abankingsystem.repository.ownerRepository;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwnerRepositoryImpl implements OwnerRepository {

    private final Map<Integer, Owner> ownerMap = new HashMap<>();

    private final List<BankAccount> bankAccounts = new ArrayList<>();

    private int idCounter = 1;

    private static OwnerRepository instance = null;

    public static OwnerRepository getInstance() {
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
//            bankAccounts.addAll(owner.getBankAccounts());
            idCounter++;
        }
    }

    @Override
    public void addAccountToOwner(Owner owner, BankAccount bankAccount) {
//        owner.addAccountToOwner(bankAccount);
        bankAccounts.add(bankAccount);
    }

    @Override
    public Owner getOwner(BankAccount bankAccount) { // PLACE NOT IN THIS REPO
//        return ownerMap.values().stream().filter(owner ->
//            owner.getBankAccounts().contains(bankAccount)
//        ).findAny().orElse(null);
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
