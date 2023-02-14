package eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository;

import eu.deltasource.internship.abankingsystem.accountType.CurrentAccount;
import eu.deltasource.internship.abankingsystem.enums.AccountType;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.abankingsystem.enums.TaxType;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void Should_AddBankToMap_When_ValidData() {
        BankInstitution bank = new BankInstitution("Test Bank", "asdsad");
        classUnderTest.addExchangeRatesToBank(bank, getExchangeRate());
        classUnderTest.addBankToMap(bank);
        Assertions.assertEquals(1, classUnderTest.bankInstitutionMap.size());
        Assertions.assertTrue(classUnderTest.bankInstitutionMap.containsValue(bank));
        Assertions.assertEquals(bank, classUnderTest.bankInstitutionMap.get(1));
    }

    @Test
    public void Should_FilterAndAddTaxesToBank_With_ValidData() {
        BankInstitution bank = new BankInstitution("Test Bank", "asdsad");
        Map<TaxType, Double> taxes = new HashMap<>();
        taxes.put(TaxType.TAX_TO_THE_SAME_BANK, 2.10);
        taxes.put(TaxType.TAX_TO_DIFFERENT_BANK, 3.05);

        List<TaxType> taxTypeToAdd = new ArrayList<>();
        taxTypeToAdd.add(TaxType.TAX_TO_THE_SAME_BANK);

        classUnderTest.filterAndAddTaxesToBank(bank, taxes, taxTypeToAdd);
        Assertions.assertEquals(1, classUnderTest.bankTaxMap.size());
        Map<TaxType, Double> filteredTaxes = classUnderTest.bankTaxMap.get(bank);
        Assertions.assertEquals(1, filteredTaxes.size());
        Assertions.assertTrue(filteredTaxes.containsKey(TaxType.TAX_TO_THE_SAME_BANK));
        Assertions.assertFalse(filteredTaxes.containsKey(TaxType.TAX_TO_DIFFERENT_BANK));
    }

    @Test
    public void Should_AddTaxToBankMap_With_ValidData() {
        BankInstitution bank = new BankInstitution("Test Bank", "asdsd");
        Map<TaxType, Double> taxMap = new HashMap<>();
        taxMap.put(TaxType.TAX_TO_THE_SAME_BANK, 0.1);
        taxMap.put(TaxType.TAX_TO_DIFFERENT_BANK, 0.2);

        classUnderTest.addTaxToBankMap(bank, taxMap);
        Assertions.assertEquals(1, classUnderTest.bankTaxMap.size());
        Map<TaxType, Double> taxes = classUnderTest.bankTaxMap.get(bank);
        Assertions.assertEquals(2, taxes.size());
        Assertions.assertTrue(taxes.containsKey(TaxType.TAX_TO_THE_SAME_BANK));
        Assertions.assertTrue(taxes.containsKey(TaxType.TAX_TO_DIFFERENT_BANK));
    }

    @Test
    public void Should_GetTaxByBank_With_ValidData() {
        BankInstitution bank = new BankInstitution("Test Bank", "asdsd");
        Map<TaxType, Double> taxMap = new HashMap<>();
        taxMap.put(TaxType.TAX_TO_THE_SAME_BANK, 0.1);
        taxMap.put(TaxType.TAX_TO_DIFFERENT_BANK, 0.2);

        classUnderTest.bankTaxMap.put(bank, taxMap);
        Double salesTax = classUnderTest.getTaxByBank(bank, TaxType.TAX_TO_THE_SAME_BANK);
        Double propertyTax = classUnderTest.getTaxByBank(bank, TaxType.TAX_TO_DIFFERENT_BANK);

        Assertions.assertEquals(0.1, salesTax, 0.01);
        Assertions.assertEquals(0.2, propertyTax, 0.01);
    }

    @Test
    public void Should_GetTaxMap_With_ValidData() {
        BankInstitution bank = new BankInstitution("Test Bank", "asdsd");
        Map<TaxType, Double> taxMap = new HashMap<>();
        taxMap.put(TaxType.TAX_TO_THE_SAME_BANK, 0.1);
        taxMap.put(TaxType.TAX_TO_DIFFERENT_BANK, 0.2);

        classUnderTest.bankTaxMap.put(bank, taxMap);
        Map<TaxType, Double> result = classUnderTest.getTaxMap(bank);

        Assertions.assertEquals(taxMap, result);
    }

    @Test
    public void Should_GetTaxMap_With_BankNotInMap() {
        BankInstitution bank = new BankInstitution("Test Bank", "asdsd");
        Map<TaxType, Double> result = classUnderTest.getTaxMap(bank);

        Assertions.assertNull(result);
    }

    @Test
    public void Should_GetTaxByTax_With_ValidData() {
        TaxType salesTax = TaxType.TAX_TO_THE_SAME_BANK;
        Double value = 0.1;
        classUnderTest.priceList.put(salesTax, value);

        Double result = classUnderTest.getTaxByTax(salesTax);

        Assertions.assertEquals(value, result);
    }

    @Test
    public void Should_GetTaxByTax_With_TaxNotInList() {
        TaxType salesTax = TaxType.TAX_TO_THE_SAME_BANK;
        Double result = classUnderTest.getTaxByTax(salesTax);

        Assertions.assertNull(result);
    }

    @Test
    public void Should_FindTaxesByBank_With_ValidData() {
        BankInstitution bank = new BankInstitution("Test Bank", "asdsd");
        Map<TaxType, Double> taxMap = new HashMap<>();
        taxMap.put(TaxType.TAX_TO_THE_SAME_BANK, 0.1);
        taxMap.put(TaxType.TAX_TO_DIFFERENT_BANK, 0.2);

        classUnderTest.bankTaxMap.put(bank, taxMap);
        Map<TaxType, Double> result = classUnderTest.findTaxesByBank(bank);

        Assertions.assertEquals(taxMap, result);
    }

    @Test
    public void Should_GetBank_With_ValidData() {
        BankInstitution bank1 = new BankInstitution("Test Bank 1", "asdsd");
        BankInstitution bank2 = new BankInstitution("Test Bank 2", "sadsad");
        BankAccount account1 = new CurrentAccount(Optional.of("iban"), Currency.EUR, 12, AccountType.CURRENT_ACCOUNT);
        BankAccount account2 = new CurrentAccount(Optional.of("iban"), Currency.EUR, 12, AccountType.CURRENT_ACCOUNT);

//        bankInstitutionRepository.addAccountToBank(dsk, List.of(bankAccount1, bankAccount2))
        classUnderTest.addAccountToBank(bank1, List.of(account1));
        classUnderTest.addAccountToBank(bank2, List.of(account2));
//        bank2.addAccountToBank(account2);

        classUnderTest.bankInstitutionMap.put(1, bank1);
        classUnderTest.bankInstitutionMap.put(2, bank2);

        BankInstitution result = classUnderTest.getBank(account1);
        Assertions.assertEquals(bank1, result);

        result = classUnderTest.getBank(account2);
        Assertions.assertEquals(bank2, result);
    }


}