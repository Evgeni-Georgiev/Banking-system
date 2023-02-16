package eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository;

import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.abankingsystem.enums.TaxType;
import eu.deltasource.internship.abankingsystem.exception.BankInstitutionNotFoundException;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.model.Owner;
import eu.deltasource.internship.abankingsystem.model.Transaction;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toList;

public class BankInstitutionRepositoryImpl implements BankInstitutionRepository {

    final Map<Integer, BankInstitution> bankInstitutionMap = new HashMap<>();

    Map<BankInstitution, List<BankAccount>> bankAccountMap = new HashMap<>();

    Map<TaxType, Double> priceList = new HashMap<>();

	Map<BankInstitution, Map<TaxType, Double>> bankTaxMap = new HashMap<>();

    Map<BankInstitution, Map<ExchangeRate, Double>> bankExchangeRates = new HashMap<>();

    List<Transaction> allTransactions = new LinkedList<>();

    final List<BankAccount> allAccounts = new ArrayList<>();

    final List<Owner> allOwners = new ArrayList<>();

    private int idCounter = 1;

    private static BankInstitutionRepositoryImpl instance = null;

    public static BankInstitutionRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new BankInstitutionRepositoryImpl();
        }

        return instance;
    }

    private BankInstitutionRepositoryImpl() {}

    @Override
    public Map<ExchangeRate, Double> getExchangeRates(BankInstitution bankInstitution) {
        return unmodifiableMap(bankExchangeRates).get(bankInstitution);
    }

    @Override
    public void addExchangeRatesToBank(BankInstitution bank, Map<ExchangeRate, Double> exchangeRates) {
        Map<ExchangeRate, Double> bankRates = new HashMap<>();
        for (ExchangeRate rate : exchangeRates.keySet()) {
            bankRates.put(rate, exchangeRates.get(rate));
        }
        bankExchangeRates.put(bank, bankRates);
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        return unmodifiableList(allAccounts);
    }

    @Override
    public void addAccount(BankAccount bankAccount) {
        allAccounts.add(bankAccount);
    }

    @Override
    public void addOwner(Owner owner) {
        allOwners.add(owner);
    }

    @Override
    public List<Owner> getAllOwners(BankInstitution bankInstitution) {
        return unmodifiableList(allOwners);
    }

    @Override
    public List<Transaction> getAllTransactions(BankInstitution bankInstitution) {
        return allTransactions.stream()
            .filter(transaction -> transaction.getSourceBank().equals(bankInstitution) || transaction.getTargetBank().equals(bankInstitution))
            .collect(toList());
    }

    @Override
    public void addTransaction(Transaction transaction) {
        allTransactions.add(transaction);
    }

    @Override
    public void addBankToMap(final BankInstitution bankInstitution) {
        if(bankInstitution != null) {
            bankInstitutionMap.put(idCounter, bankInstitution);
            idCounter++;
        }
    }

	@Override
	public void addTaxesToBank(BankInstitution bank, Map<TaxType, Double> taxes, List<TaxType> taxTypeToAdd) {
		Map<TaxType, Double> filteredTaxes = taxes.entrySet().stream()
			.filter(entry -> taxTypeToAdd.contains(entry.getKey()))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		bankTaxMap.put(bank, filteredTaxes);
	}

	@Override
	public void addTaxToBankMap(BankInstitution bank, Map<TaxType, Double> taxMap) {
		bankTaxMap.put(bank, taxMap);
	}

	@Override
	public Double getTaxByBank(BankInstitution bank, TaxType taxType) {
		return bankTaxMap.get(bank).get(taxType);
	}

	@Override
	public Map<TaxType, Double> getTaxMap(BankInstitution bank) {
		return bankTaxMap.get(bank);
	}

	@Override
	public Double getTaxByTax(TaxType taxType) {
		return priceList.get(taxType);
	}

	@Override
	public Map<TaxType, Double> findTaxesByBank(BankInstitution bank) {
		return bankTaxMap.getOrDefault(bank, new HashMap<>());
	}

    @Override
    public void addAccountToBank(BankInstitution bankInstitution, List<BankAccount> bankAccount) {
        bankAccountMap.put(bankInstitution, bankAccount);
    }

    @Override
    public BankInstitution getBank(BankAccount bankAccount) {
        Optional<BankInstitution> bankOptional = bankAccountMap.entrySet().stream()
            .filter(entry -> entry.getValue().contains(bankAccount))
            .map(Map.Entry::getKey)
            .findFirst();
        return bankOptional.orElseThrow(() -> new BankInstitutionNotFoundException(bankOptional));
    }

    @Override
    public BankInstitution getBankById(int id) {
        return bankInstitutionMap.get(id);
    }

}
