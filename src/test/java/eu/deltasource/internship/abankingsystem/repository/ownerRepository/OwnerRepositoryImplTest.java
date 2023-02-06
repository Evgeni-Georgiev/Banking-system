package eu.deltasource.internship.abankingsystem.repository.ownerRepository;

import eu.deltasource.internship.abankingsystem.accountType.CurrentAccount;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        classUnderTest.bankAccounts.clear();
    }

    @Test
    public void Should_OwnerBeAddedToMap_When_ValidData() {
        // Test if the owner is added to the map
        Owner owner = new Owner("John");
        int idCounterBefore = classUnderTest.idCounter;
        classUnderTest.addOwnerToMap(owner);
        Assertions.assertEquals(owner, classUnderTest.ownerMap.get(idCounterBefore));
        Assertions.assertEquals(idCounterBefore + 1, classUnderTest.idCounter);
    }

    @Test
    public void Should_OwnerNotBeAddedToMap_When_InvalidData() {
        // Test if null owner is not added to the map:
        int idCounterBefore = classUnderTest.idCounter;
        classUnderTest.addOwnerToMap(null);
        Assertions.assertEquals(idCounterBefore, classUnderTest.idCounter);
        Assertions.assertTrue(classUnderTest.ownerMap.isEmpty());
    }

    @Test
    public void Should_OwnerBeAddedToMap_When_IncrementsIdCounter() {
        // Test if null owner is not added to the map:
        Owner owner = new Owner("John");
        int idCounterBefore = classUnderTest.idCounter;
        classUnderTest.addOwnerToMap(owner);
        Assertions.assertEquals(idCounterBefore + 1, classUnderTest.idCounter);
    }

    @Test
    public void Should_AddOwnerToAccount_When_ValidData() {
        // Test if the bank account is added to the owner
        Owner owner = new Owner("John");
        String iban = "iban".toUpperCase();
        BankAccount bankAccount = new CurrentAccount(owner, iban, Currency.EUR, 200.0, 'C');
        classUnderTest.addAccountToOwner(owner, bankAccount);
        Assertions.assertTrue(classUnderTest.bankAccounts.contains(bankAccount));
    }

    @Test
    public void Should_ReturnOwner_When_ValidData() {
        // Test if the correct owner is returned.
        Owner owner = new Owner("John");
        String iban = "iban".toUpperCase();
        BankAccount bankAccount = new CurrentAccount(owner, iban, Currency.EUR, 200.0, 'C');
        classUnderTest.addAccountToOwner(owner, bankAccount);
        Owner returnedOwner = classUnderTest.getOwner(bankAccount);
        Assertions.assertEquals(owner, returnedOwner);
    }

    @Test
    public void Should_GetOwnerById_When_idNotFound_returnsNull() {
        // Test if null is returned when id is not found
        // Given
        int id = 0;

        // When
        Owner returnedOwner = classUnderTest.getOwnerById(id);

        // Then
        Assertions.assertNull(returnedOwner);
    }

}