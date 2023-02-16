package eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository;

import eu.deltasource.internship.abankingsystem.accountType.CurrentAccount;
import eu.deltasource.internship.abankingsystem.enums.AccountType;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.abankingsystem.enums.TaxType;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.model.Owner;
import eu.deltasource.internship.abankingsystem.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

class BankInstitutionRepositoryImplTest {

    static BankInstitutionRepositoryImpl classUnderTest; // reference to be used for test. Static is to initialized by the test framework

    @BeforeAll
    static void setUp() {
        classUnderTest = BankInstitutionRepositoryImpl.getInstance(); // Initialize
    }

    @BeforeEach
        // create in each test case with @Test and then run the test
    void clearData() {
        classUnderTest.bankInstitutionMap.clear();
        classUnderTest.priceList.clear();
        classUnderTest.bankTaxMap.clear();
        classUnderTest.bankAccountMap.clear();
        classUnderTest.bankExchangeRates.clear();
        classUnderTest.allAccounts.clear();
        classUnderTest.allOwners.clear();
        classUnderTest.allTransactions.clear();
    }

    Map<ExchangeRate, Double> getExchangeRate() {
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
        return exchangeRates;
    }

    @Test
    public void Should_GetExchangeRates_When_ValidData() {
        // Given
        BankInstitution bank = new BankInstitution("Test Bank", "asdsad");
        classUnderTest.addExchangeRatesToBank(bank, getExchangeRate());

        // When
        classUnderTest.getExchangeRates(bank);

        // Then
        Assertions.assertEquals(classUnderTest.getExchangeRates(bank), getExchangeRate());
    }

    @Test
    public void Should_AddAccount_When_ValidData() {
        // Given
        String iban = "iban".toUpperCase();
        BankAccount bankAccount = new CurrentAccount(Optional.of(iban), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);

        // When
        classUnderTest.addAccount(bankAccount);

        // Then
        Assertions.assertEquals(1, classUnderTest.allAccounts.size());
    }

    @Test
    public void Should_GetAllAccounts_When_ValidData() {
        // Given
        String iban = "iban".toUpperCase();
        BankAccount bankAccount = new CurrentAccount(Optional.of(iban), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);

        // When
        classUnderTest.allAccounts.add(bankAccount);

        // Then
        Assertions.assertEquals(classUnderTest.getAllAccounts().size(), 1);
    }

    @Test
    public void Should_AddOwner_When_ValidData() {
        // Given
        String name = "Simon";
        Owner owner = new Owner(name);

        // When
        classUnderTest.addOwner(owner);

        // Then
        Assertions.assertEquals(classUnderTest.allOwners.get(0).getName(), owner.getName());
    }

    @Test
    public void Should_GatAllOwners_When_ValidData() {
        // Given
        BankInstitution bank = new BankInstitution("Test Bank", "asdsad");
        Owner simon = new Owner("Simon");

        // When
        classUnderTest.allOwners.add(simon);

        // Then
        Assertions.assertEquals(classUnderTest.getAllOwners(bank), List.of(simon));
    }

    @Test
    public void Should_GetAllTransactionsForBank_When_validData() {
        // Given
        String iban = "iban".toUpperCase();
        BankAccount bankAccount = new CurrentAccount(Optional.of(iban), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);
        BankInstitution bankInstitution = new BankInstitution("DSK", "asdasdwqe");
        Transaction transaction1 = new Transaction.TransactionBuilder(
            String.valueOf(Optional.of(bankAccount.getIban())),
            bankInstitution,
            10.0,
            Currency.EUR,
            LocalDate.of(2023, 2, 5)
        ).build();
        Transaction transaction2 = new Transaction.TransactionBuilder(
            String.valueOf(Optional.of(bankAccount.getIban())),
            bankInstitution,
            20.0,
            Currency.EUR,
            LocalDate.of(2023, 2, 6)
        ).build();
        Transaction transaction3 = new Transaction.TransactionBuilder(
            String.valueOf(Optional.of(bankAccount.getIban())),
            bankInstitution,
            30.0,
            Currency.EUR,
            LocalDate.of(2023, 2, 7)
        ).build();

        // When
        classUnderTest.allTransactions.add(transaction1);
        classUnderTest.allTransactions.add(transaction2);
        classUnderTest.allTransactions.add(transaction3);

        // Then
        Assertions.assertEquals(classUnderTest.getAllTransactions(bankInstitution), classUnderTest.allTransactions);
    }

