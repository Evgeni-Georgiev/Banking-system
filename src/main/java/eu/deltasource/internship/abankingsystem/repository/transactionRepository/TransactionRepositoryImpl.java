package eu.deltasource.internship.abankingsystem.repository.transactionRepository;

import eu.deltasource.internship.abankingsystem.model.Transaction;

import java.util.HashMap;
import java.util.Map;

public class TransactionRepositoryImpl implements TransactionRepository{

    final Map<Integer, Transaction> transactionMap = new HashMap<>();

    private static TransactionRepositoryImpl instance = null;

    private int idCounter = 1;

    private TransactionRepositoryImpl() {}

    public static TransactionRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new TransactionRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Transaction getTransactionById(final int id) {
        return transactionMap.get(id);
    }

    @Override
    public void createTransaction(Transaction transaction) {
        if(transaction != null) {
            transaction.setId(idCounter);
            transactionMap.put(idCounter, transaction);
            idCounter++;
        }
    }

}
