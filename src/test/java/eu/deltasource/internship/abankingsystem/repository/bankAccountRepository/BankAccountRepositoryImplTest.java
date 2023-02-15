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
import java.util.List;
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
        classUnderTest.transferStatement.clear();
    }

    @Test
    void Should_AddAccountToMap_When_ValidAccount() {
        // Given
        String iban = "iban".toUpperCase();
        BankAccount bankAccount = new CurrentAccount(Optional.of(iban), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);

        // When
        classUnderTest.addBankAccountToMap(bankAccount);

        // Then
        Assertions.assertEquals(bankAccount, classUnderTest.bankAccountMap.get(iban));
    }

    @Test
    public void Should_GetTransferStatementBySourceAccount_When_ValidData() {
        // Given
        BankAccount sourceAccount = new CurrentAccount(Optional.of("iban"), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);
        BankAccount targetAccount = new CurrentAccount(Optional.of("iban2"), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);
        BankInstitution bankInstitution = new BankInstitution("DSK", "asdasdwqe");
        Transaction transaction = new Transaction.TransactionBuilder(
            sourceAccount.getIban().orElse(""),
            bankInstitution,
            20.0,
            Currency.EUR,
            LocalDate.of(2023, 2, 5))
            .setTargetAccount(targetAccount.getIban().orElse(""))
            .setTargetBank(bankInstitution)
            .setTargetCurrency(Currency.EUR)
            .build();

        // When
        classUnderTest.transferStatement.add(transaction);

        // Then
        Assertions.assertEquals(1, classUnderTest.getTransferStatementsByAccount(sourceAccount.getIban().orElse("")).size());
        Assertions.assertEquals(1, classUnderTest.getTransferStatementsByAccount(targetAccount.getIban().orElse("")).size());
    }

    @Test
    public void Should_GetTransferStatementByTargetAccount_When_ValidData() {
        // Given
        String iban = "iban".toUpperCase();
        BankAccount bankAccount = new CurrentAccount(Optional.of(iban), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);
        BankInstitution bankInstitution = new BankInstitution("DSK", "asdasdwqe");
        Transaction transaction1 = new Transaction.TransactionBuilder(
            bankAccount.getIban().orElse(""),
            bankInstitution,
            20.0,
            Currency.EUR,
            LocalDate.of(2023, 2, 5)
        ).build();
        Transaction transaction2 = new Transaction.TransactionBuilder(
            bankAccount.getIban().orElse(""),
            bankInstitution,
            20.0,
            Currency.EUR,
            LocalDate.of(2023, 2, 5)
        ).build();
        Transaction transaction3 = new Transaction.TransactionBuilder(
            bankAccount.getIban().orElse(""),
            bankInstitution,
            20.0,
            Currency.EUR,
            LocalDate.of(2023, 2, 5)
        ).build();

        // When
        classUnderTest.transferStatement.add(transaction1);
        classUnderTest.transferStatement.add(transaction2);
        classUnderTest.transferStatement.add(transaction3);

        // Then
        Assertions.assertEquals(3, classUnderTest.getTransferStatementsByAccount(bankAccount.getIban().orElse("")).size());
    }

    @Test
    void Should_AddTransaction_When_ValidData_Deposit_Withdraw() {
        // Given
        String iban = "iban".toUpperCase();
        BankAccount bankAccount = new CurrentAccount(Optional.of(iban), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);
        BankInstitution bankInstitution = new BankInstitution("DSK", "asdasdwqe");
        Transaction transaction = new Transaction.TransactionBuilder(
                bankAccount.getIban().orElse(""),
                bankInstitution,
                20.0,
                Currency.EUR,
                LocalDate.of(2023, 2, 5)
        ).build();

        // When
        classUnderTest.addTransaction(transaction);

        // Then
        Assertions.assertAll(() -> Assertions.assertTrue(classUnderTest.transferStatement.contains(transaction)),
                () -> Assertions.assertEquals(transaction, classUnderTest.transferStatement.get(0)));
    }

    @Test
    void Should_GetIban_When_Requested() {
        // Given
        String iban = "iban".toUpperCase();
        BankAccount bankAccount = new CurrentAccount(Optional.of(iban), Currency.EUR, 200.0, AccountType.CURRENT_ACCOUNT);
        classUnderTest.bankAccountMap.put(iban, bankAccount);

        // When
        classUnderTest.getByIban(iban);

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
            bankAccount.getIban().orElse(""),
            bankInstitution,
            10.0,
            Currency.EUR,
            LocalDate.of(2023, 2, 5)
        ).build();
        Transaction transaction2 = new Transaction.TransactionBuilder(
            bankAccount.getIban().orElse(""),
            bankInstitution,
            20.0,
            Currency.EUR,
            LocalDate.of(2023, 2, 6)
        ).build();
        Transaction transaction3 = new Transaction.TransactionBuilder(
            bankAccount.getIban().orElse(""),
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
            bankAccount.getIban().orElse(""),
            bankInstitution,
            10.0,
            Currency.EUR,
            LocalDate.of(2023, 2, 5)
        ).build();
        Transaction transaction2 = new Transaction.TransactionBuilder(
            bankAccount.getIban().orElse(""),
            bankInstitution,
            20.0,
            Currency.EUR,
            LocalDate.of(2023, 2, 6)
        ).build();
        Transaction transaction3 = new Transaction.TransactionBuilder(
            bankAccount.getIban().orElse(""),
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
        Assertions.assertEquals(0, result.size());
    }

}