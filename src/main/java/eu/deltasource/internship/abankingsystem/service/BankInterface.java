package eu.deltasource.internship.abankingsystem.service;

import eu.deltasource.internship.abankingsystem.BankAccount;
import eu.deltasource.internship.abankingsystem.BankInstitution;
import eu.deltasource.internship.abankingsystem.Owner;

public interface BankInterface {

    void transferBetweenAccounts(BankAccount fromAccount, BankAccount toAccount, double depositAmount);

    void deposit(BankAccount bankAccount, double depositAmount, String currency);

    void withDraw(BankAccount bankAccount, double withdrawAmount);

    void transactionHistory(BankAccount bankAccount);

}
