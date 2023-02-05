package eu.deltasource.internship.abankingsystem.service.bankInstitutionService;

import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository.BankInstitutionRepository;

import java.util.Map;

public class BankInstitutionImpl implements BankInstitutionService {

    private final BankInstitutionRepository bankInstitutionRepository;

    public BankInstitutionImpl(BankInstitutionRepository bankInstitutionRepository) {
        this.bankInstitutionRepository = bankInstitutionRepository;
    }

//    public void createExchangeRates(Taxes taxes, Double priceTax) {
//        bankInstitutionRepository.addPriceList(taxes, priceTax);
//    }

    @Override
    public void createBankInstitution(String name, String address, Map<ExchangeRate, Double> exchangeRate) {
        BankInstitution newBankInstitution = new BankInstitution(name, address, exchangeRate);
//		bankInstitutionRepository.addTaxToBankMap(newBankInstitution);
        bankInstitutionRepository.addBankToMap(newBankInstitution);
    }

    @Override
    public BankInstitution getBankInstitutionById(int id) {
        return bankInstitutionRepository.getBankById(id);
    }
}
