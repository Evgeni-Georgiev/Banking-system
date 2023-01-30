package eu.deltasource.internship.abankingsystem.service;

import eu.deltasource.internship.abankingsystem.BankAccount;
import eu.deltasource.internship.abankingsystem.BankInstitution;
import eu.deltasource.internship.abankingsystem.Owner;
import eu.deltasource.internship.abankingsystem.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BankService implements BankInterface {
    // Create methods that allow withdrawing money from and depositing to the
    // account. Those methods should be available for all accounts.

    // TODO: Add comments(Java Doc) to all methods and classes.
    // TODO: Make better namings for methods and variables.
    // TODO: Use separate methods to output String details.
    // FIXME: Remove exchange rates from ArrayList, already added from currency methods convertors.


    private LocalDate processLocalDate(int dayCount) {
        LocalDate time = LocalDate.now();
        return time.plusDays(dayCount);
    }


    private void makeTransaction(BankAccount fromAccount, BankAccount toAccount, double amount, String TransactionType) {

        var dayCount = fromAccount.getBankInstitution().getDayCountTime();

//        LocalDate time = LocalDate.now();
//        LocalDate tomorrowDate = time.plusDays(dayCount);

        Transaction transaction = new Transaction(fromAccount, toAccount, fromAccount.getBankInstitution(), toAccount.getBankInstitution(), amount, fromAccount.getCurrency(), toAccount.getCurrency(), processLocalDate(dayCount));

        transaction.setTransactionType(TransactionType);

        fromAccount.getTransferStatement().add(transaction);
        toAccount.getTransferStatement().add(transaction);

        fromAccount.setTransferStatement(fromAccount.getTransferStatement());
        toAccount.setTransferStatement(toAccount.getTransferStatement());

        dayCount++;
        fromAccount.getBankInstitution().setDayCountTime(dayCount);

    }

    private void makeTransactionForDepositOrWithdraw(BankAccount bankAccount, double depositAmount, String TransactionType) {

        var dayCount = bankAccount.getBankInstitution().getDayCountTime();

//        LocalDate time = LocalDate.now();
//        LocalDate tomorrowDate = time.plusDays(dayCount);

        Transaction transaction = new Transaction(bankAccount, bankAccount.getBankInstitution(), depositAmount, bankAccount.getCurrency(), processLocalDate(dayCount));

        transaction.setTransactionType(TransactionType);
        bankAccount.getTransferStatement().add(transaction);
//        bankAccount.setTransferStatement(bankAccount.getTransferStatement());

        dayCount++;
        bankAccount.getBankInstitution().setDayCountTime(dayCount);
    }

    private void transactionAmountBetweenTwoAccounts(BankAccount fromAccount, BankAccount toAccount, double depositAmount, String taxRate) {

        var source = fromAccount.getAmountAvailable();
        var target = toAccount.getAmountAvailable();
        var exchangeRateMap = fromAccount.getBankInstitution().getExchangeRates().get(fromAccount.getCurrency() + toAccount.getCurrency());
        double res = depositAmount * exchangeRateMap;

        var amountAndTaxes = depositAmount + fromAccount.getBankInstitution().getPriceList().get(taxRate);

        // Case: If currency between two accounts is not the same.
        if(Objects.equals(fromAccount.getIban(), toAccount.getIban())) {
            throw new IllegalArgumentException("IBANs are the same!");
        }

//        if (!Objects.equals(fromAccount.getCurrency(), toAccount.getCurrency())) {
//            source -= amountAndTaxes;
//            target += res;
//        } else {
//            source -= amountAndTaxes;
//            target += depositAmount;
//        }

        source -= amountAndTaxes;
        target += res;

        fromAccount.setAmountAvailable(source);
        toAccount.setAmountAvailable(target);
    }

    @Override
    public void deposit(BankAccount bankAccount, double depositAmount, String currency) {

        var amountAvailable = bankAccount.getAmountAvailable();
        var exchangeRateMap = bankAccount.getBankInstitution().getExchangeRates().get(bankAccount.getCurrency() + currency);
        double res = depositAmount * exchangeRateMap;
//        if (bankAccount.getAccountKey() == 'C' || bankAccount.getAccountKey() == 'S') {
            amountAvailable += res;
//        }
        bankAccount.setAmountAvailable(amountAvailable);
        makeTransactionForDepositOrWithdraw(bankAccount, depositAmount, "Deposit");
    }

    @Override
    public void withDraw(BankAccount bankAccount, double withdrawAmount) {

        var amountAvailable = bankAccount.getAmountAvailable();
        var exchangeRateMap = bankAccount.getBankInstitution().getPriceList().get("Tax to the same bank");
        var finaTrans = withdrawAmount + exchangeRateMap;
//        if (bankAccount.getAccountKey() == 'C' || bankAccount.getAccountKey() == 'S') {
            amountAvailable -= finaTrans;
//        }
        bankAccount.setAmountAvailable(amountAvailable);
        // Taxes are not inflicted
        makeTransactionForDepositOrWithdraw(bankAccount, withdrawAmount, "Withdraw");
    }

    @Override
    public void transferBetweenAccounts(BankAccount fromAccount, BankAccount toAccount, double depositAmount) {

        if (fromAccount.getAccountKey() == 'C' && toAccount.getAccountKey() == 'C') {
            // Case: Same bank institutions

            // FIXME: Extract the body of if statement in new method and reuse it.
            if (fromAccount.getBankInstitution() == toAccount.getBankInstitution()) {
                System.out.println("Banks are the same!");
                transactionAmountBetweenTwoAccounts(fromAccount, toAccount, depositAmount, "Tax to the same bank");
                makeTransaction(fromAccount, toAccount, depositAmount, "Transfer");
            } else if (fromAccount.getBankInstitution() != toAccount.getBankInstitution()) {
                System.out.println("Banks are different!");
                transactionAmountBetweenTwoAccounts(fromAccount, toAccount, depositAmount, "Tax to different bank");
                makeTransaction(fromAccount, toAccount, depositAmount, "Transfer");
            }
        }
//        makeTransaction(fromAccount, toAccount, depositAmount, "Transfer");

    }

    @Override
    public void transactionHistory(BankAccount bankAccount) {
        System.out.println("\nTransaction Statement for " + bankAccount.getIban() + ": " + bankAccount.getTransferStatement());
    }


}
