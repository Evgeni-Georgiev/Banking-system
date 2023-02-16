package eu.deltasource.internship.abankingsystem.service.transactionService;

import eu.deltasource.internship.abankingsystem.enums.*;
import eu.deltasource.internship.abankingsystem.exception.DuplicateIbanException;
import eu.deltasource.internship.abankingsystem.exception.InsufficientAmountTransferException;
import eu.deltasource.internship.abankingsystem.exception.InsufficientAmountWithdrawException;
import eu.deltasource.internship.abankingsystem.exception.TransferBetweenNotCurrentAccountsException;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.model.Transaction;
import eu.deltasource.internship.abankingsystem.repository.bankAccountRepository.BankAccountRepository;
import eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository.BankInstitutionRepository;
import eu.deltasource.internship.abankingsystem.repository.transactionRepository.TransactionRepository;

import java.time.LocalDate;
import java.util.Map;
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

    @Override
    public void transfer(BankAccount fromAccount, BankAccount toAccount, double depositAmount) {
        if (Objects.equals(fromAccount.getAccountKey(), AccountType.CURRENT_ACCOUNT) && Objects.equals(toAccount.getAccountKey(), AccountType.CURRENT_ACCOUNT)) {
            if (bankInstitutionRepository.getBank(fromAccount) == bankInstitutionRepository.getBank(toAccount)) {
                processTransferRequest(fromAccount, toAccount, depositAmount, TaxType.TAX_TO_THE_SAME_BANK);
            } else if (bankInstitutionRepository.getBank(fromAccount) != bankInstitutionRepository.getBank(toAccount)) {
                processTransferRequest(fromAccount, toAccount, depositAmount, TaxType.TAX_TO_DIFFERENT_BANK);
            }
            makeTransactionTransfer(fromAccount, toAccount, depositAmount);
        } else {
            throw new TransferBetweenNotCurrentAccountsException(fromAccount.getIban() + ", " + fromAccount.getAccountKey() + "; " + toAccount.getIban() + ", " + toAccount.getAccountKey());
        }
    }

    @Override
    public void deposit(BankAccount bankAccount, double depositAmount, Currency depositCurrency) {
        BankInstitution bankOfAccount = bankInstitutionRepository.getBank(bankAccount);
        double amountAvailable = bankAccount.getAmountAvailable();
        double amountAfterExchangeRateApplied = exchangeRate(bankAccount, depositCurrency) * depositAmount;
        double transactionAmount = amountAvailable + amountAfterExchangeRateApplied + bankInstitutionRepository.getTaxMap(bankOfAccount).get(TaxType.TAX_TO_THE_SAME_BANK);
        bankAccount.setAmountAvailable(transactionAmount);
        makeTransactionForDepositOrWithdraw(bankAccount, depositAmount, TransactionType.DEPOSIT);
    }

    @Override
    public void withdraw(BankAccount bankAccount, Double withdrawAmount) {
        double amountAvailable = bankAccount.getAmountAvailable();
        double amountAfterExchange = exchangeRate(bankAccount, bankAccount.getCurrency()) * withdrawAmount;
        Double finaTrans = amountAfterExchange + bankInstitutionRepository.getTaxMap(bankInstitutionRepository.getBank(bankAccount)).get(TaxType.TAX_TO_THE_SAME_BANK);
        if (finaTrans.compareTo(amountAvailable) > 0) {
            throw new InsufficientAmountWithdrawException(String.valueOf(amountAvailable));
        }
        bankAccount.setAmountAvailable(amountAvailable - finaTrans);
        makeTransactionForDepositOrWithdraw(bankAccount, withdrawAmount, TransactionType.WITHDRAW);
    }

    /**
     * Method for creating transactions for transferring amount between amount and setting all details to it.
     *
     * @param fromAccount     account transferring the amount.
     * @param toAccount       account receiving the amount.
     * @param amount          request account transaction.
     */
    private void makeTransactionTransfer(BankAccount fromAccount, BankAccount toAccount, double amount) {
        int dayCount = bankInstitutionRepository.getBank(fromAccount).getDayCountTime();
        Transaction transaction = new Transaction.TransactionBuilder(
            fromAccount.getIban().orElse(""),
            bankInstitutionRepository.getBank(fromAccount),
            amount,
            fromAccount.getCurrency(),
            processLocalDate(dayCount))
            .setTargetAccount(toAccount.getIban().orElse(""))
            .setTargetBank(bankInstitutionRepository.getBank(toAccount))
            .setTargetCurrency(toAccount.getCurrency())
            .build();
        transaction.setTransactionType(TransactionType.TRANSFER);
        bankAccountRepository.addTransaction(transaction);
        bankInstitutionRepository.addTransaction(transaction);
        transactionRepository.createTransaction(transaction);
        dayCount++;
        bankInstitutionRepository.getBank(fromAccount).setDayCountTime(dayCount);
    }

    /**
     * Method for saving the transaction in a statement for keeping history.
     *
     * @param bankAccount     account for making a deposit to account.
     * @param depositAmount   required amount to deposit.
     * @param TransactionType set type of operation.
     */
    private void makeTransactionForDepositOrWithdraw(BankAccount bankAccount, double depositAmount, TransactionType TransactionType) {
        // Get the count of days from BankInstitution using map through the account that is assigned to that bank.
        int dayCount = bankInstitutionRepository.getBank(bankAccount).getDayCountTime();
        Transaction transaction = new Transaction.TransactionBuilder(
            bankAccount.getIban().orElse(""),
            bankInstitutionRepository.getBank(bankAccount),
            depositAmount,
            bankAccount.getCurrency(),
            processLocalDate(dayCount))
            .build();
        transaction.setTransactionType(TransactionType);
        bankAccountRepository.addTransaction(transaction);
        transactionRepository.createTransaction(transaction);
        dayCount++;
        bankInstitutionRepository.getBank(bankAccount).setDayCountTime(dayCount);
    }

    /**
     * Prepare the amount to be subtracted and added to the corresponding accounts.
     *
     * @param fromAccount   account transferring the amount.
     * @param toAccount     account receiving the amount.
     * @param depositAmount requested amount for transfer.
     * @param taxRate       calculated taxes for the service.
     */
    private void processTransferRequest(BankAccount fromAccount, BankAccount toAccount, Double depositAmount, TaxType taxRate) {
        double sourceAccount = fromAccount.getAmountAvailable();
        double targetAccount = toAccount.getAmountAvailable();
		BankInstitution sourceAccountsBank = bankInstitutionRepository.getBank(fromAccount);
        double amountAfterExchange = exchangeRate(fromAccount, fromAccount.getCurrency()) * depositAmount;
        double amountAndTaxes = amountAfterExchange + bankInstitutionRepository.getTaxMap(sourceAccountsBank).get(taxRate);
        if (Objects.equals(fromAccount.getIban(), toAccount.getIban())) {
            throw new DuplicateIbanException(fromAccount.getIban());
        }
        if (amountAndTaxes > sourceAccount) {
            throw new InsufficientAmountTransferException(String.valueOf(amountAndTaxes));
        }
        sourceAccount -= amountAndTaxes;
        targetAccount += amountAfterExchange;
        fromAccount.setAmountAvailable(sourceAccount);
        toAccount.setAmountAvailable(targetAccount);
    }

    /**
     * Calculate exchange rates to be applied for sourcing account.
     *
     * @param bankAccount source account to be applied exchange rates
     * @param transactionCurrency amount's in currency that will be transferred.
     */
    private double exchangeRate(BankAccount bankAccount, Currency transactionCurrency) {
        String exchangeCurrency = bankAccount.getCurrency() + transactionCurrency.getCurrency();
        ExchangeRate exchangeRateUpdate = ExchangeRate.valueOf(exchangeCurrency);
        BankInstitution bank = bankInstitutionRepository.getBank(bankAccount);
        Map<ExchangeRate, Double> bankExchangeRateMap = bankInstitutionRepository.getExchangeRates(bank);
        return bankExchangeRateMap.get(exchangeRateUpdate);
    }

    /**
     * Method for automatically adding count of days to date.
     *
     * @param dayCount get the number of days to count further from.
     * @return the date plus number of days(dayCount) further.
     */
    private LocalDate processLocalDate(int dayCount) {
        LocalDate time = LocalDate.now();
        return time.plusDays(dayCount);
    }

}
