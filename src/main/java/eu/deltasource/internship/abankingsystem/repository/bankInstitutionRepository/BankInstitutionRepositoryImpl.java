package eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository;

import eu.deltasource.internship.abankingsystem.enums.Taxes;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BankInstitutionRepositoryImpl implements BankInstitutionRepository {

    final Map<Integer, BankInstitution> bankInstitutionMap = new HashMap<>();

    Map<Taxes, Double> priceList = new HashMap<>();

	Map<BankInstitution, Map<Taxes, Double>> bankTaxMap = new HashMap<>();

    private int idCounter = 1;

    @Override
    public void addBankToMap(final BankInstitution bankInstitution) {
        if(bankInstitution != null) {
            bankInstitutionMap.put(idCounter, bankInstitution);
            idCounter++;
        }
    }

	@Override
	public void filterAndAddTaxesToBank(BankInstitution bank, Map<Taxes, Double> taxes, List<Taxes> taxesToAdd) {
		Map<Taxes, Double> filteredTaxes = taxes.entrySet().stream()
			.filter(entry -> taxesToAdd.contains(entry.getKey()))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		bankTaxMap.put(bank, filteredTaxes);
	}

	@Override
	public void addTaxToBankMap(BankInstitution bank, Map<Taxes, Double> taxMap) {
		bankTaxMap.put(bank, taxMap);
	}

	@Override
	public Double getTaxByBank(BankInstitution bank, Taxes taxes) {
		return bankTaxMap.get(bank).get(taxes);
	}

	@Override
	public Map<Taxes, Double> getTaxMap(BankInstitution bank) {
		return bankTaxMap.get(bank);
	}

	@Override
	public Double getTaxByTax(Taxes taxes) {
		return priceList.get(taxes);
	}

	@Override
	public Map<Taxes, Double> findTaxesByBank(BankInstitution bank) {
		return bankTaxMap.getOrDefault(bank, new HashMap<>());
	}

    @Override
    public void addAccountToBank(BankInstitution bankInstitution, BankAccount bankAccount) {
        bankInstitution.addAccountToBank(bankAccount);
    }

    @Override
    public BankInstitution getBank(BankAccount bankAccount) {
        return bankInstitutionMap.values().stream().filter(bi ->
            bi.getBankAccounts().contains(bankAccount)
        ).findAny().orElse(null);
    }

    @Override
    public BankInstitution getBankById(int id) {
        return bankInstitutionMap.get(id);
    }

    private static BankInstitutionRepositoryImpl instance = null;

    public static BankInstitutionRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new BankInstitutionRepositoryImpl();
        }

        return instance;
    }

    private BankInstitutionRepositoryImpl() {}

}
