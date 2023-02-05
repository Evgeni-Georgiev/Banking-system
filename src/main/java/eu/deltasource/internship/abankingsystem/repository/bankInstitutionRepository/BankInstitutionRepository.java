package eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository;

import eu.deltasource.internship.abankingsystem.enums.Taxes;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;

import java.util.List;
import java.util.Map;

public interface BankInstitutionRepository {

    /**
     * Save bank to Map.
     *
     * @param bankInstitution bank object to be added to Map
     */
    void addBankToMap(final BankInstitution bankInstitution);

    /**
     * Get bank from Map by id
     *
     * @param id id of bank
     * @return bank found by id
     */
    BankInstitution getBankById(int id);


    /**
     * Assign bank to account.
     *
     * @param bankInstitution bank to be assigned to account
     * @param bankAccount account to assign to bank
     */
    void addAccountToBank(BankInstitution bankInstitution, BankAccount bankAccount);

    /**
     * Get the bank assigned to the account from the Map.
     *
     * @param bankAccount account assigned to certain bank
     * @return bank assigned to certain bank
     */
    BankInstitution getBank(BankAccount bankAccount);

//    Map<Taxes, Double> getPriceList();

//    void addPriceListToMap(Taxes taxes, Double prices);

//    Double getPriceList(Taxes taxes);

	void addTaxToBankMap(BankInstitution bank, Map<Taxes, Double> taxMap);

	Map<Taxes, Double> getTaxMap(BankInstitution bank);

	Double getTaxByTax(Taxes taxes);

	Double getTaxByBank(BankInstitution bank, Taxes taxes);

	Map<Taxes, Double> findTaxesByBank(BankInstitution bank);

	void filterAndAddTaxesToBank(BankInstitution bank, Map<Taxes, Double> taxes, List<Taxes> taxesToAdd);

//	BankAccount getAccountListedToBank(BankInstitution bankInstitution);

}