    @Test
    public void Should_AddTransaction_When_ValidData() {
        // Given
        String iban = "iban".toUpperCase();
        BankAccount bankAccount = new CurrentAccount(Optional.of(iban), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);
        BankInstitution bankInstitution = new BankInstitution("DSK", "asdasdwqe");
        Transaction transaction1 = new Transaction.TransactionBuilder(
            String.valueOf(Optional.of(bankAccount.getIban())),
            bankInstitution,
            10.0,
            Currency.EUR,
            LocalDate.of(2023, 2, 5)
        ).build();
        Transaction transaction2 = new Transaction.TransactionBuilder(
            String.valueOf(Optional.of(bankAccount.getIban())),
            bankInstitution,
            20.0,
            Currency.EUR,
            LocalDate.of(2023, 2, 6)
        ).build();
        Transaction transaction3 = new Transaction.TransactionBuilder(
            String.valueOf(Optional.of(bankAccount.getIban())),
            bankInstitution,
            30.0,
            Currency.EUR,
            LocalDate.of(2023, 2, 7)
        ).build();

        // When
        classUnderTest.addTransaction(transaction1);
        classUnderTest.addTransaction(transaction2);
        classUnderTest.addTransaction(transaction3);

        // Then
        Assertions.assertEquals(classUnderTest.allTransactions.size(), 3);
    }

    @Test
    public void Should_AddBankToMap_When_ValidData() {
        // Given
        BankInstitution bank = new BankInstitution("Test Bank", "asdsad");

        // When
        classUnderTest.addExchangeRatesToBank(bank, getExchangeRate());
        classUnderTest.addBankToMap(bank);

        // Then
        Assertions.assertEquals(1, classUnderTest.bankInstitutionMap.size());
        Assertions.assertTrue(classUnderTest.bankInstitutionMap.containsValue(bank));
        Assertions.assertEquals(bank, classUnderTest.bankInstitutionMap.get(1));
    }

    @Test
    public void Should_FilterAndAddTaxesToBank_With_ValidData() {
        // Given
        BankInstitution bank = new BankInstitution("Test Bank", "asdsad");
        Map<TaxType, Double> taxes = new HashMap<>();
        taxes.put(TaxType.TAX_TO_THE_SAME_BANK, 2.10);
        taxes.put(TaxType.TAX_TO_DIFFERENT_BANK, 3.05);
        List<TaxType> taxTypeToAdd = new ArrayList<>();
        taxTypeToAdd.add(TaxType.TAX_TO_THE_SAME_BANK);

        // When
        classUnderTest.addTaxesToBank(bank, taxes, taxTypeToAdd);

        // Then
        Assertions.assertEquals(1, classUnderTest.bankTaxMap.size());
        Map<TaxType, Double> filteredTaxes = classUnderTest.bankTaxMap.get(bank);
        Assertions.assertEquals(1, filteredTaxes.size());
        Assertions.assertTrue(filteredTaxes.containsKey(TaxType.TAX_TO_THE_SAME_BANK));
        Assertions.assertFalse(filteredTaxes.containsKey(TaxType.TAX_TO_DIFFERENT_BANK));
    }

    @Test
    public void Should_AddTaxToBankMap_With_ValidData() {
        // Given
        BankInstitution bank = new BankInstitution("Test Bank", "asdsd");
        Map<TaxType, Double> taxMap = new HashMap<>();
        taxMap.put(TaxType.TAX_TO_THE_SAME_BANK, 0.1);
        taxMap.put(TaxType.TAX_TO_DIFFERENT_BANK, 0.2);

        // When
        classUnderTest.addTaxToBankMap(bank, taxMap);

        // Then
        Assertions.assertEquals(1, classUnderTest.bankTaxMap.size());
        Map<TaxType, Double> taxes = classUnderTest.bankTaxMap.get(bank);
        Assertions.assertEquals(2, taxes.size());
        Assertions.assertTrue(taxes.containsKey(TaxType.TAX_TO_THE_SAME_BANK));
        Assertions.assertTrue(taxes.containsKey(TaxType.TAX_TO_DIFFERENT_BANK));
    }

