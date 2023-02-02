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

        BankInstitution bank = bankAccountToBankMapping.getBank(fromAccount);
//        bankAccountToBankMapping.addMapping(fromAccount, bankAccountToBankMapping.getBank(fromAccount));
//        bankAccountToBankMapping.addMapping(toAccount, bankAccountToBankMapping.getBank(toAccount));

//        var dayCount = fromAccount.getBankInstitution().getDayCountTime();
        bankAccountToBankMapping.addAccountToBankMapping(fromAccount, bank); // void
        var dayCount = bankAccountToBankMapping.getBank(fromAccount).getDayCountTime();

//        List<BankAccount> keys = mapping.keySet().stream().toList();

        // mapping.get(fromAccount) ===> fromAccount.getBankInstitution()

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
//        bankAccountToBankMapping.addMapping(bankAccount, bankAccountToBankMapping.getBank(bankAccount));

        var dayCount = bankAccountToBankMapping.getBank(bankAccount).getDayCountTime();
        BankInstitution bank = bankAccountToBankMapping.getBank(bankAccount);
        Transaction transaction = new Transaction(bankAccount, bank, depositAmount, bankAccount.getCurrency(), processLocalDate(dayCount));
        transaction.setTransactionType(TransactionType);
        bankAccount.getTransferStatement().add(transaction);
        dayCount++;
        bankAccountToBankMapping.getBank(bankAccount).setDayCountTime(dayCount);
    }

    private void transactionAmountBetweenTwoAccounts(BankAccount fromAccount, BankAccount toAccount, Double depositAmount, Taxes taxRate) {
//        bankAccountToBankMapping.addMapping(fromAccount, bankAccountToBankMapping.getBank(fromAccount));
//        bankAccountToBankMapping.addMapping(toAccount, bankAccountToBankMapping.getBank(toAccount));

        // If not added will return: null
        BankInstitution bankSource = bankAccountToBankMapping.getBank(fromAccount);
        BankInstitution bankTarget = bankAccountToBankMapping.getBank(toAccount);
        bankAccountToBankMapping.addAccountToBankMapping(fromAccount, bankSource);
        bankAccountToBankMapping.addAccountToBankMapping(toAccount, bankTarget);
        // If not added will return: null

        var source = fromAccount.getAmountAvailable();
        var target = toAccount.getAmountAvailable();

        double amountAfterExchange = exchangeRate(fromAccount, fromAccount.getCurrency()) * depositAmount;
//        double res = amountAfterExchange + taxes(fromAccount, taxRate);

//        double res = depositAmount * exchangeRateAndTaxes(fromAccount, toAccount.getCurrency(), taxRate);
//        double res = exchangeRate(fromAccount, fromAccount.getCurrency()) * depositAmount;
//        var amountAndTaxes = depositAmount + bank.getPriceList().get(taxRate);
        double amountAndTaxes = amountAfterExchange + taxes(fromAccount, taxRate);

        // Case: If currency between two accounts is not the same.
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
//        bankAccountToBankMapping.addMapping(bankAccount, bankAccountToBankMapping.getBank(bankAccount));

//        BankAccountToBankMapping bankAccountToBankMapping1 = new BankAccountToBankMapping();
//        bankAccountToBankMapping1.addMapping(bankAccount, bankAccountToBankMapping.getBank(bankAccount));
//        var ress = bankAccountToBankMapping1.getBank(bankAccount);

        BankInstitution bank = bankAccountToBankMapping.getBank(bankAccount);
//        bankAccountToBankMapping.addMapping(fromAccount, bankAccountToBankMapping.getBank(fromAccount));
//        bankAccountToBankMapping.addMapping(toAccount, bankAccountToBankMapping.getBank(toAccount));

//        var dayCount = fromAccount.getBankInstitution().getDayCountTime();
        bankAccountToBankMapping.addAccountToBankMapping(bankAccount, bank);

//        BankInstitution bank = bankAccountToBankMapping.getBank(bankAccount);
        var amountAvailable = bankAccount.getAmountAvailable();
        double amountAfterExchange = exchangeRate(bankAccount, depositCurrency) * depositAmount;
        double res = amountAfterExchange + taxes(bankAccount, Taxes.TAX_TO_THE_SAME_BANK);

//        amountAvailable += res;
        bankAccount.setAmountAvailable(amountAvailable + res);
//        bankAccount.setAmountAvailable(amountAvailable);
        makeTransactionForDepositOrWithdraw(bankAccount, depositAmount, "Deposit");
    }

    @Override
    public void withDraw(BankAccount bankAccount, Double withdrawAmount) throws InsufficientAmountWithdrawException {
        // If not added will return: null
        BankInstitution bank = bankAccountToBankMapping.getBank(bankAccount);
        bankAccountToBankMapping.addAccountToBankMapping(bankAccount, bank);
        // If not added will return: null

        var amountAvailable = bankAccount.getAmountAvailable();

        double amountAfterExchange = exchangeRate(bankAccount, bankAccount.getCurrency()) * withdrawAmount;

        Double finaTrans = amountAfterExchange + taxes(bankAccount, Taxes.TAX_TO_THE_SAME_BANK);

        if(finaTrans.compareTo(amountAvailable) > 0) {
            throw new InsufficientAmountWithdrawException("Insufficient amount to withdraw!");
        }
//        amountAvailable -= finaTrans;
        bankAccount.setAmountAvailable(amountAvailable - finaTrans);
//        bankAccount.setAmountAvailable(amountAvailable);
        makeTransactionForDepositOrWithdraw(bankAccount, withdrawAmount, "Withdraw");
    }

    private double taxes(BankAccount bankAccount, Taxes bankTaxes) {
        BankInstitution accountBank = bankAccountToBankMapping.getBank(bankAccount);
//        var bank1 = getBankForAccount(bankAccount).getExchangeRates();
        return accountBank.getPriceList().get(bankTaxes);
    }

    private double exchangeRate(BankAccount bankAccount, Currency depositCurrency) {
        var exchangeCurrency = bankAccount.getCurrency() + depositCurrency.getCurrency();
        ExchangeRate exchangeRateUpdate = ExchangeRate.valueOf(exchangeCurrency);
//        BankInstitution accountBank = bankAccountToBankMapping.getBank(bankAccount);

        BankInstitution bank = bankAccountToBankMapping.getBank(bankAccount);
//        addAccountToBankMapping(bankAccount, bank);
        var bank1 = bankAccountToBankMapping.getBank(bankAccount).getExchangeRates();

        return bank1.get(exchangeRateUpdate);
    }

    @Override
    public void transferBetweenAccounts(BankAccount fromAccount, BankAccount toAccount, double depositAmount) throws TransferBetweenNotCurrentAccountsException, InsufficientAmountTransferException {
//        bankAccountToBankMapping.addMapping(fromAccount, bankAccountToBankMapping.getBank(fromAccount));
//        bankAccountToBankMapping.addMapping(toAccount, bankAccountToBankMapping.getBank(toAccount));

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
