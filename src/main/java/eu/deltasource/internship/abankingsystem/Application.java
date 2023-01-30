package eu.deltasource.internship.abankingsystem;

import eu.deltasource.internship.abankingsystem.service.BankInterface;
import eu.deltasource.internship.abankingsystem.service.BankService;

import java.util.HashMap;
import java.util.Map;

import static eu.deltasource.internship.abankingsystem.BankAccount.ownerAccountCheck;

public class Application {
    public static void main(String[] args) {

        BankInterface transactionImpl = new BankService();

        Owner simon = new Owner("Simon");
        Owner kilian = new Owner("Kilian");
        Owner vix = new Owner("Vix");
        Owner vix2 = new Owner("Vix");

//        System.out.println(kilian);

//        ArrayList<Owner> ownersList = new ArrayList<>();
//        ownersList.add(simon);
//        ownersList.add(kilian);
//        ownersList.add(vix);
//        ownersList.add(vix2);

        Map<String, Double> exchangeRates = new HashMap<>();
        exchangeRates.put("BGNEUR", 1.95);
        exchangeRates.put("BGNUSD", 1.79);
        exchangeRates.put("USDBGN", 0.55);
        exchangeRates.put("USDEUR", 1.08);
        exchangeRates.put("EURBGN", 0.51);
        exchangeRates.put("EURUSD", 0.92);
        exchangeRates.put("EUREUR", 1.0);
        exchangeRates.put("BGNBGN", 1.0);
        exchangeRates.put("USDUSD", 1.0);

        BankInstitution dsk = new BankInstitution("DSK", "asdwqe 12 34", new HashMap<>() {{
            put("Tax to the same bank", 1.3);
            put("Tax to different bank", 2.3);
        }}, exchangeRates, transactionImpl);

//        System.out.println(dsk.getCustomers());

        BankInstitution raiffeisen = new BankInstitution("Raiffeisen", "asdwqe 12 34", new HashMap<>() {{
            put("Tax to the same bank", 3.3);
            put("Tax to different bank", 5.3);
        }}, exchangeRates, transactionImpl);


        BankAccount bankAccount1 = new BankAccount(dsk, simon, "toIBAN1", "EUR", 123.0, 'C');
        BankAccount bankAccount2 = new BankAccount(raiffeisen, simon, "fromIBANOwner1", "EUR", 123.0, 'C');
        BankAccount bankAccount5 = new BankAccount(raiffeisen, simon, "fromIBANOwner2", "EUR", 123.0, 'C');
        BankAccount bankAccount6 = new BankAccount(raiffeisen, simon, "fromIBANOwner3", "EUR", 123.0, 'S');
        BankAccount bankAccount7 = new BankAccount(dsk, simon, "fromIBANOwner4DSK", "EUR", 123.0, 'S');
        BankAccount bankAccount3 = new BankAccount(dsk, kilian, "toIBAN3", "USD", 123.0, 'C');

//        simon.getOwnerAccountCount().add(bankAccount2);
//        simon.getOwnerAccountCount().add(bankAccount5);
//        simon.getOwnerAccountCount().add(bankAccount6);

//        System.out.println(simon.getBankAccounts());

        System.out.println("How many account does a user have: ");
//        System.out.println(transactionImpl.ownerAccountCheck(BankAccount bankAccount, Owner owner));
//        System.out.println(bankAccount3.getOwnerList());
//        transactionImpl.ownerAccountCheck(bankAccount7, simon);
//        bankAccount7.ownerAccountCheck(simon);
//        System.out.println(getAccounts());
            ownerAccountCheck(simon);
//        System.out.println(bankAccount1.getAccounts());
//        transactionImpl.ownerAccountCheck(raiffeisen);
//        System.out.println(simon);
//        System.out.println(simon);
        System.out.println("0000000000000000000000000000000");

//        System.out.println(raiffeisen);

//        System.out.println(dsk.getCustomers().size());
//        System.out.println(raiffeisen.getCustomers().size());

//        System.out.println(bankAccount1);

//        transactionImpl.transferBetweenAccounts(bankAccount1, bankAccount2, 20.0);
//        transactionImpl.transferBetweenAccounts(bankAccount2, bankAccount3, 20.0);
//        System.out.println(bankAccount1.getAmountAvailable());
//        System.out.println(bankAccount2.getAmountAvailable());
//        System.out.println(bankAccount3.getAmountAvailable());


//        Transaction transaction = new Transaction(bankAccount1.getIban(), bankAccount2.getIban(), dsk, raiffeisen.getName(), 25.00, "USD", "USD", "Tax to different bank");
//        Transaction withdrawTransaction = new Transaction(bankAccount1.getIban(), dsk, 15.00, "USD");

//        System.out.println(bankAccount1.getTypeAccount());
//        dsk.processWithdraw(bankAccount1, 12); // bankAccount2
//        dsk.processWithdraw(bankAccount1, 15);
//        dsk.processWithdraw(bankAccount1, 10);
//        dsk.processWithdraw(bankAccount1, 80);
//        dsk.processDeposit(bankAccount1, 80, "USD");
//
        // TODO: If this account does not exist in the bank.
//        dsk.processWithdraw(bankAccount1, 20.00);
//        System.out.println(bankAccount1.getAmountAvailable());
//        dsk.processWithDrawHistory(bankAccount1);

//        System.out.println(bankAccount1.getIban() + ", " + bankAccount1.getAmountAvailable() + " " + bankAccount1.getCurrency());
//        System.out.println(bankAccount2.getIban() + ", " + bankAccount2.getAmountAvailable() + " " + bankAccount2.getCurrency());
//        bankAccount1.getBankInstitution().processTransferBetweenAccounts(bankAccount1, bankAccount2, 3.0);
//        dsk.processWithdraw(bankAccount1, 12);

//        transaction.processWithDrawHistory(bankAccount2);

//        System.out.println(bankAccount1.getAmountAvailable());
//        System.out.println(bankAccount2.getAmountAvailable());

//        bankAccount1.getBankInstitution().processDeposit(bankAccount1, 80, "USD");
//        bankAccount1.getBankInstitution().processDeposit(bankAccount1, 80, "USD");
//        bankAccount1.getBankInstitution().processDeposit(bankAccount1, 80, "USD");
//        bankAccount1.getBankInstitution().processDeposit(bankAccount1, 80, "USD");
//        bankAccount1.getBankInstitution().processDeposit(bankAccount1, 80, "USD");
//        bankAccount1.getBankInstitution().processWithDrawHistory(bankAccount1);

//        System.out.println(bankAccount1.getIban() + ", " + bankAccount1.getAmountAvailable() + " " + bankAccount1.getCurrency());
//        System.out.println(bankAccount2.getIban() + ", " + bankAccount2.getAmountAvailable() + " " + bankAccount2.getCurrency());
//        bankAccount1.getBankInstitution().processTransferBetweenAccounts(bankAccount1, bankAccount2, 3.0);
//
//        System.out.println(bankAccount1.getAmountAvailable());
//        System.out.println(bankAccount2.getAmountAvailable());

//        dsk.processWithdraw(bankAccount1, 12);
//
//        System.out.println("Account 1: " + bankAccount1.getAmountAvailable());
//        dsk.processDeposit(bankAccount1, 3, "BGN");
//        System.out.println("Account 1: " + bankAccount1.getAmountAvailable());
//
//        dsk.processDeposit(bankAccount1, 3, "USD");
//        dsk.processWithDrawHistory(bankAccount1);
//        System.out.println("Account 1: " + bankAccount1.getAmountAvailable());

//        System.out.println("Account 3: " + bankAccount3.getAmountAvailable());
//        dsk.processTransferBetweenAccounts(bankAccount1, bankAccount3, 20.0);

//        transactionImpl.transferBetweenAccounts(bankAccount1, bankAccount2, 20.0);
//        transactionImpl.transferBetweenAccounts(bankAccount2, bankAccount1, 30.0);
//        transactionImpl.transferBetweenAccounts(bankAccount2, bankAccount3, 45.0);
//        transactionImpl.deposit(bankAccount1, 3, "BGN");
//        transactionImpl.deposit(bankAccount1, 3, "BGN");
//        System.out.println(bankAccount1.getAmountAvailable());
//        transactionImpl.withDraw(bankAccount1, 3.0);
//        System.out.println(bankAccount1.getAmountAvailable());
//        transactionImpl.transactionHistory(bankAccount1);
//        transactionImpl.withDraw(bankAccount1, 3);
        System.out.println("-------------------------------------");
//        transactionImpl.transactionHistory(bankAccount1);
//        transactionImpl.transactionHistory(bankAccount1);

//        System.out.println("++++++++++++++++++++++++++++++++++++++");
//        System.out.println(bankAccount1.getTransferStatement());
//        System.out.println("===");
//        System.out.println(bankAccount2.getTransferStatement());
//        System.out.println("===----");
//        System.out.println(bankAccount3.getTransferStatement());
//        System.out.println("++++++++++++++++++++++++++++++++++++++");
//        transactionImpl.transactionHistory(bankAccount3);
//        transactionImpl.transactionHistory(bankAccount2);
//        System.out.println("Account 3: " + bankAccount3.getAmountAvailable());
//        dsk.processWithDrawHistory(bankAccount1);
//        dsk.processWithDrawHistory(bankAccount3);

//        System.out.println("---------------------------");
//        System.out.println("Account 1's transaction history: ");
////        dsk.processWithDrawHistory(bankAccount1);
//        System.out.println(bankAccount1.getTransferStatement());
////        dsk.processWithDrawHistory(bankAccount2);
//
//        System.out.println("Account 1's transaction history: ");
//        dsk.processWithDrawHistory(bankAccount1);
//
//        System.out.println("===================================");
//
        System.out.println("\nTimeStamp for bankAccount1:");
//        LocalDate startDate = LocalDate.of(2014, Month.JANUARY, 1);
//        LocalDate endDate = LocalDate.of(2014, Month.JANUARY, 1);
//        LocalDate start = LocalDate.of(2023, 1, 29);
//        LocalDate end = LocalDate.of(2023, 2, 2);
//        System.out.println(bankAccount1.getTransferStatementLocal(start, end));

//        System.out.println(bankAccount1.getTransferStatement());

    }
}
