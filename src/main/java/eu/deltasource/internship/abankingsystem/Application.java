package eu.deltasource.internship.abankingsystem;

import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.enums.Taxes;
import eu.deltasource.internship.abankingsystem.exception.InsufficientAmountTransferException;
import eu.deltasource.internship.abankingsystem.exception.InsufficientAmountWithdrawException;
import eu.deltasource.internship.abankingsystem.exception.TransferBetweenNotCurrentAccountsException;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.model.Owner;
import eu.deltasource.internship.abankingsystem.service.BankInterface;
import eu.deltasource.internship.abankingsystem.service.BankService;

import java.util.HashMap;
import java.util.Map;

public class Application {
    public static void main(String[] args) {

        BankInterface transactionImpl = new BankService();

        Owner simon = new Owner("Simon");
        Owner kilian = new Owner("Kilian");
        Owner vix = new Owner("Vix");
        Owner vix2 = new Owner("Vix");

//        ArrayList<Owner> ownersList = new ArrayList<>();
//        ownersList.add(simon);
//        ownersList.add(kilian);
//        ownersList.add(vix);
//        ownersList.add(vix2);

        Map<Currency, Double> exchangeRates = new HashMap<>();
        exchangeRates.put(Currency.BGNEUR, 1.95);
        exchangeRates.put(Currency.BGNUSD, 1.79);
        exchangeRates.put(Currency.USDBGN, 0.55);
        exchangeRates.put(Currency.USDEUR, 1.08);
        exchangeRates.put(Currency.EURBGN, 0.51);
        exchangeRates.put(Currency.EURUSD, 0.92);
        exchangeRates.put(Currency.EUREUR, 1.0);
        exchangeRates.put(Currency.BGNBGN, 1.0);
        exchangeRates.put(Currency.USDUSD, 1.0);

        BankInstitution dsk = new BankInstitution("DSK", "asdwqe 12 34", new HashMap<>() {{
            put(Taxes.TAX_TO_THE_SAME_BANK, 1.3);
            put(Taxes.TAX_TO_DIFFERENT_BANK, 2.3);
        }}, exchangeRates);

        BankInstitution raiffeisen = new BankInstitution("Raiffeisen", "asdwqe 12 34", new HashMap<>() {{
            put(Taxes.TAX_TO_THE_SAME_BANK, 3.3);
            put(Taxes.TAX_TO_DIFFERENT_BANK, 5.3);
        }}, exchangeRates);


//        BankAccount bankAccount1 = new BankAccount(dsk, simon, "toIBAN1", "EUR", 123.0, 'C');
//        BankAccount bankAccount2 = new BankAccount(raiffeisen, simon, "fromIBANOwner1", "EUR", 123.0, 'C');
//        BankAccount bankAccount5 = new BankAccount(raiffeisen, simon, "fromIBANOwner2", "EUR", 123.0, 'C');
//        BankAccount bankAccount6 = new BankAccount(raiffeisen, simon, "fromIBANOwner3", "EUR", 123.0, 'S');
//        BankAccount bankAccount7 = new BankAccount(dsk, simon, "fromIBANOwner4DSK", "EUR", 123.0, 'C');
//        BankAccount bankAccount3 = new BankAccount(dsk, kilian, "toIBAN3", "USD", 123.0, 'C');

        BankAccount bankAccount1 = new BankAccount(simon, "toIBAN1", "EUR", 123.0, 'C');
        BankAccount bankAccount2 = new BankAccount(simon, "fromIBANOwner1", "EUR", 123.0, 'C');
        BankAccount bankAccount3 = new BankAccount(kilian, "toIBAN3", "USD", 123.0, 'C');
        BankAccount bankAccount5 = new BankAccount(simon, "fromIBANOwner2", "EUR", 123.0, 'C');
        BankAccount bankAccount6 = new BankAccount(simon, "fromIBANOwner3", "EUR", 123.0, 'S');
        BankAccount bankAccount7 = new BankAccount(simon, "fromIBANOwner4DSK", "EUR", 123.0, 'C');
        BankAccount bankAccount8 = new BankAccount(vix, "fromIBANOwnerVix", "EUR", 123.0, 'C');

//        System.out.println(simon);

//        BankAccountToBankMapping mapping = new BankAccountToBankMapping();
//        mapping.addMapping(bankAccount1, dsk);
//        mapping.addMapping(bankAccount2, raiffeisen);
//        mapping.addMapping(bankAccount5, raiffeisen);
//        mapping.addMapping(bankAccount6, raiffeisen);
//        mapping.addMapping(bankAccount7, dsk);
//        mapping.addMapping(bankAccount3, dsk);

        BankAccountToBankMapping.mapping.put(bankAccount1, dsk);
        BankAccountToBankMapping.mapping.put(bankAccount2, raiffeisen);
        BankAccountToBankMapping.mapping.put(bankAccount5, raiffeisen);
        BankAccountToBankMapping.mapping.put(bankAccount6, raiffeisen);
        BankAccountToBankMapping.mapping.put(bankAccount7, dsk);
        BankAccountToBankMapping.mapping.put(bankAccount3, dsk);
        BankAccountToBankMapping.mapping.put(bankAccount8, dsk);

//        if(!BankAccountToBankMapping.mapping.get(bankAccount1).getCustomers().contains(bankAccount1.getOwner())) {
//            BankAccountToBankMapping.mapping.get(bankAccount1).getCustomers().add(bankAccount1.getOwner());
//        }

        dsk.getAllCustomersOfBank(bankAccount1);
        dsk.getAllCustomersOfBank(bankAccount8);

        System.out.println(BankAccountToBankMapping.mapping.get(bankAccount1).getCustomers().size());
//        System.out.println(BankAccountToBankMapping.mapping.get(bankAccount1));

//        System.out.println("How many account does a user have: ");

//        System.out.println(bankAccount1.getAmountAvailable());
//        System.out.println(bankAccount2.getAmountAvailable());


//        System.out.println(bankAccount1.getAmountAvailable());
//        transactionImpl.deposit(bankAccount1, 20.00, "EUR");
//        System.out.println(bankAccount1.getAmountAvailable());


//        System.out.println(bankAccount1.getAmountAvailable());
//        transactionImpl.withDraw(bankAccount1, 10.00);
//        System.out.println(bankAccount1.getAmountAvailable());

        System.out.println(bankAccount1.getAmountAvailable());
        System.out.println(bankAccount2.getAmountAvailable());
        transactionImpl.transferBetweenAccounts(bankAccount1, bankAccount2, 10.0);
        System.out.println(bankAccount1.getAmountAvailable());
        System.out.println(bankAccount2.getAmountAvailable());

//        try {
//            transactionImpl.withDraw(bankAccount1, 20.00);
//        } catch (InsufficientAmountWithdrawException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(bankAccount1.getAmountAvailable());

//        System.out.println(bankAccount1.getAmountAvailable());



//        System.out.println(bankAccount2.getAmountAvailable());


//        try {
//            transactionImpl.withDraw(bankAccount1, 200.00);
//        } catch (InsufficientAmountWithdrawException e) {
//            System.out.println("Insufficient amount to withdraw.");
//        }

//        transactionImpl.withDraw(bankAccount1, 200.00);



//        transactionImpl.withDraw(bankAccount1, 200.00);
//        System.out.println(transactionImpl.ownerAccountCheck(BankAccount bankAccount, Owner owner));
//        System.out.println(bankAccount3.getOwnerList());
//        transactionImpl.ownerAccountCheck(bankAccount7, simon);
//        bankAccount7.ownerAccountCheck(simon);
//        System.out.println(getAccounts());
//            ownerAccountCheck(simon);
//        System.out.println(bankAccount1.getAccounts());
//        transactionImpl.ownerAccountCheck(raiffeisen);
//        System.out.println(simon);
//        System.out.println(simon);
//        System.out.println("0000000000000000000000000000000");

//        System.out.println(raiffeisen);

//        System.out.println(dsk.getCustomers().size());
//        System.out.println(raiffeisen.getCustomers().size());

//        System.out.println(bankAccount1);

//        System.out.println(bankAccount1.getAmountAvailable());
//        System.out.println(bankAccount2.getAmountAvailable());
//

//        try {
//            transactionImpl.transferBetweenAccounts(bankAccount1, bankAccount2, 10.0);
//            transactionImpl.transferBetweenAccounts(bankAccount2, bankAccount1, 10.0);
//            transactionImpl.transferBetweenAccounts(bankAccount3, bankAccount1, 10.0);
//        } catch (TransferBetweenNotCurrentAccountsException | InsufficientAmountTransferException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(bankAccount1.getAmountAvailable());
//        System.out.println(bankAccount2.getAmountAvailable());

//        System.out.println(bankAccount1.getTransferStatement());
//        System.out.println(bankAccount1);


//        transactionImpl.deposit(bankAccount1, 29.0, "USD");
//        System.out.println(bankAccount1.getAmountAvailable());
//        System.out.println(bankAccount2.getAmountAvailable());

        // TODO: If this account does not exist in the bank.
//
//        System.out.println("\nTimeStamp for bankAccount1:");
//        LocalDate startDate = LocalDate.of(2014, Month.JANUARY, 1);
//        LocalDate endDate = LocalDate.of(2014, Month.JANUARY, 1);
//        LocalDate start = LocalDate.of(2023, 1, 29);
//        LocalDate end = LocalDate.of(2023, 2, 2);
//        System.out.println(bankAccount1.getTransferStatementLocal(start, end));

//        System.out.println(bankAccount1.getTransferStatement());

    }
}
