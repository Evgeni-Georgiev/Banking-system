package eu.deltasource.internship.abankingsystem.repository.ownerRepository;

import eu.deltasource.internship.abankingsystem.accountType.CurrentAccount;
import eu.deltasource.internship.abankingsystem.enums.AccountType;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class OwnerRepositoryImplTest {
    static OwnerRepositoryImpl classUnderTest; // reference to be used for test. Static is to initialized by the test framework

    @BeforeAll
    static void setUp() {
        classUnderTest = OwnerRepositoryImpl.getInstance(); // Initialize
    }

    @BeforeEach
        // create in each test case with @Test and then run the test
    void clearData() {
        classUnderTest.ownerMap.clear();
        classUnderTest.ownerAccountMap.clear();
    }

    @Test
    public void Should_OwnerBeAddedToMap_When_ValidData() {
        // Test if the owner is added to the map
        // Given
        Owner owner = new Owner("John");
        int idCounterBefore = classUnderTest.idCounter;

        // When
        classUnderTest.addOwnerToMap(owner);

        // Then
        Assertions.assertEquals(owner, classUnderTest.ownerMap.get(idCounterBefore));
        Assertions.assertEquals(idCounterBefore + 1, classUnderTest.idCounter);
    }

    @Test
    public void Should_OwnerNotBeAddedToMap_When_InvalidData() {
        // Test if null owner is not added to the map:
        // Given
        int idCounterBefore = classUnderTest.idCounter;

        // When
        classUnderTest.addOwnerToMap(null);

        // Then
        Assertions.assertEquals(idCounterBefore, classUnderTest.idCounter);
        Assertions.assertTrue(classUnderTest.ownerMap.isEmpty());
    }

    @Test
    public void Should_OwnerBeAddedToMap_When_IncrementsIdCounter() {
        // Test if null owner is not added to the map:
        // Given
        Owner owner = new Owner("John");
        int idCounterBefore = classUnderTest.idCounter;

        // When
        classUnderTest.addOwnerToMap(owner);

        // Then
        Assertions.assertEquals(idCounterBefore + 1, classUnderTest.idCounter);
    }

    @Test
    public void Should_AddOwnerToAccount_When_ValidData() {
        // Test if the bank account is added to the owner
        // Given
        Owner owner = new Owner("John");
        BankAccount bankAccount = new CurrentAccount(Optional.of("iban"), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);

        // When
        classUnderTest.addOwnerToAccounts(owner, List.of(bankAccount));

        // Then
        Assertions.assertTrue(classUnderTest.ownerAccountMap.containsValue(List.of(bankAccount)));
    }

    @Test
    public void Should_ReturnOwner_When_ValidData() {
        // Test if the correct owner is returned.
        // Given
        Owner owner = new Owner(1, "John");

        // When
        classUnderTest.ownerMap.put(1, owner);
        Owner getOwnerFromMap = classUnderTest.getOwnerById(owner.getId());

        // Then
        Assertions.assertEquals(owner.getName(), getOwnerFromMap.getName());

    }

    @Test
    public void Should_GetOwnerById_When_idNotFound_returnsNull() {
        // Given
        int id = 0;

        // When
        Owner returnedOwner = classUnderTest.getOwnerById(id);

        // Then
        Assertions.assertNull(returnedOwner);
    }

    @Test
    public void Should_GetAccountsForOwner_When_ValidData() {
        // Given
        Owner simon = new Owner("Simon");
        BankAccount bankAccount1 = new CurrentAccount(Optional.of("GB15Z202150876987676"), Currency.BGN, 2000.0, AccountType.CURRENT_ACCOUNT);
        BankAccount bankAccount2 = new CurrentAccount(Optional.of("BG15Z202150876987677"), Currency.BGN, 2000.0, AccountType.SAVINGS_ACCOUNT);
        BankAccount bankAccount3 = new CurrentAccount(Optional.of("US15Z202150876987678"), Currency.BGN, 2000.0, AccountType.CURRENT_ACCOUNT);

        // When
        classUnderTest.ownerAccountMap.put(simon, List.of(bankAccount1, bankAccount2, bankAccount3));

        // Then
        Assertions.assertEquals(3, classUnderTest.getAccountsForOwner(simon).size());
    }

    @Test
    public void Should_GetListOfAccountsByOwner() {
        // Given
        Owner simon = new Owner("Simon");
        BankAccount bankAccount1 = new CurrentAccount(Optional.of("iban1"), Currency.EUR, 12, AccountType.CURRENT_ACCOUNT);
        BankAccount bankAccount2 = new CurrentAccount(Optional.of("iban2"), Currency.EUR, 12, AccountType.CURRENT_ACCOUNT);
        BankAccount bankAccount3 = new CurrentAccount(Optional.of("iban3"), Currency.EUR, 12, AccountType.CURRENT_ACCOUNT);

        // When
        classUnderTest.ownerAccountMap.put(simon, List.of(bankAccount1, bankAccount2, bankAccount3));

        // Then
        Assertions.assertEquals(classUnderTest.ownerAccountMap, classUnderTest.getOwnerAccountMap());
    }

}