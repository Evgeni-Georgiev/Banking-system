package eu.deltasource.internship.abankingsystem;

import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.abankingsystem.enums.Taxes;
import eu.deltasource.internship.abankingsystem.model.BankAccount;
import eu.deltasource.internship.abankingsystem.model.BankInstitution;
import eu.deltasource.internship.abankingsystem.model.Owner;
import eu.deltasource.internship.abankingsystem.repository.bankAccountRepository.BankAccountRepository;
import eu.deltasource.internship.abankingsystem.repository.bankAccountRepository.BankAccountRepositoryImpl;
import eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository.BankInstitutionRepository;
import eu.deltasource.internship.abankingsystem.repository.bankInstitutionRepository.BankInstitutionRepositoryImpl;
import eu.deltasource.internship.abankingsystem.repository.ownerRepository.OwnerRepository;
import eu.deltasource.internship.abankingsystem.repository.ownerRepository.OwnerRepositoryImpl;
import eu.deltasource.internship.abankingsystem.repository.transactionRepository.TransactionRepository;
import eu.deltasource.internship.abankingsystem.repository.transactionRepository.TransactionRepositoryImpl;
import eu.deltasource.internship.abankingsystem.service.OwnerService.OwnerImpl;
import eu.deltasource.internship.abankingsystem.service.OwnerService.OwnerService;
import eu.deltasource.internship.abankingsystem.service.TransactionService.TransactionService;
import eu.deltasource.internship.abankingsystem.service.TransactionService.TransactionServiceImpl;
import eu.deltasource.internship.abankingsystem.service.bankAccountService.BankAccountService;
import eu.deltasource.internship.abankingsystem.service.bankAccountService.BankAccountServiceImpl;
import eu.deltasource.internship.abankingsystem.service.bankInstitutionService.BankInstitutionImpl;
import eu.deltasource.internship.abankingsystem.service.bankInstitutionService.BankInstitutionService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Application {

    public static void main(String[] args) {

        OwnerRepository ownerRepository = OwnerRepositoryImpl.getInstance();
        OwnerService ownerService = new OwnerImpl(ownerRepository);

        BankInstitutionRepository bankInstitutionRepository = BankInstitutionRepositoryImpl.getInstance();
        BankInstitutionService bankInstitutionService = new BankInstitutionImpl(bankInstitutionRepository);

        TransactionRepository transactionRepository = TransactionRepositoryImpl.getInstance();
        TransactionService transactionService = new TransactionServiceImpl(transactionRepository, bankInstitutionRepository);

        BankAccountRepository bankAccountRepository = BankAccountRepositoryImpl.getInstance();
        BankAccountService bankAccountService = new BankAccountServiceImpl(bankAccountRepository);

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

        // Create owner
        ownerService.createOwner("Simon");
        ownerService.createOwner("Vix");
        ownerService.createOwner("Kilian");

        // Get owner by id
        Owner simon = ownerService.getOwnerById(1);
        Owner vix2 = ownerService.getOwnerById(2);
        Owner kilian = ownerService.getOwnerById(3);

        // Add owner to Map(db)
        ownerRepository.addOwnerToMap(simon);
        ownerRepository.addOwnerToMap(vix2);
        ownerRepository.addOwnerToMap(kilian);

        // Create BankInstitution
        bankInstitutionService.createBankInstitution("DSK", "NY 10001", new HashMap<>() {{
            put(Taxes.TAX_TO_THE_SAME_BANK, 1.3);
            put(Taxes.TAX_TO_DIFFERENT_BANK, 2.3);
        }}, exchangeRates);

        bankInstitutionService.createBankInstitution("Raiffeisen", "London EC3V 9EL", new HashMap<>() {{
            put(Taxes.TAX_TO_THE_SAME_BANK, 3.3);
            put(Taxes.TAX_TO_DIFFERENT_BANK, 5.3);
        }}, exchangeRates);

        // Get BankInstitution by id
        BankInstitution dsk = bankInstitutionService.getBankInstitutionById(1);
        BankInstitution raiffeisen = bankInstitutionService.getBankInstitutionById(2);

        // Add BankInstitution to Map(db)
        bankInstitutionRepository.addBankToMap(dsk);
        bankInstitutionRepository.addBankToMap(raiffeisen);


        // Create account
        bankAccountService.createBankAccount(vix2, "GB15Z202150876987676", Currency.BGN, 2000., 'C');
        bankAccountService.createBankAccount(kilian, "FR761255123456789678967892", Currency.BGN, 2000., 'C');

        // Get account by IBAN
        BankAccount bankAccount1 = bankAccountService.getBankAccountByIban("GB15Z202150876987676");
        BankAccount bankAccount2 = bankAccountService.getBankAccountByIban("FR761255123456789678967892");

        // Add Account to Map(db)
        bankAccountRepository.addBankAccountToMap(bankAccount1);
        bankAccountRepository.addBankAccountToMap(bankAccount2);


        // Assign Owner to BankAccount
        ownerRepository.addAccountToOwner(vix2, bankAccount1);
        ownerRepository.addAccountToOwner(kilian, bankAccount2);

        // Get Owner by BankAccount
//        System.out.println(ownerRepository.getOwner(bankAccount1));

        // Assign bank to account
        bankInstitutionRepository.addAccountToBank(dsk, bankAccount1);
        bankInstitutionRepository.addAccountToBank(dsk, bankAccount2);

        System.out.println("Current amount of source account: " + bankAccount1.getAmountAvailable());
        System.out.println("Current amount of target account: " + bankAccount2.getAmountAvailable());
        transactionService.transfer(bankAccount1, bankAccount2, 10.0);
        System.out.println("After transfer: " + bankAccount2.getAmountAvailable());
        System.out.println("After transfer of source account: " + bankAccount1.getAmountAvailable());
        System.out.println("After transfer of source account: " + bankAccount2.getAmountAvailable());

        System.out.println("Current amount of source account: " + bankAccount1.getAmountAvailable());
        System.out.println(bankAccount1.getAmountAvailable());
        transactionService.withdraw(bankAccount1, 20.00);
        System.out.println(bankAccount1.getAmountAvailable());
        System.out.println("After withdraw: " + bankAccount1.getAmountAvailable());
        System.out.println("After withdraw of source account: " + bankAccount1.getAmountAvailable());

        System.out.println("Current amount of source account: " + bankAccount1.getAmountAvailable());
        System.out.println(bankAccount1.getAmountAvailable());
        transactionService.deposit(bankAccount1, 2000.00, Currency.EUR);
        System.out.println(bankAccount1.getAmountAvailable());
        System.out.println("After deposit: " + bankAccount1.getAmountAvailable());
        System.out.println("After deposit of source account: " + bankAccount1.getAmountAvailable());


        LocalDate start = LocalDate.of(2023, 2, 2);
        LocalDate end = LocalDate.of(2023, 2, 8);
        System.out.println(bankAccount1.getTransferStatementLocal(start, end));

        transactionService.transactionHistory(bankAccount1);

        System.out.println(bankAccount1.getTransferStatement());

        BankAccount.countOfAccountOwnerHas(vix2);
        transactionService.transactionHistory(bankAccount1);

    }
}
