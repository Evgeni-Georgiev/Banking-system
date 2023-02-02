package eu.deltasource.internship.abankingsystem.service;

import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankAccount;

public interface BankInterface {

    void transferBetweenAccounts(BankAccount fromAccount, BankAccount toAccount, double depositAmount);

    void deposit(BankAccount bankAccount, double depositAmount, Currency depositCurrency);

    void withDraw(BankAccount bankAccount, Double withdrawAmount);

    void transactionHistory(BankAccount bankAccount);

}
