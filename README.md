# Java Swing Finance Tracker

A simple, user-friendly **Java Swing** application to track personal finances, manage transactions, and view spending totals. The application supports **persistent storage** using serialized files (.ser) for user-specific transaction histories.

---

## Features

- **User Login Screen**
  - Enter your name before starting the tracker.
  - Personalized transaction storage per user.

- **Transaction Management**
  - Add a new transaction with:
    - Name
    - Date
    - Amount
    - Category
    - Description
  - Remove existing transactions from the list.

- **Financial Summary**
  - View the **total amount** of all transactions.

- **Persistent Storage**
  - Automatically loads existing transactions for a user.
  - Saves transactions in .ser files to preserve data between sessions.

- **Interactive UI**
  - Built with **Java Swing**.
  - Mouse hover effects for buttons for a better user experience.

---

## How to Run

1.  **Prerequisites**: Ensure you have a **Java Development Kit (JDK)** installed on your system.
2.  **Clone or Download**: Get the source code for the project.
3.  **Compile**: Open your terminal or command prompt, navigate to the project directory, and compile the Java files using the `javac` command:
    ```bash
    javac Project/*.java
    ```
4.  **Run**: Execute the main class, `OpeningFrame`, to start the application:
    ```bash
    java Project.OpeningFrame
    ```
    This will launch the user login window where you can enter your name to begin.

---

## Project Structure

* `OpeningFrame.java`: This class creates the initial **GUI** frame where the user inputs their name. It serves as the entry point for the application.
* `FinanceTracker.java`: This is the core class of the application. It handles the **main financial tracking interface**, including the transaction table, input fields, and all button functionalities.
* `Transaction.java`: A simple **data model class** that represents a single transaction. It implements the `Serializable` interface to allow for data persistence.
* `TransactionTableModel.java`: An inner class within `FinanceTracker.java` that extends `DefaultTableModel`. It manages the data displayed in the `JTable`.

---

## File Persistence

The application uses **Java's serialization** to save and load transaction data. Each user's transactions are stored in a separate file named `[username]_transactions.ser`. This ensures that each user has their own unique and persistent financial history.

* `saveTransactions()`: This method serializes the list of `Transaction` objects and writes it to a file.
* `loadTransactions()`: This method deserializes the data from the user-specific file and populates the transaction table when the application starts.

---
