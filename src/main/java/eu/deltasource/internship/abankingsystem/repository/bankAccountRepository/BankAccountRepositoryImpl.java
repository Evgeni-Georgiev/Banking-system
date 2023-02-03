package eu.deltasource.internship.abankingsystem.repository.bankAccountRepository;

import eu.deltasource.internship.abankingsystem.model.BankAccount;

import java.util.HashMap;
import java.util.Map;

public class BankAccountRepositoryImpl implements BankAccountRepository {

    private final Map<String, BankAccount> bankAccountMap = new HashMap<>();

    private static BankAccountRepository instance = null;

    @Override
    public void addBankAccountToMap(final BankAccount bankAccount) {
        if (bankAccount != null) {
            bankAccountMap.put(bankAccount.getIban(), bankAccount);
        }
    }

    @Override
    public BankAccount getByIban(final String iban) {
        return bankAccountMap.get(iban);
    }

    private BankAccountRepositoryImpl(){}

    public static BankAccountRepository getInstance() {
        if (instance == null) {
            instance = new BankAccountRepositoryImpl();
        }

        return instance;
    }
}
