package eu.deltasource.internship.abankingsystem.service;

import eu.deltasource.internship.abankingsystem.BankAccountToBankMapping;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.abankingsystem.enums.Taxes;
import eu.deltasource.internship.abankingsystem.exception.InsufficientAmountTransferException;
import eu.deltasource.internship.abankingsystem.exception.InsufficientAmountWithdrawException;
import eu.deltasource.internship.abankingsystem.exception.TransferBetweenNotCurrentAccountsException;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.model.Transaction;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Processing business logic for transactions.
 */
public class TransactionServiceImpl implements TransactionService {
    // Create methods that allow withdrawing money from and depositing to the
    // account. Those methods should be available for all accounts.

    // TODO: Add comments(Java Doc) to all methods and classes.
    // TODO: Make better namings for methods and variables.
    // TODO: Use separate methods to output String details.
    // FIXME: Remove exchange rates from ArrayList, already added from currency methods convertors.

    public BankAccountToBankMapping bankAccountToBankMapping = new BankAccountToBankMapping();

    /**
     * Method for automatically adding count of days to date.
     *
     * @param dayCount - get the number of days to count further from
     * @return - the date plus number of days(dayCount) further.
     */
    private LocalDate processLocalDate(int dayCount) {
        LocalDate time = LocalDate.now();
        return time.plusDays(dayCount);
    }

    /**
     * Method for creating transactions for transferring amount between amount and setting all details to it.
     *
     * @param fromAccount - account transferring the amount
     * @param toAccount - account receiving the amount
     * @param amount - request account transaction
     * @param TransactionType - Type of operation [transfer(between accounts), withdraw, deposit)]
     */
    private void makeTransactionTransfer(BankAccount fromAccount, BankAccount toAccount, double amount, String TransactionType) {

        var dayCount = bankAccountToBankMapping.getBank(fromAccount).getDayCountTime();
        Transaction transaction = new Transaction(fromAccount, toAccount, bankAccountToBankMapping.getBank(fromAccount), bankAccountToBankMapping.getBank(toAccount), amount, fromAccount.getCurrency(), toAccount.getCurrency(), processLocalDate(dayCount));
        transaction.setTransactionType(TransactionType);
        fromAccount.addTransaction(transaction);
        toAccount.addTransaction(transaction);
        fromAccount.setTransferStatement(fromAccount.getTransferStatement());
        toAccount.setTransferStatement(toAccount.getTransferStatement());

        dayCount++;
        bankAccountToBankMapping.getBank(fromAccount).setDayCountTime(dayCount);

    }

    /**
     * Method for saving the transaction in a statement for keeping history.
     *
     * @param bankAccount - account for making a deposit to account
     * @param depositAmount - required amount to deposit
     * @param TransactionType - set type of operation
     */
    private void makeTransactionForDepositOrWithdraw(BankAccount bankAccount, double depositAmount, String TransactionType) {

        // get the count of days from BankInstitution using map through the account that is assigned to that bank.
        var dayCount = bankAccountToBankMapping.getBank(bankAccount).getDayCountTime();
        BankInstitution bank = bankAccountToBankMapping.getBank(bankAccount);
        Transaction transaction = new Transaction(bankAccount, bank, depositAmount, bankAccount.getCurrency(), processLocalDate(dayCount));
        transaction.setTransactionType(TransactionType);
        bankAccount.addTransaction(transaction);
        dayCount++;
        bankAccountToBankMapping.getBank(bankAccount).setDayCountTime(dayCount);
    }

    /**
     * Prepare the amount to be subtracted and added to the corresponding accounts.
     *
     * @param fromAccount - account transferring the amount
     * @param toAccount - account receiving the amount
     * @param depositAmount - requested amount for transfer
     * @param taxRate - calculated taxes for the service
     */
    private void processTransferRequest(BankAccount fromAccount, BankAccount toAccount, Double depositAmount, Taxes taxRate) {

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
    public void withdraw(BankAccount bankAccount, Double withdrawAmount) throws InsufficientAmountWithdrawException {
        var amountAvailable = bankAccount.getAmountAvailable();

        double amountAfterExchange = exchangeRate(bankAccount, bankAccount.getCurrency()) * withdrawAmount;

        Double finaTrans = amountAfterExchange + taxes(bankAccount, Taxes.TAX_TO_THE_SAME_BANK);

        if(finaTrans.compareTo(amountAvailable) > 0) {
            throw new InsufficientAmountWithdrawException("Insufficient amount to withdraw!");
        }
        bankAccount.setAmountAvailable(amountAvailable - finaTrans);
        makeTransactionForDepositOrWithdraw(bankAccount, withdrawAmount, "Withdraw");
    }

    /**
     * Fetch the taxes of the Bank whose account is making the operation.
     * @param bankTaxes - Type of Tax to calculate
     */
    private double taxes(BankAccount bankAccount, Taxes bankTaxes) {

        // Get the bank that is assigned to account through map
        BankInstitution accountBank = bankAccountToBankMapping.getBank(bankAccount);
        return accountBank.getPriceList().get(bankTaxes);
    }

    /**
     * Fetch exchange rates
     *
     * @param bankAccount
     * @param depositCurrency
     */
    private double exchangeRate(BankAccount bankAccount, Currency depositCurrency) {
        var exchangeCurrency = bankAccount.getCurrency() + depositCurrency.getCurrency();
        ExchangeRate exchangeRateUpdate = ExchangeRate.valueOf(exchangeCurrency);

        // Get the exchange rate of the bank that is assigned to this account through map
        var bankExchangeRateMap = bankAccountToBankMapping.getBank(bankAccount).getExchangeRates();

        return bankExchangeRateMap.get(exchangeRateUpdate);
    }

    @Override
    public void transfer(BankAccount fromAccount, BankAccount toAccount, double depositAmount) throws TransferBetweenNotCurrentAccountsException, InsufficientAmountTransferException {

        if (fromAccount.getAccountKey() == 'C' && toAccount.getAccountKey() == 'C') {
            if (bankAccountToBankMapping.getBank(fromAccount) == bankAccountToBankMapping.getBank(toAccount)) {
                processTransferRequest(fromAccount, toAccount, depositAmount, Taxes.TAX_TO_THE_SAME_BANK);
            } else if (bankAccountToBankMapping.getBank(fromAccount) != bankAccountToBankMapping.getBank(toAccount)) {
                processTransferRequest(fromAccount, toAccount, depositAmount, Taxes.TAX_TO_DIFFERENT_BANK);
            }
            makeTransactionTransfer(fromAccount, toAccount, depositAmount, "Transfer");
        } else {
            throw new TransferBetweenNotCurrentAccountsException("One of the two accounts is not current!");
        }

    }

    @Override
    public void transactionHistory(BankAccount bankAccount) {
        System.out.println("\nTransaction Statement for " + bankAccount.getIban() + ": " + bankAccount.getTransferStatement());
    }

}
