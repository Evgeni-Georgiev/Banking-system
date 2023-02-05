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
import java.util.List;
import java.util.Map;

import static eu.deltasource.internship.abankingsystem.repository.bankAccountRepository.BankAccountRepositoryImpl.countOfAccountOwnerHas;

public class Application {

    public static void main(String[] args) {

        OwnerRepository ownerRepository = OwnerRepositoryImpl.getInstance();
        OwnerService ownerService = new OwnerImpl(ownerRepository);

        BankInstitutionRepository bankInstitutionRepository = BankInstitutionRepositoryImpl.getInstance();
        BankInstitutionService bankInstitutionService = new BankInstitutionImpl(bankInstitutionRepository);

        BankAccountRepository bankAccountRepository = BankAccountRepositoryImpl.getInstance();
        BankAccountService bankAccountService = new BankAccountServiceImpl(bankAccountRepository);

        TransactionRepository transactionRepository = TransactionRepositoryImpl.getInstance();
        TransactionService transactionService = new TransactionServiceImpl(transactionRepository, bankInstitutionRepository, bankAccountRepository);


//		Map<Taxes, Double> taxMap1 = new HashMap<>();
//		taxMap1.put(Taxes.TAX_TO_THE_SAME_BANK, 2.10);
//		taxMap1.put(Taxes.TAX_TO_DIFFERENT_BANK, 3.05);
//
//		Map<Taxes, Double> taxMap2 = new HashMap<>();
//		taxMap1.put(Taxes.TAX_TO_THE_SAME_BANK, 1.10);
//		taxMap1.put(Taxes.TAX_TO_DIFFERENT_BANK, 1.05);

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

//        bankInstitutionRepository.addPriceListToMap(Taxes.TAX_TO_THE_SAME_BANK, 1.3);
//        bankInstitutionRepository.addPriceListToMap(Taxes.TAX_TO_DIFFERENT_BANK, 2.3);

//		Double tax1 = bankInstitutionRepository.getTaxByTax(Taxes.TAX_TO_THE_SAME_BANK);
//		Double tax2 = bankInstitutionRepository.getTaxByTax(Taxes.TAX_TO_DIFFERENT_BANK);
//		System.out.println("TAX: ");
//		System.out.println(tax1);
//		System.out.println(tax2);
//		System.out.println("TAX: ");


        // Create owner
        ownerService.createOwner("Simon");
        ownerService.createOwner("Vix");
        ownerService.createOwner("Kilian");

        // Get owner by id
        Owner simon = ownerService.getOwnerById(1);
        Owner vix = ownerService.getOwnerById(2);
        Owner kilian = ownerService.getOwnerById(3);

        // Add owner to Map(db)
        ownerRepository.addOwnerToMap(simon);
        ownerRepository.addOwnerToMap(vix);
        ownerRepository.addOwnerToMap(kilian);

        // Create BankInstitution

//        bankInstitutionService.createBankInstitution("DSK", "NY 10001", exchangeRates);

//        bankInstitutionService.createBankInstitution("Raiffeisen", "London EC3V 9EL", exchangeRates);

//        bankInstitutionService.createBankInstitution("DSK", "NY 10001", new HashMap<>() {{
//            put(Taxes.TAX_TO_THE_SAME_BANK, 1.3);
//            put(Taxes.TAX_TO_DIFFERENT_BANK, 2.3);
//        }}, exchangeRates);
//
//        bankInstitutionService.createBankInstitution("Raiffeisen", "London EC3V 9EL", new HashMap<>() {{
//            put(Taxes.TAX_TO_THE_SAME_BANK, 3.3);
//            put(Taxes.TAX_TO_DIFFERENT_BANK, 5.3);
//        }}, exchangeRates);

		bankInstitutionService.createBankInstitution("DSK", "NY 10001", exchangeRates);

		bankInstitutionService.createBankInstitution("Raiffeisen", "London EC3V 9EL", exchangeRates);

        // Get BankInstitution by id
        BankInstitution dsk = bankInstitutionService.getBankInstitutionById(1);
        BankInstitution raiffeisen = bankInstitutionService.getBankInstitutionById(2);

//		Map<Taxes, Double> makermap = new HashMap<>();
//		makermap.put(Taxes.TAX_TO_THE_SAME_BANK, tax1);
//		makermap.put(Taxes.TAX_TO_DIFFERENT_BANK, tax2);

//		bankInstitutionRepository.addTaxToBankMap(dsk);

//        bankInstitutionRepository.addBankAccountsToBank();

        // Add BankInstitution to Map(db)
        bankInstitutionRepository.addBankToMap(dsk);
        bankInstitutionRepository.addBankToMap(raiffeisen);

//		System.out.println(bankInstitutionRepository.getTaxMap(dsk));
//		System.out.println(bankInstitutionRepository.getTaxMap(raiffeisen));

//		bankInstitutionRepository.addTaxToBankMap(dsk);
//		bankInstitutionRepository.addTaxToBankMap(raiffeisen);

//		System.out.println(bankInstitutionRepository.getTaxByBank(dsk, Taxes.TAX_TO_THE_SAME_BANK));


        // Create account
        bankAccountService.createBankAccount(vix, "GB15Z202150876987676", Currency.BGN, 2000.0, 'C');
        bankAccountService.createBankAccount(kilian, "FR761255123456789678967892", Currency.BGN, 2000.0, 'C');
        bankAccountService.createBankAccount(simon, "DE89370400440532013000", Currency.BGN, 2000.0, 'C');

		Map<Taxes, Double> taxMap1 = new HashMap<>();
		taxMap1.put(Taxes.TAX_TO_THE_SAME_BANK, 2.10);
		taxMap1.put(Taxes.TAX_TO_DIFFERENT_BANK, 3.05);
		bankInstitutionRepository.addTaxToBankMap(dsk, taxMap1);

		Map<Taxes, Double> taxMap2 = new HashMap<>();
		taxMap2.put(Taxes.TAX_TO_THE_SAME_BANK, 1.10);
		taxMap2.put(Taxes.TAX_TO_DIFFERENT_BANK, 1.05);
		bankInstitutionRepository.addTaxToBankMap(raiffeisen, taxMap2);

//		System.out.println(bankInstitutionRepository.findTaxesByBank(dsk));
//		System.out.println(bankInstitutionRepository.findTaxesByBank(raiffeisen));

		Map<Taxes, Double> retrievedTaxMap1 = bankInstitutionRepository.getTaxMap(dsk);
		Map<Taxes, Double> retrievedTaxMap2 = bankInstitutionRepository.getTaxMap(raiffeisen);

		System.out.println(retrievedTaxMap1);
		System.out.println(retrievedTaxMap2);

		System.out.println(dsk);
		System.out.println(raiffeisen);

		List<Taxes> taxesToAdd1 = List.of(Taxes.TAX_TO_THE_SAME_BANK, Taxes.TAX_TO_DIFFERENT_BANK);
		List<Taxes> taxesToAdd2 = List.of(Taxes.TAX_TO_THE_SAME_BANK, Taxes.TAX_TO_DIFFERENT_BANK);

		bankInstitutionRepository.filterAndAddTaxesToBank(dsk, taxMap1, taxesToAdd1);
		bankInstitutionRepository.filterAndAddTaxesToBank(raiffeisen, taxMap2, taxesToAdd2);

		Map<Taxes, Double> bank1Taxes = bankInstitutionRepository.getTaxMap(dsk);
		Map<Taxes, Double> bank2Taxes = bankInstitutionRepository.getTaxMap(raiffeisen);

		System.out.println("Taxes for Bank 1: " + bank1Taxes);
		System.out.println("Taxes for Bank 2: " + bank2Taxes);


		// Get account by IBAN
        BankAccount bankAccount1 = bankAccountService.getBankAccountByIban("GB15Z202150876987676");
        BankAccount bankAccount2 = bankAccountService.getBankAccountByIban("FR761255123456789678967892");
        BankAccount bankAccount3 = bankAccountService.getBankAccountByIban("DE89370400440532013000");

        // Add Account to Map(db)
        bankAccountRepository.addBankAccountToMap(bankAccount1);
        bankAccountRepository.addBankAccountToMap(bankAccount2);
        bankAccountRepository.addBankAccountToMap(bankAccount3);

        // Assign Owner to BankAccount
        ownerRepository.addAccountToOwner(vix, bankAccount1);
        ownerRepository.addAccountToOwner(kilian, bankAccount2);
        ownerRepository.addAccountToOwner(simon, bankAccount3);

        // Get Owner by BankAccount
//        System.out.println(ownerRepository.getOwner(bankAccount1));

        // Assign bank to account
        bankInstitutionRepository.addAccountToBank(dsk, bankAccount1);
        bankInstitutionRepository.addAccountToBank(dsk, bankAccount2);
        bankInstitutionRepository.addAccountToBank(raiffeisen, bankAccount3);

        System.out.println("\n Transfer: \n");
        System.out.println("Current amount of source account: " + bankAccount1.getAmountAvailable());
        System.out.println("Current amount of target account: " + bankAccount2.getAmountAvailable());
        transactionService.transfer(bankAccount1, bankAccount2, 10.0);
//        transactionService.transfer(bankAccount2, bankAccount1, 10.0);
//        transactionService.transfer(bankAccount3, bankAccount1, 10.0);
//        transactionService.transfer(bankAccount3, bankAccount2, 10.0);
//        transactionService.transfer(bankAccount2, bankAccount3, 10.0);
        System.out.println("After transfer: " + bankAccount2.getAmountAvailable());
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


        LocalDate start = LocalDate.of(2023, 2, 5);
        LocalDate end = LocalDate.of(2023, 2, 7);
//        System.out.println(bankAccount1.getTransferStatementLocal(start, end));
        System.out.println(bankAccountRepository.getTransferStatementLocal(start, end));

//        System.out.println(bankAccountRepository.getTransferStatement(bankAccount2));

//        transactionService.transactionHistory(bankAccount1);

//        System.out.println(bankAccountRepository.getTransferStatement(bankAccount1));

//        System.out.println(bankAccount1.getTransferStatement());

//        BankAccount.countOfAccountOwnerHas(vix);
//        transactionService.transactionHistory(bankAccount1);

//        System.out.println(bankAccountRepository.getTransferStatement(bankAccount1));
//        System.out.println(ownerRepository.getOwner(bankAccount1));

        countOfAccountOwnerHas(simon);

		System.out.println("DSK has theres accounts: ");

	}
}
