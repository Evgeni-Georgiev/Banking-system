package eu.deltasource.internship.abankingsystem.service;

import eu.deltasource.internship.abankingsystem.BankAccountToBankMapping;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.abankingsystem.enums.Taxes;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
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

    public BankAccountToBankMapping bankAccountToBankMapping = new BankAccountToBankMapping();

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

        var dayCount = bankAccountToBankMapping.getBank(fromAccount).getDayCountTime();

        Transaction transaction = new Transaction(fromAccount, toAccount, bankAccountToBankMapping.getBank(fromAccount), bankAccountToBankMapping.getBank(toAccount), amount, fromAccount.getCurrency(), toAccount.getCurrency(), processLocalDate(dayCount));

        transaction.setTransactionType(TransactionType);

        fromAccount.getTransferStatement().add(transaction);
        toAccount.getTransferStatement().add(transaction);

        fromAccount.setTransferStatement(fromAccount.getTransferStatement());
        toAccount.setTransferStatement(toAccount.getTransferStatement());

        dayCount++;
        bankAccountToBankMapping.getBank(fromAccount).setDayCountTime(dayCount);

    }

    private void makeTransactionForDepositOrWithdraw(BankAccount bankAccount, double depositAmount, String TransactionType) {
        var dayCount = bankAccountToBankMapping.getBank(bankAccount).getDayCountTime();
        BankInstitution bank = bankAccountToBankMapping.getBank(bankAccount);
        Transaction transaction = new Transaction(bankAccount, bank, depositAmount, bankAccount.getCurrency(), processLocalDate(dayCount));
        transaction.setTransactionType(TransactionType);
        bankAccount.getTransferStatement().add(transaction);
        dayCount++;
        bankAccountToBankMapping.getBank(bankAccount).setDayCountTime(dayCount);
    }

    private void transactionAmountBetweenTwoAccounts(BankAccount fromAccount, BankAccount toAccount, Double depositAmount, Taxes taxRate) {

        var source = fromAccount.getAmountAvailable();
        var target = toAccount.getAmountAvailable();

        double amountAfterExchange = exchangeRate(fromAccount, fromAccount.getCurrency()) * depositAmount;
        double amountAndTaxes = amountAfterExchange + taxes(fromAccount, taxRate);

        if(Objects.equals(fromAccount.getIban(), toAccount.getIban())) {
            throw new IllegalArgumentException("IBANs are the same!");
        }

        if(amountAndTaxes > source) {
            throw new InsufficientAmountTransferException("Insufficient amount to transfer!");
        }

        source -= amountAndTaxes;
        target += amountAfterExchange;

        fromAccount.setAmountAvailable(source);
        toAccount.setAmountAvailable(target);
    }

    @Override
    public void deposit(BankAccount bankAccount, double depositAmount, Currency depositCurrency) {
        var amountAvailable = bankAccount.getAmountAvailable();
        double amountAfterExchange = exchangeRate(bankAccount, depositCurrency) * depositAmount;
        double res = amountAfterExchange + taxes(bankAccount, Taxes.TAX_TO_THE_SAME_BANK);

        bankAccount.setAmountAvailable(amountAvailable + res);
        makeTransactionForDepositOrWithdraw(bankAccount, depositAmount, "Deposit");
    }

    @Override
    public void withDraw(BankAccount bankAccount, Double withdrawAmount) throws InsufficientAmountWithdrawException {
        var amountAvailable = bankAccount.getAmountAvailable();

        double amountAfterExchange = exchangeRate(bankAccount, bankAccount.getCurrency()) * withdrawAmount;

        Double finaTrans = amountAfterExchange + taxes(bankAccount, Taxes.TAX_TO_THE_SAME_BANK);

        if(finaTrans.compareTo(amountAvailable) > 0) {
            throw new InsufficientAmountWithdrawException("Insufficient amount to withdraw!");
        }
        bankAccount.setAmountAvailable(amountAvailable - finaTrans);
        makeTransactionForDepositOrWithdraw(bankAccount, withdrawAmount, "Withdraw");
    }

    private double taxes(BankAccount bankAccount, Taxes bankTaxes) {
        BankInstitution accountBank = bankAccountToBankMapping.getBank(bankAccount);
        return accountBank.getPriceList().get(bankTaxes);
    }

    private double exchangeRate(BankAccount bankAccount, Currency depositCurrency) {
        var exchangeCurrency = bankAccount.getCurrency() + depositCurrency.getCurrency();
        ExchangeRate exchangeRateUpdate = ExchangeRate.valueOf(exchangeCurrency);
        var bankExchangeRateMap = bankAccountToBankMapping.getBank(bankAccount).getExchangeRates();

        return bankExchangeRateMap.get(exchangeRateUpdate);
    }

    @Override
    public void transferBetweenAccounts(BankAccount fromAccount, BankAccount toAccount, double depositAmount) throws TransferBetweenNotCurrentAccountsException, InsufficientAmountTransferException {

        if (fromAccount.getAccountKey() == 'C' && toAccount.getAccountKey() == 'C') {
            if (bankAccountToBankMapping.getBank(fromAccount) == bankAccountToBankMapping.getBank(toAccount)) {
                transactionAmountBetweenTwoAccounts(fromAccount, toAccount, depositAmount, Taxes.TAX_TO_THE_SAME_BANK);
            } else if (bankAccountToBankMapping.getBank(fromAccount) != bankAccountToBankMapping.getBank(toAccount)) {
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
