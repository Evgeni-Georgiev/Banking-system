package eu.deltasource.internship.abankingsystem.service.transactionService;

import eu.deltasource.internship.abankingsystem.accountType.CurrentAccount;
import eu.deltasource.internship.abankingsystem.accountType.SavingsAccount;
import eu.deltasource.internship.abankingsystem.enums.*;
import eu.deltasource.internship.abankingsystem.exception.DuplicateIbanException;
import eu.deltasource.internship.abankingsystem.exception.InsufficientAmountTransferException;
import eu.deltasource.internship.abankingsystem.exception.InsufficientAmountWithdrawException;
import eu.deltasource.internship.abankingsystem.exception.TransferBetweenNotCurrentAccountsException;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.repository.bankAccountRepository.BankAccountRepository;
import eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository.BankInstitutionRepository;
import eu.deltasource.internship.abankingsystem.repository.transactionRepository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    BankInstitutionRepository bankInstitutionRepository;

    @Mock
    BankAccountRepository bankAccountRepository;

    @InjectMocks
    TransactionServiceImpl classUnderTest;

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

    Map<TaxType, Double> getTaxMap() {
        Map<TaxType, Double> taxMap1 = new HashMap<>();
        taxMap1.put(TaxType.TAX_TO_THE_SAME_BANK, 2.10);
        taxMap1.put(TaxType.TAX_TO_DIFFERENT_BANK, 3.05);
        return taxMap1;
    }

    @Test
    void Should_Transfer_When_Valid_Data() {
        // Given
        BankAccount from = new CurrentAccount(Optional.of("iban2"), Currency.EUR, 15, AccountType.CURRENT_ACCOUNT);
        BankAccount to = new CurrentAccount(Optional.of("iban"), Currency.EUR, 12, AccountType.CURRENT_ACCOUNT);
        BankInstitution bankInstitution = new BankInstitution("DSK", "asd");
        when(bankInstitutionRepository.getBank(from)).thenReturn(bankInstitution);
        when(bankInstitutionRepository.getBank(to)).thenReturn(bankInstitution);
        when(bankInstitutionRepository.getTaxMap(any(BankInstitution.class))).thenReturn(getTaxMap());
        when(bankInstitutionRepository.getExchangeRates(bankInstitution)).thenReturn(getExchangeRate());

        // When
        classUnderTest.transfer(from, to, 2);

        // Then
        Assertions.assertEquals(10.9, from.getAmountAvailable());
        Assertions.assertEquals(14, to.getAmountAvailable());
    }

    @Test
    void Should_ThrowException_When_InValid_Data() {
        // Given
        BankAccount from = new SavingsAccount(Optional.of("iban2"), Currency.EUR, 15, AccountType.CURRENT_ACCOUNT);
        BankAccount to = new CurrentAccount(Optional.of("iban"), Currency.EUR, 12, AccountType.SAVINGS_ACCOUNT);

        // Then
        Assertions.assertThrows(TransferBetweenNotCurrentAccountsException.class, ()-> classUnderTest.transfer(from, to, 2));
    }

    @Test
    void Should_Deposit_When_Valid_Data() {
        // Given
        BankAccount account = new CurrentAccount(Optional.of("iban2"), Currency.EUR, 15, AccountType.CURRENT_ACCOUNT);
        BankInstitution bankInstitution = new BankInstitution("DSK", "asd");

        when(bankInstitutionRepository.getBank(account)).thenReturn(bankInstitution);
        when(bankInstitutionRepository.getTaxMap(bankInstitution)).thenReturn(getTaxMap());
        when(bankInstitutionRepository.getExchangeRates(bankInstitution)).thenReturn(getExchangeRate());

        // When
        classUnderTest.deposit(account, 2, Currency.EUR);

        // Then
        Assertions.assertEquals(19.1, account.getAmountAvailable());
    }

    @Test
    void Should_Withdraw_When_Valid_Data() {
        // Given
        BankAccount account = new CurrentAccount(Optional.of("iban2"), Currency.EUR, 15, AccountType.CURRENT_ACCOUNT);
        BankInstitution bankInstitution = new BankInstitution("DSK", "asd");

        when(bankInstitutionRepository.getBank(account)).thenReturn(bankInstitution);
        when(bankInstitutionRepository.getTaxMap(bankInstitution)).thenReturn(getTaxMap());
        when(bankInstitutionRepository.getExchangeRates(bankInstitution)).thenReturn(getExchangeRate());

        // When
        classUnderTest.withdraw(account, 2.0);

        // Then
        Assertions.assertEquals(10.9, account.getAmountAvailable());
    }

    @Test
    void Should_NotWithdraw_When_InValid_Data() {
        // Given
        BankAccount account = new CurrentAccount(Optional.of("iban2"), Currency.EUR, 15, AccountType.CURRENT_ACCOUNT);
        BankInstitution bankInstitution = new BankInstitution("DSK", "asd");

        when(bankInstitutionRepository.getBank(account)).thenReturn(bankInstitution);
        when(bankInstitutionRepository.getTaxMap(bankInstitution)).thenReturn(getTaxMap());
        when(bankInstitutionRepository.getExchangeRates(bankInstitution)).thenReturn(getExchangeRate());

        // When
        RuntimeException exception = assertThrows(InsufficientAmountWithdrawException.class,
            () -> classUnderTest.withdraw(account,20.0));

        // Then
        Assertions.assertTrue(exception.getMessage().contentEquals(MessageTemplate.INSUFFICIENT_AMOUNT_TO_WITHDRAW.getMessageTemplate() + account.getAmountAvailable()));
    }

    @Test
    void Should_Transfer_When_Different_Banks() {
        // Given
        BankAccount from = new CurrentAccount(Optional.of("iban1"), Currency.EUR, 15, AccountType.CURRENT_ACCOUNT);
        BankAccount to = new CurrentAccount(Optional.of("iban2"), Currency.EUR, 12, AccountType.CURRENT_ACCOUNT);
        BankInstitution bankInstitution1 = new BankInstitution("DSK", "asd");
        BankInstitution bankInstitution2 = new BankInstitution("KBC", "qwe");

        when(bankInstitutionRepository.getBank(from)).thenReturn(bankInstitution1);
        when(bankInstitutionRepository.getBank(to)).thenReturn(bankInstitution2);
        when(bankInstitutionRepository.getTaxMap(any(BankInstitution.class))).thenReturn(getTaxMap());
        when(bankInstitutionRepository.getExchangeRates(any(BankInstitution.class))).thenReturn(getExchangeRate());

        // When
        classUnderTest.transfer(from, to, 2);

        // Then
        Assertions.assertEquals(9.95, from.getAmountAvailable());
        Assertions.assertEquals(14, to.getAmountAvailable());
    }

    @Test
    void Should_NotTransfer_When_Same_Ibans() {
        // Given
        BankAccount from = new CurrentAccount(Optional.of("iban1"), Currency.EUR, 15, AccountType.CURRENT_ACCOUNT);
        BankAccount to = new CurrentAccount(Optional.of("iban1"), Currency.EUR, 12, AccountType.CURRENT_ACCOUNT);
        BankInstitution bankInstitution1 = new BankInstitution("DSK", "asd");
        BankInstitution bankInstitution2 = new BankInstitution("KBC", "qwe");
        when(bankInstitutionRepository.getBank(from)).thenReturn(bankInstitution1);
        when(bankInstitutionRepository.getBank(to)).thenReturn(bankInstitution2);
        when(bankInstitutionRepository.getTaxMap(any(BankInstitution.class))).thenReturn(getTaxMap());
        when(bankInstitutionRepository.getExchangeRates(any(BankInstitution.class))).thenReturn(getExchangeRate());

        // When
        RuntimeException exception = assertThrows(DuplicateIbanException.class,
            () -> classUnderTest.transfer(from, to, 2));

        // Then
        Assertions.assertTrue(exception.getMessage().contentEquals(MessageTemplate.DUPLICATE_IBAN.getMessageTemplate() + from.getIban()));
    }

    @Test
    void Should_Transfer_When_Amount_Too_Big_To_Transfer() {
        // Given
        BankAccount from = new CurrentAccount(Optional.of("iban1"), Currency.EUR, 15.0, AccountType.CURRENT_ACCOUNT);
        BankAccount to = new CurrentAccount(Optional.of("iban2"), Currency.EUR, 12.0, AccountType.CURRENT_ACCOUNT);
        BankInstitution bankInstitution1 = new BankInstitution("DSK", "asd");
        BankInstitution bankInstitution2 = new BankInstitution("KBC", "qwe");
        when(bankInstitutionRepository.getBank(from)).thenReturn(bankInstitution1);
        when(bankInstitutionRepository.getBank(to)).thenReturn(bankInstitution2);
        when(bankInstitutionRepository.getTaxMap(any(BankInstitution.class))).thenReturn(getTaxMap());
        when(bankInstitutionRepository.getExchangeRates(any(BankInstitution.class))).thenReturn(getExchangeRate());

        // When
        RuntimeException exception =
            assertThrows(InsufficientAmountTransferException.class,
            () -> classUnderTest.transfer(from, to, 200.0));

        // Then
        Assertions.assertTrue(exception.getMessage().contentEquals(MessageTemplate.INSUFFICIENT_AMOUNT_TO_TRANSFER.getMessageTemplate() + 203.05));
    }

}