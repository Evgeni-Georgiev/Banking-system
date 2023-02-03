package eu.deltasource.internship.abankingsystem.repository.transactionRepository;

import eu.deltasource.internship.abankingsystem.model.Transaction;

public interface TransactionRepository {

    Transaction getById(int id);

    void createTransaction(Transaction transaction);

}
