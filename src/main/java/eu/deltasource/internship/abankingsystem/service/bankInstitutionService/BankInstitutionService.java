package eu.deltasource.internship.abankingsystem.service.bankInstitutionService;

import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;

import java.util.Map;

public interface BankInstitutionService {

    /**
     * Create bank from model instance
     *
     * @param name
     * @param address
     * @param exchangeRate
     */
    void createBankInstitution(String name, String address, Map<ExchangeRate, Double> exchangeRate);


    /**
     * Get existing BankInstitution by it's id
     *
     * @param id
     * @return
     */
    BankInstitution getBankInstitutionById(int id);

}
