package eu.deltasource.internship.abankingsystem;

import eu.deltasource.internship.abankingsystem.service.BankInterface;

import java.util.*;

public class BankInstitution {

    private BankInterface transactionInterface;

    private String name;

    private String address;

    private ArrayList<Owner> customers = new ArrayList<>();

    private HashMap<String, Double> priceList; // (that describes all taxes and fees and all currency exchange rates)

    private Map<String, Double> exchangeRates;

//    private LinkedList<Transaction> transferHistory;

    private int dayCountTime = 1;

    public BankInstitution(String name, String address, HashMap<String, Double> priceList, Map<String, Double> exchangeRate, BankInterface transactionInterface) {
        this.name = name;
        this.address = address;
//        this.customers = customers;
        // FIXME: Should be implemented in Application. Every Bank can have different taxes.
        this.priceList = priceList;
        this.exchangeRates = exchangeRate;
        this.transactionInterface = transactionInterface;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<Owner> getCustomers() {
        return customers;
    }

    public HashMap<String, Double> getPriceList() {
        return priceList;
    }

    public Map<String, Double> getExchangeRates() {
        return exchangeRates;
    }

    public int getDayCountTime() {
        return dayCountTime;
    }

    public void setExchangeRates(Map<String, Double> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public void setDayCountTime(int dayCountTime) {
        this.dayCountTime = dayCountTime;
    }

    public void setPriceList(HashMap<String, Double> priceList) {
        this.priceList = priceList;
    }

//    public LinkedList<Transaction> getTransferHistory() {
//        return transferHistory;
//    }
//
//    public void setTransferHistory(LinkedList<Transaction> transferHistory) {
//        this.transferHistory = transferHistory;
//    }

//    public String transactionHistory(BankAccount bankAccount) {
//        return String.format("%nTransaction Statement for %s: %n " +
//                "Source account: %s(%s) %n Target account: %s(%s) %n " +
//                "Transferred amount: (%s)%,.2f %n Source currency: %s %n " +
//                "Target currency: %s %n " +
//                "Transaction type: %s %n " +
//                "Timestamp: %s %n", getSourceIban(),
//            bankAccount.getSourceAccount().getIban(),
//            targetAccount.getBankInstitution().getName(),
//            targetAccount.getIban(),
//            getTargetBank().getName(),
//            getSourceCurrency(),
//            getAmount(),
//            getSourceCurrency(),
//            getTargetCurrency(),
//            getTransactionType(),
//            getTimestamp());
//    }

//    public void processDeposit(BankAccount bankAccount, double depositAmount, String currency) {
//        transactionInterface.deposit(bankAccount, depositAmount, currency);
//    }
//
//    public void processWithdraw(BankAccount bankAccount, double amount) {
//        transactionInterface.withDraw(bankAccount, amount);
//    }
//
//    public void processTransferBetweenAccounts(BankAccount fromAccount, BankAccount toAccount, double depositAmount) {
//        transactionInterface.transferBetweenAccounts(fromAccount, toAccount, depositAmount);
//    }
//
//    public void processWithDrawHistory(BankAccount bankAccount) {
//        transactionInterface.transactionHistory(bankAccount);
//    }
//
//    public void checkOwnersOfAccountInBank() {
//
//    }

    @Override
    public String toString() {
        return String.format("%nBank Details: %n " +
                "Name: %s %n " +
                "Address: %s %n " +
                "Customers: %s %n " +
                "Taxes: %s %n " +
                "Exchange rates available: %s %n " +
            getName(),
            getName(),
            getAddress(),
            getCustomers().size(),
            getPriceList(),
            getExchangeRates()
        );
    }
}
