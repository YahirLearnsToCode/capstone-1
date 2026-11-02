package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ApplicationMainMenu {
    private static Scanner myScanner = new Scanner(System.in);

    public static void main(String[] args) {

        userMenuChoice();

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

    public static void userMenuChoice() {
        boolean done = false;
        do {

            displayMenu();
            char userChoice = myScanner.nextLine().trim().toUpperCase().charAt(0);

            switch (userChoice) {
                case 'D' -> addDeposit();
                case 'P' -> makePayment();
                case 'L' -> displayLedger();
                case 'X' -> done = true;
                default -> System.out.println("Not A Valid Choice");
            }

        } while (!done);
    }

    private static void displayLedger() {
    }

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

    public static String formattedFileOutput(String date, String time, String description, String vendor, double amount) {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }
}
