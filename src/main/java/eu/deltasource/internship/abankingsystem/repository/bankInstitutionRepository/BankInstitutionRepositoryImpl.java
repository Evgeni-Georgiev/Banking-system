package eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository;

import eu.deltasource.internship.abankingsystem.enums.Taxes;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BankInstitutionRepositoryImpl implements BankInstitutionRepository {

    private final Map<Integer, BankInstitution> bankInstitutionMap = new HashMap<>();

    private Map<Taxes, Double> priceList = new HashMap<>();

	private Map<BankInstitution, Map<Taxes, Double>> bankTaxMap = new HashMap<>();

//    private final List<BankAccount> bankAccounts = new ArrayList<>();

//	private final Map<BankInstitution, BankAccount> listOfBankAccountForBank = new HashMap<>();

//	@Override
//	public BankAccount getAccountListedToBank(BankInstitution bankInstitution) {
//		return listOfBankAccountForBank.get(bankInstitution);
//	}

//	public void addBankAccount(BankAccount bankAccount) {
//		bankAccounts.add(bankAccount);
//	}

//    @Override
//    public void addPriceListToMap(Taxes taxes, Double prices) {
//        priceList.put(taxes, prices);
//    }

//    @Override
//    public Double getPriceList(Taxes taxes) {
//        return priceList.get(taxes);
//    }

//    @Override
//    public Map<Taxes, Double> getPriceList() {
////        return priceList.values().stream().filter(pl ->
////            pl.getBankAccounts().contains(bankAccount)
////        ).findAny().orElse(null);
//
//        return null;
//    }

//    @Override
//    public void addPriceList(Taxes taxes, Double priceTax) {
//        priceList.put(taxes, priceTax);
//    }

    private int idCounter = 1;

    @Override
    public void addBankToMap(final BankInstitution bankInstitution) {
        // Cannot create new instance of bank inside this method:
        // new BankInstitution() cannot happen since
        // => final BankInstitution bankInstitution
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

//	public Map<Taxes, Double> filterTaxes(BankInstitution bank, double threshold) {
//		return getTaxMap(bank).entrySet().stream()
//			.filter(entry -> entry.getValue() >= threshold)
//			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//	}

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

    private static BankInstitutionRepository instance = null;

    public static BankInstitutionRepository getInstance() {
        if (instance == null) {
            instance = new BankInstitutionRepositoryImpl();
        }

        return instance;
    }

    private BankInstitutionRepositoryImpl() {}

}
