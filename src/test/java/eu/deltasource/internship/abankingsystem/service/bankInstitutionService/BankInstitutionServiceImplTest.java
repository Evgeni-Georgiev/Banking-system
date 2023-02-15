package eu.deltasource.internship.abankingsystem.service.bankInstitutionService;

import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankInstitutionServiceImplTest {

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    BankInstitutionRepository bankInstitutionRepository;

    @Mock
    BankAccountRepository bankAccountRepository;

    @InjectMocks
    BankInstitutionServiceImpl classUnderTest;

    @Test
    public void Should_FilterOwnersByBank_With_EmptyOwnerAccountMap() {
        // Given
        when(ownerRepository.getOwnerAccountMap()).thenReturn(new HashMap<>());

        // When
        List<Owner> filteredOwners = classUnderTest.filterOwnersByBank(new BankInstitution("Test Bank 1", "asdsd"));

        // Then
        Assertions.assertEquals(0, filteredOwners.size());
    }

    @Test
    void Should_createBank_When_ValidData() {
        // Given
        String name = "DSK";

        // When
        classUnderTest.createBankInstitution(name, "addressNumber");

        // Then
        Assertions.assertEquals("DSK", name);
    }

    @Test
    public void Should_FilterAccountsInBank_With_EmptyAccounts() {
        // Given
        when(bankInstitutionRepository.getAllAccounts()).thenReturn(Arrays.asList());

        // When
        List<BankAccount> filteredAccounts = classUnderTest.filterAccountsInBank(new BankInstitution("Test Bank 1", "asdsd"));

        // Then
        Assertions.assertEquals(0, filteredAccounts.size());
    }

}