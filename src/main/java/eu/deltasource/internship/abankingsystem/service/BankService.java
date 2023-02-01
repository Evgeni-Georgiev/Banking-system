package eu.deltasource.internship.abankingsystem.service;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.enums.Taxes;
import eu.deltasource.internship.abankingsystem.model.Transaction;
import eu.deltasource.internship.abankingsystem.exception.InsufficientAmountTransferException;
import eu.deltasource.internship.abankingsystem.exception.InsufficientAmountWithdrawException;
import eu.deltasource.internship.abankingsystem.exception.TransferBetweenNotCurrentAccountsException;

import java.time.LocalDate;
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


    /**
     * Method for creating transactions and setting all details to it.
     *
     * @param fromAccount - account transferring the amount
     * @param toAccount - account receiving the amount
     * @param amount - request account transaction
     * @param TransactionType - Type of operation [transfer(between accounts), withdraw, deposit)]
     */
    private void makeTransaction(BankAccount fromAccount, BankAccount toAccount, double amount, String TransactionType) {

        var dayCount = fromAccount.getBankInstitution().getDayCountTime();

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
        Transaction transaction = new Transaction(bankAccount, bankAccount.getBankInstitution(), depositAmount, bankAccount.getCurrency(), processLocalDate(dayCount));
        transaction.setTransactionType(TransactionType);
        bankAccount.getTransferStatement().add(transaction);
        dayCount++;
        bankAccount.getBankInstitution().setDayCountTime(dayCount);
    }

    private void transactionAmountBetweenTwoAccounts(BankAccount fromAccount, BankAccount toAccount, Double depositAmount, Taxes taxRate) throws InsufficientAmountTransferException {

        var source = fromAccount.getAmountAvailable();
        var target = toAccount.getAmountAvailable();
        double res = depositAmount * exchangeRateAndTaxes(fromAccount, toAccount.getCurrency(), taxRate);

        var amountAndTaxes = depositAmount + fromAccount.getBankInstitution().getPriceList().get(taxRate);

        // Case: If currency between two accounts is not the same.
        if(Objects.equals(fromAccount.getIban(), toAccount.getIban())) {
            throw new IllegalArgumentException("IBANs are the same!");
        }

        if(amountAndTaxes > source) {
            throw new InsufficientAmountTransferException("Insufficient amount to transfer!");
        }

        source -= amountAndTaxes;
        target += res;

        fromAccount.setAmountAvailable(source);
        toAccount.setAmountAvailable(target);
    }

    @Override
    public void deposit(BankAccount bankAccount, double depositAmount, String depositCurrency) {
        var amountAvailable = bankAccount.getAmountAvailable();
        double res = depositAmount * exchangeRateAndTaxes(bankAccount, depositCurrency, Taxes.TAX_TO_THE_SAME_BANK);

//        amountAvailable += res;
        bankAccount.setAmountAvailable(amountAvailable + res);
//        bankAccount.setAmountAvailable(amountAvailable);
        makeTransactionForDepositOrWithdraw(bankAccount, depositAmount, "Deposit");
    }

    @Override
    public void withDraw(BankAccount bankAccount, Double withdrawAmount) throws InsufficientAmountWithdrawException {

        var amountAvailable = bankAccount.getAmountAvailable();
        Double finaTrans = withdrawAmount + exchangeRateAndTaxes(bankAccount, bankAccount.getCurrency(), Taxes.TAX_TO_THE_SAME_BANK);

        if(finaTrans.compareTo(amountAvailable) > 0) {
            throw new InsufficientAmountWithdrawException("Insufficient amount to withdraw!");
        }
//        amountAvailable -= finaTrans;
        bankAccount.setAmountAvailable(amountAvailable - finaTrans);
//        bankAccount.setAmountAvailable(amountAvailable);
        makeTransactionForDepositOrWithdraw(bankAccount, withdrawAmount, "Withdraw");
    }

    private double exchangeRateAndTaxes(BankAccount bankAccount, String depositCurrency, Taxes bankTaxes) {
        var exchangeCurrency = bankAccount.getCurrency() + depositCurrency;
        Currency currencyUpdate = Currency.valueOf(exchangeCurrency);
        return bankAccount.getBankInstitution().getExchangeRates().get(currencyUpdate) + bankAccount.getBankInstitution().getPriceList().get(bankTaxes);
    }

    @Override
    public void transferBetweenAccounts(BankAccount fromAccount, BankAccount toAccount, double depositAmount) throws TransferBetweenNotCurrentAccountsException, InsufficientAmountTransferException {

        if (fromAccount.getAccountKey() == 'C' && toAccount.getAccountKey() == 'C') {
            if (fromAccount.getBankInstitution() == toAccount.getBankInstitution()) {
                System.out.println("Banks are the same!");
                transactionAmountBetweenTwoAccounts(fromAccount, toAccount, depositAmount, Taxes.TAX_TO_THE_SAME_BANK);
            } else if (fromAccount.getBankInstitution() != toAccount.getBankInstitution()) {
                System.out.println("Banks are different!");
                transactionAmountBetweenTwoAccounts(fromAccount, toAccount, depositAmount, Taxes.TAX_TO_DIFFERENT_BANK);
            }
            makeTransaction(fromAccount, toAccount, depositAmount, "Transfer");
        } else {
            throw new TransferBetweenNotCurrentAccountsException("One of the two accounts is not current!");
        }

    }

    @Override
    public void transactionHistory(BankAccount bankAccount) {
        System.out.println("\nTransaction Statement for " + bankAccount.getIban() + ": " + bankAccount.getTransferStatement());
    }

}
