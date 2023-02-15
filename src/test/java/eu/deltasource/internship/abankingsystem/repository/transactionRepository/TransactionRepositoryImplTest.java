package eu.deltasource.internship.abankingsystem.repository.transactionRepository;

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
import java.util.Optional;

class TransactionRepositoryImplTest {
    static TransactionRepositoryImpl classUnderTest;

    @BeforeAll
    static void setUp() {
        classUnderTest = TransactionRepositoryImpl.getInstance();
    }

    @BeforeEach
    void clearData() {
        classUnderTest.transactionMap.clear();
    }

    @Test
    public void Should_CreateTransaction_When_ValidData() {
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
        classUnderTest.createTransaction(transaction);

        // Then
        Assertions.assertEquals(transaction, classUnderTest.transactionMap.get(1));

    }

    @Test
    public void Should_GetTransactionById_When_ValidData() {
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
        classUnderTest.transactionMap.put(1, transaction);

        // Then
        Assertions.assertEquals(transaction, classUnderTest.getTransactionById(1));

    }

}