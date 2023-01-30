# Bank Simulation Application

## Overview
This application simulates a banking system, including classes for a bank institution, bank accounts, and transactions. The classes and methods allow for withdrawing and depositing money, transferring money between accounts, and generating bank statements.

## Classes
- `Bank`: models a bank institution and contains information such as the name, address, number of customers, and price list (including taxes and fees and currency exchange rates).
- `Account`: models a bank account and contains information such as the owner, IBAN, currency, amount available, and account type (current or savings).
- `Transaction`: models a transaction and contains information such as the source and target account IBANs, source and target banks, amount transferred, source and target currencies, exchange rate, and timestamp.

## Methods
- `withdraw(amount)`: allows for withdrawing money from an account. Available for all account types.
- `deposit(amount)`: allows for depositing money to an account. Available for all account types.
- `transfer(target_account, amount)`: allows for transferring money between accounts. Only available for transfers between two current accounts. Standard fees as specified in the bank's price list are applied. Differentiates between transfers within the same bank and transfers between two banks. Exchange rates of the source bank are applied for transfers between accounts in different currencies.
- `print_info()`: prints information about the banks, accounts, and customers.
- `generate_statement(start_date, end_date)`: prepares bank statements on accounts for a given time range and prints them in a human-readable format.

## Additional Classes
Feel free to create any other classes that will help you accomplish the task.

## How to use
1. Create an instance of the `Bank` class, providing the necessary information such as name and address.
2. Create instances of the `Account` class, providing the necessary information such as owner, IBAN, currency, and account type.
3. Use the `withdraw`, `deposit`, and `transfer` methods to perform transactions on the accounts.
4. Use the `print_info` method to view information about the bank and its accounts.
5. Use the `generate_statement` method to prepare bank statements for a specific time range.

## Note
- For the transactions, only transfers between two current accounts is allowed.
- Upon each transfer standard fees as specified in the bank’s price list will be applied.
- When the two accounts are in different currencies exchange rate of the source bank will be applied.
- Feel free to create any other classes that will help you accomplish the task.
# a-banking-system-v2
