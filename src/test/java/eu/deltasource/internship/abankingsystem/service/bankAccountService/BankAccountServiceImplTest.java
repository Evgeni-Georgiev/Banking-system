package eu.deltasource.internship.abankingsystem.service.bankAccountService;

import eu.deltasource.internship.abankingsystem.enums.AccountType;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.Owner;
import eu.deltasource.internship.abankingsystem.repository.bankAccountRepository.BankAccountRepository;
import eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository.BankInstitutionRepository;
import eu.deltasource.internship.abankingsystem.repository.ownerRepository.OwnerRepository;
import eu.deltasource.internship.abankingsystem.repository.transactionRepository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    BankInstitutionRepository bankInstitutionRepository;

    @Mock
    BankAccountRepository bankAccountRepository;

    @InjectMocks
    BankAccountServiceImpl classUnderTest;


    @Test
    void Should_createCurrentAccount_When_ValidData() {
        // Given
        Owner from = new Owner("John");

        // When
        classUnderTest.createBankAccount(Optional.of("iban1"), Currency.EUR, 15, AccountType.CURRENT_ACCOUNT);

        // Then
        Assertions.assertEquals("John", from.getName());
    }

    @Test
    void Should_createSavingsAccount_When_ValidData() {
        // Given
        Owner from = new Owner("John");

        // When
        classUnderTest.createBankAccount(Optional.of("iban2"), Currency.EUR, 15, AccountType.SAVINGS_ACCOUNT);

        // Then
        Assertions.assertEquals("John", from.getName());
    }

    @Test
    void Should_GetBankAccountByIban_When_ValidData() {
        // Given
        classUnderTest.createBankAccount(Optional.of("iban2"), Currency.EUR, 15, AccountType.SAVINGS_ACCOUNT);

        // When
        BankAccount bankAccount1 = classUnderTest.getBankAccountByIban("iban2");

        // Then
        Assertions.assertEquals(bankAccount1, classUnderTest.getBankAccountByIban("iban2"));
    }

}