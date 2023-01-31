package eu.deltasource.internship.abankingsystem.service;

import eu.deltasource.internship.abankingsystem.BankAccount;
import eu.deltasource.internship.abankingsystem.Currency;
import eu.deltasource.internship.abankingsystem.Taxes;
import eu.deltasource.internship.abankingsystem.Transaction;
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

        var exchangeCurrency = fromAccount.getCurrency() + toAccount.getCurrency();
        Currency currency = Currency.valueOf(exchangeCurrency);

        var exchangeRateMap = fromAccount.getBankInstitution().getExchangeRates().get(currency);
        double res = depositAmount * exchangeRateMap;

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
    public void deposit(BankAccount bankAccount, double depositAmount, String currency) {

        var amountAvailable = bankAccount.getAmountAvailable();

        var exchangeCurrency = bankAccount.getCurrency() + currency;
        Currency currencyUpdate = Currency.valueOf(exchangeCurrency);

        var exchangeRateMap = bankAccount.getBankInstitution().getExchangeRates().get(currencyUpdate);
        double res = depositAmount * exchangeRateMap;
        amountAvailable += res;
        bankAccount.setAmountAvailable(amountAvailable);
        makeTransactionForDepositOrWithdraw(bankAccount, depositAmount, "Deposit");
    }

    @Override
    public void withDraw(BankAccount bankAccount, Double withdrawAmount) throws InsufficientAmountWithdrawException {

        var amountAvailable = bankAccount.getAmountAvailable();
        var exchangeRateMap = bankAccount.getBankInstitution().getPriceList().get(Taxes.TAX_TO_THE_SAME_BANK);
        Double finaTrans = withdrawAmount + exchangeRateMap;
        if(finaTrans.compareTo(amountAvailable) > 0) {
            throw new InsufficientAmountWithdrawException("Insufficient amount to withdraw!");
        }
        amountAvailable -= finaTrans;
        bankAccount.setAmountAvailable(amountAvailable);
        makeTransactionForDepositOrWithdraw(bankAccount, withdrawAmount, "Withdraw");
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
