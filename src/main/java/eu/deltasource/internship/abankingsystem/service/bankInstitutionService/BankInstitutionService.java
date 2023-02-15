package eu.deltasource.internship.abankingsystem.service.bankInstitutionService;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.model.Owner;

import java.util.List;

public interface BankInstitutionService {

    /**
     * Create bank from model instance.
     *
     * @param name name of bank.
     * @param address address of bank.
     */
    void createBankInstitution(String name, String address);


    /**
     * Get existing BankInstitution by its id.
     *
     * @param id requested bank by its id.
     * @return requested bank by its id.
     */
    BankInstitution getBankInstitutionById(int id);

    /**
     * Return a list of all owners having at least one account in bank.
     *
     * @param bankInstitution bank to search for owners.
     * @return Return a list of owners.
     */
    List<Owner> filterOwnersByBank(BankInstitution bankInstitution);

    /**
     * Return a list of all accounts having at least one account in bank.
     *
     * @param bankInstitution bank to search for accounts.
     * @return Return a list of accounts.
     */
    List<BankAccount> filterAccountsInBank(BankInstitution bankInstitution);

}
