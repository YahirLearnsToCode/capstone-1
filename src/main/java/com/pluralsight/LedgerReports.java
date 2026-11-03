package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

            String input;
            while ((input = bufReader.readLine()) != null) {
                allEntries.add(input);
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

            String input;
            while ((input = bufReader.readLine()) != null) {

                String[] negativeCheck = input.split("\\|");

                if (!negativeCheck[4].contains("-"))
                    allEntries.add(input);
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

            String input;
            while ((input = bufReader.readLine()) != null) {

                String[] negativeCheck = input.split("\\|");

                if (negativeCheck[4].contains("-"))
                    allEntries.add(input);
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

    private static void searchByVendor() {
    }

    private static void searchByPreviousYear() {
    }

    private static void searchByYearToDate() {
        
    }

    private static void searchByPreviousMonth() {
        
    }

    private static void searchByMonthToDate() {
        
    }

}
