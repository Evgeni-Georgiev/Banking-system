package eu.deltasource.internship.abankingsystem.repository.ownerRepository;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;

import java.util.List;
import java.util.Map;

public interface OwnerRepository {

    /**
     * Save Owner.
     *
     * @param owner existing owner.
     */
    void addOwnerToMap(final Owner owner);

    /**
     * Assign Owner to Bank Account(s).
     *
     * @param owner requested owner.
     * @param bankAccounts list of accounts.
     */
    void addOwnerToAccounts(Owner owner, List<BankAccount> bankAccounts);


    /**
     * Get owner by id.
     *
     * @param id requested id of owner.
     */
    Owner getOwnerById(int id);

    /**
     * Get all account single owner has.
     *
     * @param owner requested owner.
     * @return list of all owner's accounts.
     */
    List<BankAccount> getAccountsForOwner(Owner owner);

    /**
     * Get list of account by owner assigned ot them.
     *
     * @return accounts
     */
    Map<Owner, List<BankAccount>> getOwnerAccountMap();
}
