package eu.deltasource.internship.abankingsystem.service.bankInstitutionService;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.model.Owner;
import eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository.BankInstitutionRepository;
import eu.deltasource.internship.abankingsystem.repository.ownerRepository.OwnerRepository;

import java.util.List;
import java.util.Map;

/**
 * Processing business logic for BankInstitution.
 */
public class BankInstitutionServiceImpl implements BankInstitutionService {

    private final BankInstitutionRepository bankInstitutionRepository;

    private final OwnerRepository ownerRepository;

    public BankInstitutionServiceImpl(BankInstitutionRepository bankInstitutionRepository, OwnerRepository ownerRepository) {
        this.bankInstitutionRepository = bankInstitutionRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public void createBankInstitution(String name, String address) {
        BankInstitution newBankInstitution = new BankInstitution(name, address);
        bankInstitutionRepository.addBankToMap(newBankInstitution);
    }

    @Override
    public List<Owner> filterOwnersByBank(BankInstitution bankInstitution) {
        return ownerRepository.getOwnerAccountMap().entrySet().stream()
            .filter(entry -> entry.getValue().stream().anyMatch(account -> bankInstitutionRepository.getBank(account).equals(bankInstitution)))
            .map(Map.Entry::getKey)
            .toList();
    }

    @Override
    public List<BankAccount> filterAccountsInBank(BankInstitution bankInstitution) {
        return bankInstitutionRepository.getAllAccounts().stream()
            .filter(account -> bankInstitutionRepository.getBank(account).equals(bankInstitution))
            .toList();
    }

    @Override
    public BankInstitution getBankInstitutionById(int id) {
        return bankInstitutionRepository.getBankById(id);
    }
}
