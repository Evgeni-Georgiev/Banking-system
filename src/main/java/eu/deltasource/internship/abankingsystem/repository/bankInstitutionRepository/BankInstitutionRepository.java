package eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository;

import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.abankingsystem.enums.TaxType;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.model.Owner;
import eu.deltasource.internship.abankingsystem.model.Transaction;

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
     * Assign account to bank.
     *
     * @param bankInstitution bank to be assigned to account
     * @param bankAccount account to assign to bank
     */
    void addAccountToBank(BankInstitution bankInstitution, List<BankAccount> bankAccount);

    /**
     * Get the bank assigned to the account from the Map.
     *
     * @param bankAccount account assigned to certain bank
     * @return bank assigned to certain bank
     */
    BankInstitution getBank(BankAccount bankAccount);

	void addTaxToBankMap(BankInstitution bank, Map<TaxType, Double> taxMap);

	Map<TaxType, Double> getTaxMap(BankInstitution bank);

	Double getTaxByTax(TaxType taxType);

	Double getTaxByBank(BankInstitution bank, TaxType taxType);

	Map<TaxType, Double> findTaxesByBank(BankInstitution bank);

	void filterAndAddTaxesToBank(BankInstitution bank, Map<TaxType, Double> taxes, List<TaxType> taxTypeToAdd);

    void addTransaction(Transaction transaction);

    List<Transaction> getAllTransactions(BankInstitution bankInstitution);

    void addAccount(BankAccount bankAccount);

    void addOwner(Owner owner);

    List<Owner> getAllOwners(BankInstitution bankInstitution);

    List<BankAccount> getAllAccounts();

    Map<ExchangeRate, Double> getExchangeRates(BankInstitution bankInstitution);

    void addExchangeRatesToBank(BankInstitution bank, Map<ExchangeRate, Double> exchangeRates);


}
