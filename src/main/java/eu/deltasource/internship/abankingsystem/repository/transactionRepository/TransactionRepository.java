package eu.deltasource.internship.abankingsystem.repository.transactionRepository;

import eu.deltasource.internship.abankingsystem.model.Transaction;

public interface TransactionRepository {

    /**
     * Get selected transaction by it's id.
     *
     * @param id requested id.
     * @return transaction with id.
     */
    Transaction getTransactionById(int id);

    /**
     * Save transaction.
     */
    void createTransaction(Transaction transaction);

}
