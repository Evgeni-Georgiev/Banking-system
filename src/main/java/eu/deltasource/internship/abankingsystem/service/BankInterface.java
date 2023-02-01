package eu.deltasource.internship.abankingsystem.service;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.exception.InsufficientAmountTransferException;
import eu.deltasource.internship.abankingsystem.exception.InsufficientAmountWithdrawException;
import eu.deltasource.internship.abankingsystem.exception.TransferBetweenNotCurrentAccountsException;

public interface BankInterface {

    void transferBetweenAccounts(BankAccount fromAccount, BankAccount toAccount, double depositAmount) throws TransferBetweenNotCurrentAccountsException, InsufficientAmountTransferException;

    void deposit(BankAccount bankAccount, double depositAmount, String currency);

    void withDraw(BankAccount bankAccount, Double withdrawAmount) throws InsufficientAmountWithdrawException;

    void transactionHistory(BankAccount bankAccount);

}
