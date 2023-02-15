package eu.deltasource.internship.abankingsystem.service.ownerService;

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

@ExtendWith(MockitoExtension.class)
class OwnerServiceImplTest {

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    BankInstitutionRepository bankInstitutionRepository;

    @Mock
    BankAccountRepository bankAccountRepository;

    @InjectMocks
    OwnerServiceImpl classUnderTest;

    @Test
    void Should_createOwner_When_ValidData() {
        // Given
        String name = "Simon";
        Owner owner = new Owner(name);

        // When
        classUnderTest.createOwner(owner.getName());

        // Then
        Assertions.assertEquals("Simon", name);
    }

    @Test
    void getOwnerById() {
        // Given
        String name = "Simon";
        Owner owner = new Owner(name);
        classUnderTest.createOwner(owner.getName());

        // When
        classUnderTest.getOwnerById(0);

        // Then
        Assertions.assertEquals(owner.getName(),"Simon");
    }

}