# A Banking System

This is a Java-based application that simulates a banking system. It is a system for managing bank accounts and transactions.

### Features

The Java banking system application includes the following features:

1. Bank Institution Class - models a bank institution, including its name, address, number of customers, and price list (that describes all taxes and fees and all currency exchange rates).
2. Bank Account Class - models a bank account, including information about the owner, iban, currency, amount available, and its type (which can be either a ‘current account’ or a ‘savings account’).
3. Transaction Class - models transactions, including the source and target account ibans, the source and target banks, the amount being transferred, the source and target currencies, the exchange rate, and a timestamp.
4. Withdrawal and deposit methods - allows users to withdraw money from and deposit money into their accounts.
5. Transfer methods - allows transfers only between two current accounts, and applies standard fees as specified in the bank’s price list. Differentiates between transfers within the same bank and transfers between two banks. When the two accounts are in different currencies, it applies the exchange rates of the source bank.
6. Information printing methods - prints information about the banks, accounts, and customers.
7. Bank statement method - prepares bank statements on accounts for a given time range, and prints the statements in a human-readable format.

### How to run the application

1. Clone the repository or download the zip file.
2. Open the project in your preferred IDE or code editor.
3. Run the Main.java file to start the application.
4. Follow the on-screen instructions to use the application.

### Additional classes

Feel free to create any other classes that will help you accomplish the task.

In this project, we created a Customer class to store information about the bank's customers.
