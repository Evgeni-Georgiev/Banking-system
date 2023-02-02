package eu.deltasource.internship.abankingsystem;

import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.abankingsystem.enums.Taxes;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.model.Owner;
import eu.deltasource.internship.abankingsystem.service.BankService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Application {
    public static void main(String[] args) {

        BankService transactionImpl = new BankService();
//        BankAccountToBankMapping bankAccountToBankMapping = new BankAccountToBankMapping();

        Owner simon = new Owner("Simon");
        Owner kilian = new Owner("Kilian");
        Owner vix = new Owner("Vix");
        Owner vix2 = new Owner("Vix");

        Map<ExchangeRate, Double> exchangeRates = new HashMap<>();
        exchangeRates.put(ExchangeRate.BGNEUR, 1.95);
        exchangeRates.put(ExchangeRate.BGNUSD, 1.79);
        exchangeRates.put(ExchangeRate.USDBGN, 0.55);
        exchangeRates.put(ExchangeRate.USDEUR, 1.08);
        exchangeRates.put(ExchangeRate.EURBGN, 0.51);
        exchangeRates.put(ExchangeRate.EURUSD, 0.92);
        exchangeRates.put(ExchangeRate.EUREUR, 1.0);
        exchangeRates.put(ExchangeRate.BGNBGN, 1.0);
        exchangeRates.put(ExchangeRate.USDUSD, 1.0);

        BankInstitution dsk = new BankInstitution("DSK", "asdwqe 12 34", new HashMap<>() {{
            put(Taxes.TAX_TO_THE_SAME_BANK, 1.3);
            put(Taxes.TAX_TO_DIFFERENT_BANK, 2.3);
        }}, exchangeRates);

        BankInstitution raiffeisen = new BankInstitution("Raiffeisen", "asdwqe 12 34", new HashMap<>() {{
            put(Taxes.TAX_TO_THE_SAME_BANK, 3.3);
            put(Taxes.TAX_TO_DIFFERENT_BANK, 5.3);
        }}, exchangeRates);

        BankAccount bankAccount1 = new BankAccount(simon, "toIBAN1", Currency.EUR, 123.0, 'C');
        BankAccount bankAccount2 = new BankAccount(simon, "fromIBANOwner1", Currency.EUR, 123.0, 'C');
        BankAccount bankAccount3 = new BankAccount(kilian, "toIBAN3", Currency.USD, 123.0, 'C');
        BankAccount bankAccount5 = new BankAccount(simon, "fromIBANOwner2", Currency.EUR, 123.0, 'C');
        BankAccount bankAccount6 = new BankAccount(simon, "fromIBANOwner3", Currency.EUR, 123.0, 'S');
        BankAccount bankAccount7 = new BankAccount(simon, "fromIBANOwner4DSK", Currency.EUR, 123.0, 'C');
        BankAccount bankAccount8 = new BankAccount(vix, "fromIBANOwnerVix", Currency.EUR, 123.0, 'C');

        transactionImpl.bankAccountToBankMapping.addAccountToBankMapping(bankAccount1, dsk);
        transactionImpl.bankAccountToBankMapping.addAccountToBankMapping(bankAccount2, raiffeisen);
        transactionImpl.bankAccountToBankMapping.addAccountToBankMapping(bankAccount5, raiffeisen);
        transactionImpl.bankAccountToBankMapping.addAccountToBankMapping(bankAccount6, raiffeisen);
        transactionImpl.bankAccountToBankMapping.addAccountToBankMapping(bankAccount7, dsk);
        transactionImpl.bankAccountToBankMapping.addAccountToBankMapping(bankAccount3, dsk);

//        BankAccountToBankMapping.mapping.put(bankAccount1, dsk);
//        BankAccountToBankMapping.mapping.put(bankAccount2, raiffeisen);
//        BankAccountToBankMapping.mapping.put(bankAccount5, raiffeisen);
//        transactionImpl.bankAccountToBankMapping.addAccountToBankMapping(bankAccount5, raiffeisen);
//        BankAccountToBankMapping.mapping.put(bankAccount6, raiffeisen);
//        BankAccountToBankMapping.mapping.put(bankAccount7, dsk);
//        BankAccountToBankMapping.mapping.put(bankAccount3, dsk);
//        BankAccountToBankMapping.mapping.put(bankAccount8, dsk);

        System.out.println(bankAccount1.getAmountAvailable());
        System.out.println(bankAccount2.getAmountAvailable());
        transactionImpl.transferBetweenAccounts(bankAccount1, bankAccount2, 10.0);
        transactionImpl.transferBetweenAccounts(bankAccount1, bankAccount2, 10.0);
        transactionImpl.transferBetweenAccounts(bankAccount1, bankAccount2, 10.0);
        System.out.println(bankAccount1.getAmountAvailable());
        System.out.println(bankAccount2.getAmountAvailable());

        System.out.println(bankAccount1.getAmountAvailable());
        transactionImpl.withDraw(bankAccount1, 20.00);
        System.out.println(bankAccount1.getAmountAvailable());

        // cannot deposit negative amount -- exception
        System.out.println(bankAccount1.getAmountAvailable());
        transactionImpl.deposit(bankAccount1, 2000.00, Currency.EUR);
        System.out.println(bankAccount1.getAmountAvailable());


        // TODO: If this account does not exist in the bank.
        LocalDate start = LocalDate.of(2023, 2, 2);
        LocalDate end = LocalDate.of(2023, 2, 8);
        System.out.println(bankAccount1.getTransferStatementLocal(start, end));

//        System.out.println(bankAccount1.getTransferStatement());

    }
}
