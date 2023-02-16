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
     * Save bank.
     *
     * @param bankInstitution bank object to be added to Map
     */
    void addBankToMap(final BankInstitution bankInstitution);

    /**
     * Get saved bank by id.
     *
     * @param id id of bank.
     * @return bank found by id.
     */
    BankInstitution getBankById(int id);

    /**
     * Assign account to bank.
     *
     * @param bankInstitution bank to be assigned to account.
     * @param bankAccount account to assign to bank.
     */
    void addAccountToBank(BankInstitution bankInstitution, List<BankAccount> bankAccount);

    /**
     * Get the bank assigned to the account from the Map.
     *
     * @param bankAccount account assigned to certain bank.
     * @return bank assigned to certain bank.
     */
    BankInstitution getBank(BankAccount bankAccount);

    /**
     * Add taxes to bank(used on operations).
     *
     * @param bank requested bank.
     * @param taxMap requested taxes.
     */
	void addTaxToBankMap(BankInstitution bank, Map<TaxType, Double> taxMap);

    /**
     * Get the full Collection having saved taxes by bank.
     *
     * @param bank requested bank having the certain taxes.
     * @return the taxes of the requested bank.
     */
	Map<TaxType, Double> getTaxMap(BankInstitution bank);

    /**
     * Get tax's price by its key.
     *
     * @param taxType const.
     * @return taxes list.
     */
	Double getTaxByTax(TaxType taxType);

    /**
     * Get tax from certain bank institution.
     *
     * @param bank requested bank.
     * @param taxType requested bank's tax.
     * @return value of tax.
     */
	Double getTaxByBank(BankInstitution bank, TaxType taxType);

    /**
     * Get all taxes assigned to a bank institution.
     *
     * @param bank requested bank.
     * @return Map of all taxes.
     */
	Map<TaxType, Double> findTaxesByBank(BankInstitution bank);

    /**
     * Add taxes to bank(used on operations).
     */
	void addTaxesToBank(BankInstitution bank, Map<TaxType, Double> taxes, List<TaxType> taxTypeToAdd);

    /**
     * Save transaction.
     *
     * @param transaction requested transaction.
     */
    void addTransaction(Transaction transaction);

    /**
     * Get all transactions for bank institution.
     *
     * @param bankInstitution requested bank.
     * @return list of all transactions.
     */
    List<Transaction> getAllTransactions(BankInstitution bankInstitution);

    /**
     * Save account.
     *
     * @param bankAccount requested created account to be saved.
     */
    void addAccount(BankAccount bankAccount);

    /**
     * Save owner.
     *
     * @param owner requested created owner to be saved.
     */
    void addOwner(Owner owner);

    /**
     * Get all owners assigned to bank institution.
     *
     * @param bankInstitution requested bank institution.
     * @return list of owners assigned to certain bank account.
     */
    List<Owner> getAllOwners(BankInstitution bankInstitution);

    /**
     * Get list of all accounts assigned to bank
     *
     * @return list of accounts
     */
    List<BankAccount> getAllAccounts();

    /**
     * Get Collection of Exchange Rates assigned to a bank institution.
     *
     * @param bankInstitution requested bank.
     * @return Collection of Exchange Rates.
     */
    Map<ExchangeRate, Double> getExchangeRates(BankInstitution bankInstitution);

    /**
     * Add exchange rates to bank
     *
     * @param bank requested bank
     * @param exchangeRates requested exchange rates Collection
     */
    void addExchangeRatesToBank(BankInstitution bank, Map<ExchangeRate, Double> exchangeRates);

}
