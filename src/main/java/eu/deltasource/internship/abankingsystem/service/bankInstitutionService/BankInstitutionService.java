package eu.deltasource.internship.abankingsystem.service.bankInstitutionService;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.model.Owner;

import java.util.List;

public interface BankInstitutionService {

    /**
     * Create bank from model instance
     *
     * @param name
     * @param address
     */
//    void createBankInstitution(String name, String address, Map<ExchangeRate, Double> exchangeRate);
    void createBankInstitution(String name, String address);


    /**
     * Get existing BankInstitution by its id
     *
     * @param id
     * @return
     */
    BankInstitution getBankInstitutionById(int id);

    List<Owner> filterOwnersByBank(BankInstitution bankInstitution);

    List<BankAccount> filterAccountsInBank(BankInstitution bankInstitution);

}