    @Test
    public void Should_GetTaxByBank_With_ValidData() {
        // Given
        BankInstitution bank = new BankInstitution("Test Bank", "asdsd");
        Map<TaxType, Double> taxMap = new HashMap<>();
        taxMap.put(TaxType.TAX_TO_THE_SAME_BANK, 0.1);
        taxMap.put(TaxType.TAX_TO_DIFFERENT_BANK, 0.2);

        // When
        classUnderTest.bankTaxMap.put(bank, taxMap);
        Double salesTax = classUnderTest.getTaxByBank(bank, TaxType.TAX_TO_THE_SAME_BANK);
        Double propertyTax = classUnderTest.getTaxByBank(bank, TaxType.TAX_TO_DIFFERENT_BANK);

        // Then
        Assertions.assertEquals(0.1, salesTax, 0.01);
        Assertions.assertEquals(0.2, propertyTax, 0.01);
    }

    @Test
    public void Should_GetTaxMap_With_ValidData() {
        // Given
        BankInstitution bank = new BankInstitution("Test Bank", "asdsd");
        Map<TaxType, Double> taxMap = new HashMap<>();
        taxMap.put(TaxType.TAX_TO_THE_SAME_BANK, 0.1);
        taxMap.put(TaxType.TAX_TO_DIFFERENT_BANK, 0.2);

        // When
        classUnderTest.bankTaxMap.put(bank, taxMap);
        Map<TaxType, Double> result = classUnderTest.getTaxMap(bank);

        // Then
        Assertions.assertEquals(taxMap, result);
    }

    @Test
    public void Should_GetTaxMap_With_BankNotInMap() {
        // Given
        BankInstitution bank = new BankInstitution("Test Bank", "asdsd");

        // When
        Map<TaxType, Double> result = classUnderTest.getTaxMap(bank);

        // Then
        Assertions.assertNull(result);
    }

    @Test
    public void Should_GetTaxByTax_With_ValidData() {
        // Given
        TaxType salesTax = TaxType.TAX_TO_THE_SAME_BANK;
        Double value = 0.1;
        classUnderTest.priceList.put(salesTax, value);

        // When
        Double result = classUnderTest.getTaxByTax(salesTax);

        // Then
        Assertions.assertEquals(value, result);
    }

    @Test
    public void Should_GetTaxByTax_With_TaxNotInList() {
        // Given
        TaxType salesTax = TaxType.TAX_TO_THE_SAME_BANK;

        // When
        Double result = classUnderTest.getTaxByTax(salesTax);

        // Then
        Assertions.assertNull(result);
    }

    @Test
    public void Should_FindTaxesByBank_With_ValidData() {
        // Given
        BankInstitution bank = new BankInstitution("Test Bank", "asdsd");
        Map<TaxType, Double> taxMap = new HashMap<>();
        taxMap.put(TaxType.TAX_TO_THE_SAME_BANK, 0.1);
        taxMap.put(TaxType.TAX_TO_DIFFERENT_BANK, 0.2);

        // When
        classUnderTest.bankTaxMap.put(bank, taxMap);
        Map<TaxType, Double> result = classUnderTest.findTaxesByBank(bank);

        // Then
        Assertions.assertEquals(taxMap, result);
    }

    @Test
    public void Should_GetBank_With_ValidData() {
        // Given
        BankInstitution bank1 = new BankInstitution("Test Bank 1", "asdsd");
        BankInstitution bank2 = new BankInstitution("Test Bank 2", "sadsad");
        BankAccount account1 = new CurrentAccount(Optional.of("iban"), Currency.EUR, 12, AccountType.CURRENT_ACCOUNT);
        BankAccount account2 = new CurrentAccount(Optional.of("iban2"), Currency.EUR, 12, AccountType.CURRENT_ACCOUNT);

        classUnderTest.addAccountToBank(bank1, List.of(account1));
        classUnderTest.addAccountToBank(bank2, List.of(account2));

        classUnderTest.bankInstitutionMap.put(1, bank1);
        classUnderTest.bankInstitutionMap.put(2, bank2);

        // When
        BankInstitution result = classUnderTest.getBank(account1);
        BankInstitution result2 = classUnderTest.getBank(account2);

        // Then
        Assertions.assertEquals(bank1, result);
        Assertions.assertEquals(bank2, result2);
    }

    @Test
    public void Should_GetBankById_With_ValidData() {

        // Given
        BankInstitution bank1 = new BankInstitution("Test Bank 1", "asdsd");
        classUnderTest.bankInstitutionMap.put(1, bank1);

        // When
        classUnderTest.getBankById(1);

        // Then
        Assertions.assertEquals(bank1, classUnderTest.getBankById(1));
    }

}