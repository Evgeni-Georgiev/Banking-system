package eu.deltasource.internship.abankingsystem;

import eu.deltasource.internship.abankingsystem.enums.AccountType;
import eu.deltasource.internship.abankingsystem.enums.Currency;
import eu.deltasource.internship.abankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.abankingsystem.enums.TaxType;
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
import eu.deltasource.internship.abankingsystem.service.bankAccountService.BankAccountService;
import eu.deltasource.internship.abankingsystem.service.bankAccountService.BankAccountServiceImpl;
import eu.deltasource.internship.abankingsystem.service.bankInstitutionService.BankInstitutionService;
import eu.deltasource.internship.abankingsystem.service.bankInstitutionService.BankInstitutionServiceImpl;
import eu.deltasource.internship.abankingsystem.service.ownerService.OwnerService;
import eu.deltasource.internship.abankingsystem.service.ownerService.OwnerServiceImpl;
import eu.deltasource.internship.abankingsystem.service.transactionService.TransactionService;
import eu.deltasource.internship.abankingsystem.service.transactionService.TransactionServiceImpl;
import eu.deltasource.internship.abankingsystem.utility.ExchangeRatesReader;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Application {

    public static void main(String[] args) {

        OwnerRepository ownerRepository = OwnerRepositoryImpl.getInstance();
        BankInstitutionRepository bankInstitutionRepository = BankInstitutionRepositoryImpl.getInstance();
        BankAccountRepository bankAccountRepository = BankAccountRepositoryImpl.getInstance();
        TransactionRepository transactionRepository = TransactionRepositoryImpl.getInstance();

        BankInstitutionService bankInstitutionService = new BankInstitutionServiceImpl(bankInstitutionRepository, ownerRepository);
        OwnerService ownerService = new OwnerServiceImpl(ownerRepository, bankInstitutionRepository);
        BankAccountService bankAccountService = new BankAccountServiceImpl(bankAccountRepository, bankInstitutionRepository);
        TransactionService transactionService = new TransactionServiceImpl(transactionRepository, bankInstitutionRepository, bankAccountRepository);

        String jsonName = "src/main/resources/exchangeRates.json";
        Map<String, Double> exchangeRates;
        exchangeRates = ExchangeRatesReader.readExchangeRatesFile(jsonName);

        Map<ExchangeRate, Double> exchangeRates1 = new HashMap<>();
        exchangeRates1.put(ExchangeRate.BGNEUR, exchangeRates.get("BGNEUR"));
        exchangeRates1.put(ExchangeRate.BGNUSD, exchangeRates.get("BGNUSD"));
        exchangeRates1.put(ExchangeRate.USDBGN, exchangeRates.get("USDBGN"));
        exchangeRates1.put(ExchangeRate.USDEUR, exchangeRates.get("USDEUR"));
        exchangeRates1.put(ExchangeRate.EURBGN, exchangeRates.get("EURBGN"));
        exchangeRates1.put(ExchangeRate.EURUSD, exchangeRates.get("EURUSD"));
        exchangeRates1.put(ExchangeRate.EUREUR, exchangeRates.get("EUREUR"));
        exchangeRates1.put(ExchangeRate.BGNBGN, exchangeRates.get("BGNBGN"));
        exchangeRates1.put(ExchangeRate.USDUSD, exchangeRates.get("USDUSD"));

        // Create owner
        ownerService.createOwner("Simon");
        ownerService.createOwner("Vix");
        ownerService.createOwner("Kilian");

        // Get owner by id
        Owner simon = ownerService.getOwnerById(1);
        Owner vix = ownerService.getOwnerById(2);
        Owner kilian = ownerService.getOwnerById(3);

        // Create BankInstitution
		bankInstitutionService.createBankInstitution("DSK", "NY 10001");
		bankInstitutionService.createBankInstitution("Raiffeisen", "London EC3V 9EL");

        // Get BankInstitution by id
        BankInstitution dsk = bankInstitutionService.getBankInstitutionById(1);
        BankInstitution raiffeisen = bankInstitutionService.getBankInstitutionById(2);

        // Create account
        bankAccountService.createBankAccount(Optional.of("GB15Z202150876987676"), Currency.BGN, 2000.0, AccountType.CURRENT_ACCOUNT);
        bankAccountService.createBankAccount(Optional.of("FR761255123456789678967892"), Currency.BGN, 2000.0, AccountType.CURRENT_ACCOUNT);
        bankAccountService.createBankAccount(Optional.of("DE89370400440532013000"), Currency.BGN, 2000.0, AccountType.CURRENT_ACCOUNT);

		Map<TaxType, Double> taxMap1 = new HashMap<>();
		taxMap1.put(TaxType.TAX_TO_THE_SAME_BANK, 2.10);
		taxMap1.put(TaxType.TAX_TO_DIFFERENT_BANK, 3.05);
		bankInstitutionRepository.addTaxToBankMap(dsk, taxMap1);

		Map<TaxType, Double> taxMap2 = new HashMap<>();
		taxMap2.put(TaxType.TAX_TO_THE_SAME_BANK, 1.10);
		taxMap2.put(TaxType.TAX_TO_DIFFERENT_BANK, 1.05);
		bankInstitutionRepository.addTaxToBankMap(raiffeisen, taxMap2);

		List<TaxType> taxTypeToAdd1 = List.of(TaxType.TAX_TO_THE_SAME_BANK, TaxType.TAX_TO_DIFFERENT_BANK);
		List<TaxType> taxTypeToAdd2 = List.of(TaxType.TAX_TO_THE_SAME_BANK, TaxType.TAX_TO_DIFFERENT_BANK);

		bankInstitutionRepository.filterAndAddTaxesToBank(dsk, taxMap1, taxTypeToAdd1);
		bankInstitutionRepository.filterAndAddTaxesToBank(raiffeisen, taxMap2, taxTypeToAdd2);

        // Add exchange rates to bank
        bankInstitutionRepository.addExchangeRatesToBank(dsk, exchangeRates1);
        bankInstitutionRepository.addExchangeRatesToBank(raiffeisen, exchangeRates1);

		// Get account by IBAN
        BankAccount bankAccount1 = bankAccountService.getBankAccountByIban("GB15Z202150876987676");
        BankAccount bankAccount2 = bankAccountService.getBankAccountByIban("FR761255123456789678967892");
        BankAccount bankAccount3 = bankAccountService.getBankAccountByIban("DE89370400440532013000");

        System.out.println(bankAccount1);
        System.out.println(bankAccount2);
        System.out.println(bankAccount3);

        // Add Account to Map(db)
        bankAccountRepository.addBankAccountToMap(bankAccount1);
        bankAccountRepository.addBankAccountToMap(bankAccount2);
        bankAccountRepository.addBankAccountToMap(bankAccount3);

        // Assign Owner to BankAccount(s)
        ownerRepository.addOwnerToAccounts(vix, List.of(bankAccount1));
        ownerRepository.addOwnerToAccounts(simon, List.of(bankAccount3));
        ownerRepository.addOwnerToAccounts(kilian, List.of(bankAccount2));

        // Get Owner by BankAccount
//        System.out.println(ownerRepository.getOwner(bankAccount1));

        // Assign bank to account(s)
        bankInstitutionRepository.addAccountToBank(dsk, List.of(bankAccount1, bankAccount2));
        bankInstitutionRepository.addAccountToBank(raiffeisen, List.of(bankAccount3));

        System.out.println("\n Transfer: \n");
        System.out.println("Current amount of source account: " + bankAccount1.getAmountAvailable());
        System.out.println("Current amount of target account: " + bankAccount2.getAmountAvailable());
        transactionService.transfer(bankAccount1, bankAccount2, 10.0);
        transactionService.transfer(bankAccount2, bankAccount1, 10.0);
        transactionService.transfer(bankAccount3, bankAccount1, 10.0);
        transactionService.transfer(bankAccount3, bankAccount2, 10.0);
        transactionService.transfer(bankAccount2, bankAccount3, 10.0);
        System.out.println("After transfer: " + String.format("%.2f", bankAccount2.getAmountAvailable()));
        System.out.println("After transfer of source account: " + bankAccount1.getAmountAvailable());
        System.out.println("After transfer of source account: " + bankAccount2.getAmountAvailable());

        System.out.println("\n Withdraw: \n");
        System.out.println("Current amount of source account: " + bankAccount1.getAmountAvailable());
        transactionService.withdraw(bankAccount1, 30.00);
        transactionService.withdraw(bankAccount2, 20.00);
        transactionService.withdraw(bankAccount3, 20.00);
        System.out.println("After withdraw: " + bankAccount1.getAmountAvailable());

        System.out.println("\n Deposit: \n");
        System.out.println("Current amount of source account: " + bankAccount1.getAmountAvailable());
        transactionService.deposit(bankAccount1, 2000.00, Currency.EUR);
        transactionService.deposit(bankAccount2, 2000.00, Currency.EUR);
        System.out.println("After deposit: " + bankAccount1.getAmountAvailable());


        LocalDate start = LocalDate.of(2023, 2, 7);
        LocalDate end = LocalDate.of(2023, 2, 10);
//        System.out.println(bankAccount1.getTransferStatementLocal(start, end));
        System.out.println("Date range:");
        System.out.println(bankAccountRepository.getTransferStatementLocal(start, end));
        System.out.println("=============");

        System.out.println(bankAccountRepository.getTransferStatementByAccount(bankAccount2));

        System.out.println("Simon's all transactions: ");
        System.out.println(bankAccountRepository.getTransferStatementByAccount(bankAccount2));

        System.out.println("Simon has accounts: ");

        System.out.println(ownerRepository.getAccountsForOwner(simon));

        System.out.println("All Transactions of DSK: ");
        System.out.println(bankInstitutionRepository.getAllTransactions(dsk));

        System.out.println("All Transactions of Raifaizen: ");
        System.out.println(bankInstitutionRepository.getAllTransactions(raiffeisen));

        System.out.println("All accounts of simon: ");
        System.out.println(ownerRepository.getAccountsForOwner(simon));

        System.out.println("Get all Owners of bank: ");
        System.out.println(bankInstitutionService.filterOwnersByBank(raiffeisen));
        System.out.println(bankInstitutionService.filterOwnersByBank(dsk));

        System.out.println("Get all accounts of bank: ");
        System.out.println(bankInstitutionService.filterAccountsInBank(raiffeisen));
        System.out.println(bankInstitutionService.filterAccountsInBank(dsk));


    }

}
