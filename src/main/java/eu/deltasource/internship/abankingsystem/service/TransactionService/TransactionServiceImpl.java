package eu.deltasource.internship.abankingsystem.service.TransactionService;

import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.abankingsystem.enums.Taxes;
import eu.deltasource.internship.abankingsystem.exception.InsufficientAmountTransferException;
import eu.deltasource.internship.abankingsystem.exception.InsufficientAmountWithdrawException;
import eu.deltasource.internship.abankingsystem.exception.TransferBetweenNotCurrentAccountsException;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Transaction;
import eu.deltasource.internship.abankingsystem.repository.bankAccountRepository.BankAccountRepository;
import eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository.BankInstitutionRepository;
import eu.deltasource.internship.abankingsystem.repository.transactionRepository.TransactionRepository;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Processing business logic for transactions.
 */
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final BankInstitutionRepository bankInstitutionRepository;

    private final BankAccountRepository bankAccountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, BankInstitutionRepository bankInstitutionRepository, BankAccountRepository bankAccountRepository) {
        this.bankInstitutionRepository = bankInstitutionRepository;
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

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
     * @param fromAccount     - account transferring the amount
     * @param toAccount       - account receiving the amount
     * @param amount          - request account transaction
     * @param TransactionType - Type of operation [transfer(between accounts), withdraw, deposit)]
     */
    private void makeTransactionTransfer(BankAccount fromAccount, BankAccount toAccount, double amount, String TransactionType) {

        var dayCount = bankInstitutionRepository.getBank(fromAccount).getDayCountTime();
        Transaction transaction = new Transaction.TransactionBuilder(
            fromAccount,
            bankInstitutionRepository.getBank(fromAccount),
            amount,
            fromAccount.getCurrency(),
            processLocalDate(dayCount))
            .setTargetAccount(toAccount)
            .setTargetBank(bankInstitutionRepository.getBank(toAccount))
            .setTargetCurrency(toAccount.getCurrency())
            .build();

        transaction.setTransactionType(TransactionType);

        var sourceAccount = bankAccountRepository.getByIban(fromAccount.getIban());
        var targetAccount = bankAccountRepository.getByIban(toAccount.getIban());
//        bankAccountRepository.getByIban(fromAccount.getIban()).addTransaction(transaction);
//        bankAccountRepository.getByIban(toAccount.getIban()).addTransaction(transaction);
        bankAccountRepository.addTransaction(transaction);
//        fromAccount.addTransaction(transaction);
//        toAccount.addTransaction(transaction);


//        bankAccountRepository.getByIban(fromAccount.getIban());

//        fromAccount.setTransferStatement(fromAccount.getTransferStatement()); // delete
//        toAccount.setTransferStatement(toAccount.getTransferStatement()); // delete

        transactionRepository.createTransaction(transaction);

        dayCount++;
        bankInstitutionRepository.getBank(fromAccount).setDayCountTime(dayCount);
    }

    /**
     * Method for saving the transaction in a statement for keeping history.
     *
     * @param bankAccount     - account for making a deposit to account
     * @param depositAmount   - required amount to deposit
     * @param TransactionType - set type of operation
     */
    private void makeTransactionForDepositOrWithdraw(BankAccount bankAccount, double depositAmount, String TransactionType) {

        // Get the count of days from BankInstitution using map through the account that is assigned to that bank.
        var dayCount = bankInstitutionRepository.getBank(bankAccount).getDayCountTime();
        Transaction transaction = new Transaction.TransactionBuilder(
            bankAccount,
            bankInstitutionRepository.getBank(bankAccount),
            depositAmount,
            bankAccount.getCurrency(),
            processLocalDate(dayCount))
            .build();
        transaction.setTransactionType(TransactionType);

//        bankAccount.addTransaction(transaction);

//        bankAccount.setTransferStatement(bankAccount.getTransferStatement()); // delete

        bankAccountRepository.addTransaction(transaction);

//        bankAccountRepository.getByIban(bankAccount.getIban()).addTransaction(transaction);


        transactionRepository.createTransaction(transaction);
        dayCount++;
        bankInstitutionRepository.getBank(bankAccount).setDayCountTime(dayCount);
    }

    /**
     * Prepare the amount to be subtracted and added to the corresponding accounts.
     *
     * @param fromAccount   - account transferring the amount
     * @param toAccount     - account receiving the amount
     * @param depositAmount - requested amount for transfer
     * @param taxRate       - calculated taxes for the service
     */
    private void processTransferRequest(BankAccount fromAccount, BankAccount toAccount, Double depositAmount, Taxes taxRate) {

        var source = fromAccount.getAmountAvailable();
        var target = toAccount.getAmountAvailable();

//		var sourceAccountsBank = bankInstitutionRepository.getBank(fromAccount).getPriceList();
		var sourceAccountsBank = bankInstitutionRepository.getBank(fromAccount);

        double amountAfterExchange = exchangeRate(fromAccount, fromAccount.getCurrency()) * depositAmount;
//        double amountAndTaxes = amountAfterExchange + taxes(fromAccount, taxRate);
        double amountAndTaxes = amountAfterExchange + bankInstitutionRepository.getTaxMap(sourceAccountsBank).get(taxRate);
        if (Objects.equals(fromAccount.getIban(), toAccount.getIban())) {
            throw new IllegalArgumentException("IBANs are the same!");
        }
        if (amountAndTaxes > source) {
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
//        double res = amountAfterExchange + taxes(bankAccount, Taxes.TAX_TO_THE_SAME_BANK);
        double res = amountAfterExchange + bankInstitutionRepository.getTaxMap(bankInstitutionRepository.getBank(bankAccount)).get(Taxes.TAX_TO_THE_SAME_BANK);
        bankAccount.setAmountAvailable(amountAvailable + res);
        makeTransactionForDepositOrWithdraw(bankAccount, depositAmount, "Deposit");
    }

    @Override
    public void withdraw(BankAccount bankAccount, Double withdrawAmount) throws InsufficientAmountWithdrawException {

        var amountAvailable = bankAccount.getAmountAvailable();
        double amountAfterExchange = exchangeRate(bankAccount, bankAccount.getCurrency()) * withdrawAmount;
//        Double finaTrans = amountAfterExchange + taxes(bankAccount, Taxes.TAX_TO_THE_SAME_BANK);
        Double finaTrans = amountAfterExchange + bankInstitutionRepository.getTaxMap(bankInstitutionRepository.getBank(bankAccount)).get(Taxes.TAX_TO_THE_SAME_BANK);
        if (finaTrans.compareTo(amountAvailable) > 0) {
            throw new InsufficientAmountWithdrawException("Insufficient amount to withdraw!");
        }
        bankAccount.setAmountAvailable(amountAvailable - finaTrans);
        makeTransactionForDepositOrWithdraw(bankAccount, withdrawAmount, "Withdraw");
    }

//    /**
//     * Fetch the taxes of the Bank whose account is making the operation.
//     *
//     * @param bankTaxes - Type of Tax to calculate
//     */
//    private double taxes(BankAccount bankAccount, Taxes bankTaxes) {
//
//        // Get the bank that is assigned to account through map
//        BankInstitution accountBank = bankInstitutionRepository.getBank(bankAccount);
////		bankInstitutionRepository.addTaxToBankMap(accountBank, bankTaxes);
//
////		return bankInstitutionRepository.getTaxByBank(accountBank, bankTaxes);
//		return bankInstitutionRepository.getTaxMap(accountBank);
////		return bankInstitutionRepository.getTaxByTax(bankTaxes);
//
////        return accountBank.getPriceList().get(bankTaxes);
//    }

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
        var bankExchangeRateMap = bankInstitutionRepository.getBank(bankAccount).getExchangeRates();
        return bankExchangeRateMap.get(exchangeRateUpdate);
    }

    @Override
    public void transfer(BankAccount fromAccount, BankAccount toAccount, double depositAmount) throws TransferBetweenNotCurrentAccountsException, InsufficientAmountTransferException {

        if (fromAccount.getAccountKey() == 'C' && toAccount.getAccountKey() == 'C') {
            if (bankInstitutionRepository.getBank(fromAccount) == bankInstitutionRepository.getBank(toAccount)) {
                processTransferRequest(fromAccount, toAccount, depositAmount, Taxes.TAX_TO_THE_SAME_BANK);
            } else if (bankInstitutionRepository.getBank(fromAccount) != bankInstitutionRepository.getBank(toAccount)) {
                processTransferRequest(fromAccount, toAccount, depositAmount, Taxes.TAX_TO_DIFFERENT_BANK);
            }
            makeTransactionTransfer(fromAccount, toAccount, depositAmount, "Transfer");
        } else {
            throw new TransferBetweenNotCurrentAccountsException("One of the two accounts is not current!");
        }
    }

//    @Override
//    public void transactionHistory(BankAccountRepository bankAccount) {
//
//        System.out.println("\nTransaction Statement for " + bankAccount.getByIban() + ": " + bankAccount.getTransferStatement());
//    }

}
