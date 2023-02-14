package eu.deltasource.internship.abankingsystem.repository.ownerRepository;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;

import java.util.List;
import java.util.Map;

public interface OwnerRepository {

    /**
     * Save Owner to Map
     *
     * @param owner existing owner
     */
    void addOwnerToMap(final Owner owner);

    /**
     * Assign Owner to Bank Account(s)
     *
//     * @param owner
//     * @param bankAccounts
     */
    void addOwnerToAccounts(Owner owner, List<BankAccount> bankAccounts);

    Owner getOwnerById(int id);

    List<BankAccount> getAccountsForOwner(Owner owner);

    Map<Owner, List<BankAccount>> getOwnerAccountMap();
}
