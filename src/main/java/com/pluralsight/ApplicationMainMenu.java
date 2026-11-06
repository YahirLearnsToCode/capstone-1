package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class ApplicationMainMenu {
    private static Scanner myScanner = new Scanner(System.in);

    public static void main(String[] args) {
        printBanner();
        userMainMenuChoice();
    }

    // Takes in user choice for Home Screen
    public static void userMainMenuChoice() {
        boolean done = false;
        do {
            displayMenu();
            String userChoice = myScanner.nextLine().trim().toUpperCase();

            if ((userChoice.isEmpty())) {
                System.out.println("Please Enter A Valid Choice");
            } else {
                char choiceAsChar = userChoice.charAt(0);
                switch (choiceAsChar) {
                    case 'D' -> addDeposit();
                    case 'P' -> makePayment();
                    case 'L' -> userLedgerMenuChoice();
                    case 'X' -> done = true;
                    default -> System.out.println("Not A Valid Choice");
                }
            }

        } while (!done);
    }

    // Takes in user choice for Ledger Screen
    private static void userLedgerMenuChoice() {
        boolean done = false;
        do {
            displayLedgerMenu();
            String userChoice = myScanner.nextLine().trim().toUpperCase();

            if ((userChoice.isEmpty())) {
                System.out.println("Please Enter A Valid Choice");
            } else {
                char choiceAsChar = userChoice.charAt(0);
                switch (choiceAsChar) {
                    case 'A' -> LedgerReports.displayAllEntries();
                    case 'D' -> LedgerReports.displayDeposits();
                    case 'P' -> LedgerReports.displayPayments();
                    case 'R' -> LedgerReports.userReportsMenuChoice();
                    case 'H' -> done = true;
                    default -> System.out.println("Not A Valid Choice");
                }
            }
        } while (!done);
    }

//region Home Screen Menu Methods

    // prompts user for payment info
    private static void makePayment() {
        displayTransactionArt("Payment");
        String paymentDescription = myScanner.nextLine().trim();

        System.out.print("Who Are You Making a Payment To? ");
        String paymentSender = myScanner.nextLine().trim();

        double paymentAmount;
        while (true) {
            System.out.print("Type Your Payment Amount? ");
            paymentAmount = LedgerReports.validAmountNumber();

            if (paymentAmount <= 0)
                System.out.println("Payment Amount Must Be Greater Than 0!");
            else break;
        }
        writePaymentToFile(paymentDescription, paymentSender, paymentAmount);
        transactionSuccessfulScreen("Payment", paymentDescription, paymentSender,paymentAmount);
    }

    // prompts user for deposit info
    private static void addDeposit() {

        displayTransactionArt("Deposit");
        String depositDescription = myScanner.nextLine().trim();

        System.out.print("Who is The Deposit From? ");
        String depositSender = myScanner.nextLine().trim();

        double depositAmount;
        while (true) {
            System.out.print("How Much Would You Like To Deposit? ");
            depositAmount = LedgerReports.validAmountNumber();

            if (depositAmount <= 0) System.out.println("Deposit Amount Must Be Greater Than 0!");
            else break;
        }
        writeDepositToFile(depositDescription, depositSender, depositAmount);
        transactionSuccessfulScreen("Deposit", depositDescription, depositSender,depositAmount);
    }

    //used to write user deposit info to file
    public static void writeDepositToFile(String depositDescription, String depositSender, double depositAmount) {
        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {

            LocalDateTime currentDateAndTime = LocalDateTime.now();

            String[] dateAndTime = currentDateAndTime.toString().split("[T.]");

            String currentDate = dateAndTime[0];
            String currentTime = dateAndTime[1];

            bufWriter.write(formattedFileOutput(currentDate, currentTime, depositDescription, depositSender, depositAmount));
            bufWriter.newLine();

        } catch (IOException e) {
            System.out.println("Error Writing Deposit To File");
        }
    }

    // Used to write user payment info to file
    private static void writePaymentToFile(String paymentDescription, String paymentSender, double paymentAmount) {
        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {

            LocalDateTime currentDateAndTime = LocalDateTime.now();

            String[] dateAndTime = currentDateAndTime.toString().split("[T.]");

            String currentDate = dateAndTime[0];
            String currentTime = dateAndTime[1];

            bufWriter.write(formattedFileOutput(currentDate, currentTime, paymentDescription, paymentSender, -paymentAmount));
            bufWriter.newLine();

        } catch (IOException e) {
            System.out.println("Error Writing Payment To File");
        }
    }

    // Easy Method to properly format data written to file
    public static String formattedFileOutput(String date, String time, String description, String vendor, double amount) {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }
//endregion

    //region Screen Arts
    public static void printBanner() {
        String BANNER = """
                
                   Welcome to
                   █   █ ████      █     █████ ████  █████ █████ ████          ▄▄▄
                    █ █  █   █     █     █     █   █ █     █     █   █      ▄███████▄
                     █   ████      █     ████  █   █ █  ██ ████  ████      ▀▀▀▀▀▀▀▀▀▀▀
                     █   █   █     █     █     █   █ █   █ █     █  █       █ █ █ █ █
                     █   ████      █████ █████ ████   ███  █████ █   █    █████████████
                """;
        System.out.println(BANNER);
    }

    // Home screen displays menu choices for user until exit
    public static void displayMenu() {
        String MENU = """
                 ▄████████████████████████████████████████████████▄
                ████████████████████████████████████████████████████
                ███ ╭──────────────────────────────────────────╮ ███
                ███ │                                          │ ███
                ███ │              * MAIN MENU *               │ ███
                ███ │             Make a Selection             │ ███
                ███ │            ------------------            │ ███
                ███ │     D) Add Deposit                       │ ███
                ███ │     P) Make Payment (Debit)              │ ███
                ███ │     L) Ledger                            │ ███
                ███ │     X) Exit                              │ ███
                ███ │                                          │ ███
                ███ ╰──────────────────────────────────────────╯ ███
                ███  ▒▒▒▒▒▒▒▒▒▒▒▒▒              (o) (o)  [PWR] ● ███
                 ▀████████████████████████████████████████████████▀
                                ▀██████████████████▀
                            ▄▄████████████████████████▄▄
                """;

        System.out.print(MENU);
        System.out.print("Enter Your Choice: ");
    }

    // displays choices for Ledger menu, pops up when user chooses 'L' in main menu
    private static void displayLedgerMenu() {
        String MENU = """
                 ▄████████████████████████████████████████████████▄
                ████████████████████████████████████████████████████
                ███ ╭──────────────────────────────────────────╮ ███
                ███ │                                          │ ███
                ███ │             * LEDGER MENU *              │ ███
                ███ │             Make a Selection             │ ███
                ███ │            ------------------            │ ███
                ███ │     A) All                               │ ███
                ███ │     D) Deposits                          │ ███
                ███ │     P) Payments                          │ ███
                ███ │     R) Reports                           │ ███
                ███ │     H) Home                              │ ███
                ███ ╰──────────────────────────────────────────╯ ███
                ███  ▒▒▒▒▒▒▒▒▒▒▒▒▒              (o) (o)  [PWR] ● ███
                 ▀████████████████████████████████████████████████▀
                                ▀██████████████████▀
                            ▄▄████████████████████████▄▄
                """;

        System.out.print(MENU);
        System.out.print("Enter Your Choice: ");
    }

    private static void displayTransactionArt(String transactionType) {
        String type = transactionType.toUpperCase();

        System.out.println(" ╭─────────────────╮");
        System.out.println(" │  $  " + type + "  $  ╰──────────────────────────╮");
        System.out.println(" ├────────────────────────────────────────────┤");
        System.out.println(" │                                            │");
        System.out.println(" │              MAKING A " + type + "              │");
        System.out.println(" │              ----------------              │");
        System.out.println(" │              $ $ $ $ $ $ $ $               │");
        System.out.println(" │                                            │");
        System.out.println(" ╰────────────────────────────────────────────╯");

        System.out.print("Enter the Description of The " + transactionType + ": ");
    }

    private static void transactionSuccessfulScreen(String transactionType, String description, String personOrCompany, double amount){
        String type = transactionType.toUpperCase();
        String senderOrRecipient = "";
        String time = LocalTime.now().withNano(0).toString();
        String date = LocalDate.now().toString();

        if(transactionType.equals("Deposit")) senderOrRecipient =  "From";
        if (transactionType.equals("Payment")) senderOrRecipient = "To";

        System.out.println(" ╭─────────────────╮");
        System.out.println(" │  $  SUCCESS  $  ╰──────────────────────────╮");
        System.out.println(" ├────────────────────────────────────────────┤");
        System.out.println(" │                                            │");
        System.out.println(" │            " + type + " SUCCESSFUL!             │");
        System.out.println(" │            -------------------             │");
        System.out.println(" │                                            │");
        System.out.printf(" │  %s : %-28s│\n", "Description", description);
        System.out.printf(" │  %-11s : %-28s│\n", senderOrRecipient, personOrCompany); //%-11s Creates white space padding on the right to align with "Description"
        System.out.printf(" │  %-11s : %-28s│\n", "Amount", String.format("$%,.2f", amount)); //%-28 pads the value to 28 characters, if it doesn't reach 28 then it will create white space on the right.
        System.out.printf(" │  %-11s : %-28s│\n", "Date", date);
        System.out.printf(" │  %-11s : %-28s│\n", "Time", time);
        System.out.println(" │                                            │");
        System.out.println(" ╰────────────────────────────────────────────╯");
    }
    //endregion
}