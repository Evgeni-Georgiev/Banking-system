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
//        classUnderTest.bankAccounts.clear();
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
        BankAccount bankAccount = new CurrentAccount(Optional.of(iban), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);
//        classUnderTest.addAccountToOwner(owner, bankAccount);
        classUnderTest.addOwnerToAccounts(owner, List.of(bankAccount));
//        Assertions.assertTrue(classUnderTest.bankAccounts.contains(bankAccount));
        Assertions.assertTrue(classUnderTest.ownerAccountMap.containsValue(List.of(bankAccount)));
    }

    @Test
    public void Should_ReturnOwner_When_ValidData() {
        // Test if the correct owner is returned.
        Owner owner = new Owner(1, "John");
//        classUnderTest.addOwnerToMap(owner);
        classUnderTest.ownerMap.put(1, owner);
        Owner getOwnerFromMap = classUnderTest.getOwnerById(owner.getId());
        Assertions.assertEquals(owner.getName(), getOwnerFromMap.getName());

//        Owner owner1 = new Owner(1, "John Doe");
//
//        // Test when the owner is not null
//        classUnderTest.addOwnerToMap(owner1);
//        Assertions.assertEquals(1, classUnderTest.ownerMap.size());
//        Assertions.assertEquals(owner1, classUnderTest.ownerMap.get(1));

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