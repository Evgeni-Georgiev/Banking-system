package eu.deltasource.internship.abankingsystem.repository.transactionRepository;

import eu.deltasource.internship.abankingsystem.model.Transaction;

import java.util.HashMap;
import java.util.Map;

public class TransactionRepositoryImpl implements TransactionRepository{

    private final Map<Integer, Transaction> transactionMap = new HashMap<>();

    private static TransactionRepository instance = null;

    private int idCounter = 1;

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

    private TransactionRepositoryImpl(){}

    public static TransactionRepository getInstance() {
        if (instance == null) {
            instance = new TransactionRepositoryImpl();
        }
        return instance;
    }

}
