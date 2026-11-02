package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ApplicationMainMenu {
    private static Scanner myScanner = new Scanner(System.in);

    public static void main(String[] args) {

        userMainMenuChoice();

    }

    // Home screen displays menu choices for user until exit TODO: Make menu screen fancier after main project finished
    public static void displayMenu() {
        String menu = """
                     * MENU *
                  Make a Selection
                 ------------------
                D) Add Deposit
                P) Make Payment (Debit)
                L) Ledger
                X) Exit""";

        System.out.println(menu);
    }

    // Takes in user choice for Home Screen
    public static void userMainMenuChoice() {
        System.out.println("Enter Your Choice: ");
        boolean done = false;
        do {

            displayMenu();
            char userChoice = myScanner.nextLine().trim().toUpperCase().charAt(0);

            switch (userChoice) {
                case 'D' -> addDeposit();
                case 'P' -> makePayment();
                case 'L' -> userLedgerMenuChoice();
                case 'X' -> done = true;
                default -> System.out.println("Not A Valid Choice");
            }

        } while (!done);
    }

    // displays choices for Ledger menu, pops up when user chooses 'L' in main menu
    private static void displayLedgerMenu() {
        String menu = """
                  * LEDGER MENU *
                  Make a Selection
                --------------------
                A) All
                D) Deposits
                P) Payments
                R) Reports
                H) Home""";

        System.out.println(menu);
    }

    // Takes in user choice for Ledger Screen
    private static void userLedgerMenuChoice() {
        System.out.println("Enter Your Choice: ");
        boolean done = false;
        do {

            displayLedgerMenu();
            char userChoice = myScanner.nextLine().trim().toUpperCase().charAt(0);

            switch (userChoice) {
                case 'A' -> LedgerReports.displayAllEntries();
                //case 'D' -> displayDeposits();
                //case 'P' -> displayPayments();
                case 'H' -> done = true;
                default -> System.out.println("Not A Valid Choice");
            }

        } while (!done);
    }

    //region Ledger Screen Menu Methods


    //endregion

    //region Home Screen Menu Methods

    // prompts user for payment info
    private static void makePayment() {
        System.out.println("Enter a Description for The Payment: ");
        String paymentDescription = myScanner.nextLine().trim();

        System.out.println("Who Are You Making a Payment To? ");
        String paymentSender = myScanner.nextLine().trim();

        double paymentAmount;
        while (true) {
            System.out.println("Type Your Payment Amount? ");
            paymentAmount = Double.parseDouble(myScanner.nextLine().trim());

            if (paymentAmount <= 0) System.out.println("Payment Amount Must Be Greater Than 0!");
            else break;
        }
        writePaymentToFile(paymentDescription, paymentSender, paymentAmount);
    }

    // prompts user for deposit info
    private static void addDeposit() {
        System.out.println("Enter the Description of The Deposit: ");
        String depositDescription = myScanner.nextLine().trim();

        System.out.println("Who is The Deposit From? ");
        String depositSender = myScanner.nextLine().trim();

        double depositAmount;
        while (true) {
            System.out.println("How Much Would You Like To Deposit? ");
            depositAmount = Double.parseDouble(myScanner.nextLine().trim());

            if (depositAmount <= 0) System.out.println("Deposit Amount Must Be Greater Than 0!");
            else break;
        }
        writeDepositToFile(depositDescription, depositSender, depositAmount);
    }

    //used to write user deposit info to file
    public static void writeDepositToFile(String depositDescription, String depositSender, double depositAmount) {
        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv",true));

            LocalDateTime currentDateAndTime = LocalDateTime.now();

            String[] dateAndTime = currentDateAndTime.toString().split("[T.]");

            String currentDate = dateAndTime[0];
            String currentTime = dateAndTime[1];

            bufWriter.write(formattedFileOutput(currentDate, currentTime, depositDescription, depositSender, depositAmount));
            bufWriter.newLine();
            bufWriter.close();

        } catch (IOException e) {
            System.out.println("Error Writing Deposit To File");
        }
    }

    // Used to write user payment info to file
    private static void writePaymentToFile(String paymentDescription, String paymentSender, double paymentAmount) {
        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));

            LocalDateTime currentDateAndTime = LocalDateTime.now();

            String[] dateAndTime = currentDateAndTime.toString().split("[T.]");

            String currentDate = dateAndTime[0];
            String currentTime = dateAndTime[1];

            bufWriter.write(formattedFileOutput(currentDate, currentTime, paymentDescription, paymentSender, -paymentAmount));
            bufWriter.newLine();
            bufWriter.close();

        } catch (IOException e) {
            System.out.println("Error Writing Payment To File");
        }
    }

    // Easy Method to properly format data written to file
    public static String formattedFileOutput(String date, String time, String description, String vendor, double amount) {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }

    //endregion
}
