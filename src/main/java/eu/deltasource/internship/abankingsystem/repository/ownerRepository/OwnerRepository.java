package eu.deltasource.internship.abankingsystem.repository.ownerRepository;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;

public interface OwnerRepository {

    /**
     * Save Owner to Map
     *
     * @param owner existing owner
     */
    void addOwnerToMap(final Owner owner);

    /**
     * Assign Owner to Bank Account in Map
     *
     * @param owner
     * @param bankAccount
     */
    void addAccountToOwner(Owner owner, BankAccount bankAccount);

    /**
     * Find out the Owner of account
     *
     * @param bankAccount
     * @return
     */
    Owner getOwner(BankAccount bankAccount);

    Owner getOwnerById(int id);
}
