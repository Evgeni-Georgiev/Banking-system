package eu.deltasource.internship.abankingsystem.repository.bankAccountRepository;

import eu.deltasource.internship.abankingsystem.accountType.CurrentAccount;
import eu.deltasource.internship.abankingsystem.enums.AccountType;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class BankAccountRepositoryImplTest {
    static BankAccountRepositoryImpl classUnderTest; // reference to be used for test. Static is to initialized by the test framework

    @BeforeAll
    static void setUp() {
        classUnderTest = BankAccountRepositoryImpl.getInstance(); // Initialize
    }

    @BeforeEach
        // create in each test case with @Test and then run the test
    void clearData() {
        classUnderTest.bankAccountMap.clear();
//        BankAccountRepositoryImpl.accounts.clear();
        classUnderTest.transferStatement.clear();
    }

    @Test
    void Should_AddAccountToMap_When_ValidAccount() {
        String iban = "iban".toUpperCase();
        BankAccount bankAccount = new CurrentAccount(Optional.of(iban), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);
        classUnderTest.addBankAccountToMap(bankAccount);
        Assertions.assertEquals(bankAccount, classUnderTest.bankAccountMap.get(iban));
    }


    @Test
    void Should_AddTransaction_When_ValidData_Deposit_Withdraw() {
        String iban = "iban".toUpperCase();
        BankAccount bankAccount = new CurrentAccount(Optional.of(iban), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);
        BankInstitution bankInstitution = new BankInstitution("DSK", "asdasdwqe");
        Transaction transaction = new Transaction.TransactionBuilder(
                String.valueOf(Optional.of(bankAccount.getIban())),
                bankInstitution,
                20.0,
                Currency.EUR,
                LocalDate.of(2023, 2, 5)
        ).build();
        classUnderTest.addTransaction(transaction);

        Assertions.assertAll(() -> Assertions.assertTrue(classUnderTest.transferStatement.contains(transaction)),
                () -> Assertions.assertEquals(transaction, classUnderTest.transferStatement.get(0)));
    }

    @Test
    void Should_GetIban_When_Requested() {

        // Given
        String iban = "iban".toUpperCase();
        BankAccount bankAccount = new CurrentAccount(Optional.of(iban), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);
        Map<String, BankAccount> bankAccountMap = new HashMap<>();
        bankAccountMap.put(iban, bankAccount);

        // When
        classUnderTest.addBankAccountToMap(bankAccount);
//        classUnderTest.getByIban(iban); // set through variable

        // Then
        Assertions.assertEquals(bankAccount, classUnderTest.getByIban(iban));
    }

    @Test
    void Should_Return_Transactions_When_TimeInRange() {
        // Given
        LocalDate start = LocalDate.of(2023, 2, 5);
        LocalDate end = LocalDate.of(2023, 2, 7);
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
        classUnderTest.transferStatement.add(transaction1);
        classUnderTest.transferStatement.add(transaction2);
        classUnderTest.transferStatement.add(transaction3);
        List<Transaction> result = classUnderTest.getTransferStatementLocal(start, end);

        // Then
        // Assert by size of the List and amount for transaction
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(10.0, result.get(0).getAmount());
        Assertions.assertEquals(20.0, result.get(1).getAmount());
        Assertions.assertEquals(30.0, result.get(2).getAmount());
    }


    @Test
    void Should_NotReturn_Transactions_When_NotInTimeRange() {
        // Given
        LocalDate start = LocalDate.of(2023, 2, 1);
        LocalDate end = LocalDate.of(2023, 2, 3);
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
        classUnderTest.transferStatement.add(transaction1);
        classUnderTest.transferStatement.add(transaction2);
        classUnderTest.transferStatement.add(transaction3);
        List<Transaction> result = classUnderTest.getTransferStatementLocal(start, end);

        // Then
        // Assert by size of the List and amount for transaction
        Assertions.assertEquals(0, result.size());
    }

//    @Test
//    void Should_CountAccountsOwnerHas_When_ValidData() {
//        // Given
//        Owner owner = new Owner("Simon");
//        String iban1 = "iban1".toUpperCase();
//        String iban2 = "iban2".toUpperCase();
//        String iban3 = "iban3".toUpperCase();
//        BankAccount account1 = new CurrentAccount(iban1, Currency.EUR, 200.0, 'C');
//        BankAccount account2 = new CurrentAccount(iban2, Currency.EUR, 200.0, 'C');
//        BankAccount account3 = new CurrentAccount(iban3, Currency.EUR, 200.0, 'C');
//
////        ownerRepository.addOwnerToAccounts(vix, List.of(bankAccount1, bankAccount2));
//
//        // When
//        BankAccountRepositoryImpl.accounts.add(account1);
//        BankAccountRepositoryImpl.accounts.add(account2);
//        BankAccountRepositoryImpl.accounts.add(account3);
//        classUnderTest.addBankAccountToMap(account1);
//        classUnderTest.addBankAccountToMap(account2);
//        classUnderTest.addBankAccountToMap(account3);
//
//        // Then
//        int countOfAccountsSingleOwnerHas = ownerRepository.getAccountsForOwner(simon).size();
//        Assertions.assertEquals(6, countOfAccountsSingleOwnerHas);
//    }

}