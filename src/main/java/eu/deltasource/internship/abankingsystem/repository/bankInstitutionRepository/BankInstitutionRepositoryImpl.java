package eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankInstitutionRepositoryImpl implements BankInstitutionRepository {

    private final Map<Integer, BankInstitution> bankInstitutionMap = new HashMap<>();

    private final List<BankAccount> bankAccounts = new ArrayList<>();

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
