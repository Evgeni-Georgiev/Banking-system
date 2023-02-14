package eu.deltasource.internship.abankingsystem.service.transactionService;

import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankAccount;

public interface TransactionService {

    /**
     * Transfer requested amount between two accounts
     *
     * @param fromAccount source account
     * @param toAccount target account
     * @param depositAmount requested amount
     */
    void transfer(BankAccount fromAccount, BankAccount toAccount, double depositAmount);

    /**
     * Deposit amount to account
     *
     * @param bankAccount account to be deposited to
     * @param depositAmount requested amount
     * @param depositCurrency in what currency will the amount be transferred
     */
    void deposit(BankAccount bankAccount, double depositAmount, Currency depositCurrency);

    /**
     * Withdraw amount from account
     *
     * @param bankAccount account to process the withdraw
     * @param withdrawAmount requested amount
     */
    void withdraw(BankAccount bankAccount, Double withdrawAmount);

    /**
     * Output transactions for selected account
     *
     * @param bankAccount selected account to output transactions of
     */
//    void transactionHistory(BankAccount bankAccount);

}
