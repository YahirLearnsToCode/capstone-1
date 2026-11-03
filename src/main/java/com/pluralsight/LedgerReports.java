package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class LedgerReports {
    private static Scanner myScanner = new Scanner(System.in);


    //region Ledger Menu Methods
    // Will display all entries in the transaction.csv file in order from newest to oldest
    public static void displayAllEntries() {
        ArrayList<String> allEntries = new ArrayList<>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));

            String line;
            while ((line = bufReader.readLine()) != null) {
                allEntries.add(line);
            }

        } catch (IOException e) {
            System.out.println("Error Reading File");
        }

        allEntries.sort(Comparator.reverseOrder());
        for (String entry : allEntries) {
            System.out.println(entry);
        }
    }

    // Will display only positive entries in the transaction.csv
    public static void displayDeposits() {
        ArrayList<String> allEntries = new ArrayList<>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));

            String line;
            while ((line = bufReader.readLine()) != null) {

                String[] negativeCheck = line.split("\\|");

                if (!negativeCheck[4].contains("-"))
                    allEntries.add(line);
            }

        } catch (IOException e) {
            System.out.println("Error Reading File");
        }

        allEntries.sort(Comparator.reverseOrder());
        for (String entry : allEntries) {
            System.out.println(entry);
        }
    }

    // Will display only negative entries in the transaction.csv
    public static void displayPayments() {
        ArrayList<String> allEntries = new ArrayList<>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));

            String line;
            while ((line = bufReader.readLine()) != null) {

                String[] negativeCheck = line.split("\\|");

                if (negativeCheck[4].contains("-"))
                    allEntries.add(line);
            }

        } catch (IOException e) {
            System.out.println("Error Reading File");
        }

        allEntries.sort(Comparator.reverseOrder());
        for (String entry : allEntries) {
            System.out.println(entry);
        }
    }

    // displays choices for Reports menu
    public static void displayReportsMenu() {
        String menu = """
                  * Reports MENU *
                  Make a Selection
                --------------------
                1) Month To Date
                2) Previous Month
                3) Year To Date
                4) Previous Year
                5) Search By Vendor
                0) Back""";

        System.out.println(menu);
    }

    // Takes in user choice for Reports Screen
    public static void userReportsMenuChoice() {
        System.out.println("Enter Your Choice: ");
        boolean done = false;
        do {

            displayReportsMenu();
            char userChoice = myScanner.nextLine().trim().toUpperCase().charAt(0);

            switch (userChoice) {
                case '1' -> searchByMonthToDate();
                case '2' -> searchByPreviousMonth();
                case '3' -> searchByYearToDate();
                case '4' -> searchByPreviousYear();
                case '5' -> searchByVendor();
                case '0' -> done = true;
                default -> System.out.println("Not A Valid Choice");
            }

        } while (!done);
    }
//endregion     Reports Menu Methods

    //region Reports Menu Methods
    // Prompts user for a vendor they want to search for and compares each file line for a match
    private static void searchByVendor() {
        ArrayList<String> specificVendor = new ArrayList<>();

        System.out.println("What Vendor Do You Want To Sort By? ");
        String userInputVendor = myScanner.nextLine().trim();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));

            String line;
            while ((line = bufReader.readLine()) != null) {

                String[] lineSplit = line.split("\\|");
                String vendor = lineSplit[3];

                if (userInputVendor.equals(vendor))
                    specificVendor.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error Reading File For Search Vendor");
        }

        System.out.println(specificVendor + "\n");
    }

    // Displays all transactions from previous year
    private static void searchByPreviousYear() {
        ArrayList<String> previousYearTransactions = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();

        System.out.println("Your Previous Year Transactions: ");

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));

            String line;
            while ((line = bufReader.readLine()) != null) {

                String[] lineSplit = line.split("\\|", 2); // returns [date], [other stuff](not important)

                try {
                    int parsedDate = LocalDate.parse(lineSplit[0]).getYear(); // getting year for each line

                    if (parsedDate == currentYear - 1)
                        previousYearTransactions.add(line);

                } catch (DateTimeException e) {
                }  //catches and skips line that can't be parsed
            }
        } catch (IOException e) {
            System.out.println("Error Reading File For Search Vendor");
        }
        if (previousYearTransactions.isEmpty()) {
            System.out.println("No Previous Year Activity To Show");
        } else {
            for (String transaction : previousYearTransactions) {
                System.out.println(transaction);
            }
        }
    }

    private static void searchByYearToDate() {
        ArrayList<String> yearToDateTransactions = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();

        System.out.println("Your Year-To-Date Transactions: ");

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));

            String line;
            while ((line = bufReader.readLine()) != null) {

                String[] lineSplit = line.split("\\|", 2); // returns [date], [other stuff](not important)

                try {
                    int parsedDate = LocalDate.parse(lineSplit[0]).getYear(); // getting year for each line

                    if (parsedDate == currentYear)
                        yearToDateTransactions.add(line);

                } catch (DateTimeException e) {
                }  //catches and skips line that can't be parsed
            }
        } catch (IOException e) {
            System.out.println("Error Reading File For Search Vendor");
        }

        if (yearToDateTransactions.isEmpty()) {
            System.out.println("No Year-To-Date Activity To Show");
        } else {
            for (String transaction : yearToDateTransactions) {
                System.out.println(transaction);
            }
        }
    }

    private static void searchByPreviousMonth() {

    }

    private static void searchByMonthToDate() {

    }
    //endregion
}
