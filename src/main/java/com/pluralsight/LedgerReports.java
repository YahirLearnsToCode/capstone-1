package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class LedgerReports {
    private static Scanner myScanner = new Scanner(System.in);


    //region Ledger Menu Methods
    // Will display all entries in the transaction.csv file in order from newest to oldest
    public static void displayAllEntries() {
        ArrayList<String> allEntries = transactionsFile(); // Stores items in transactions directly

        for (String entry : allEntries) {
            System.out.println(entry);
        }
    }

    // Will display only positive entries in the transaction.csv
    public static void displayDeposits() {
        ArrayList<String> allEntries = new ArrayList<>();

        for (String line : transactionsFile()) {
            String[] negativeCheck = line.split("\\|");

            if (!negativeCheck[4].contains("-")) allEntries.add(line);
        }

        for (String entry : allEntries) {
            System.out.println(entry);
        }

    }

    // Will display only negative entries in the transaction.csv
    public static void displayPayments() {
        ArrayList<String> allEntries = new ArrayList<>();

        for (String line : transactionsFile()) {
            String[] negativeCheck = line.split("\\|");

            if (negativeCheck[4].contains("-")) allEntries.add(line);
        }

        for (String entry : allEntries) {
            System.out.println(entry);
        }
    }

    // Takes in user choice for Reports Screen
    public static void userReportsMenuChoice() {
        System.out.println("Enter Your Choice: ");
        boolean done = false;
        do {
            displayReportsMenu();
            String userChoice = myScanner.nextLine().trim().toUpperCase();

            if ((userChoice.isEmpty())) {
                System.out.println("Please Enter A Valid Choice");
            } else {
                char choiceAsChar = userChoice.charAt(0);
                switch (choiceAsChar) {
                    case '1' -> searchByMonthToDate();
                    case '2' -> searchByPreviousMonth();
                    case '3' -> searchByYearToDate();
                    case '4' -> searchByPreviousYear();
                    case '5' -> searchByVendor();
                    case '6' -> customSearch();
                    case '0' -> done = true;
                    default -> System.out.println("Not A Valid Choice");
                }
            }
        } while (!done);
    }
    //endregion-

    //region Reports Menu Methods
    private static void customSearch() {
        System.out.println("Custom Search");

        LocalDate startDate = readDate("Enter Start Date");
        LocalDate endDate = readDate("Enter End Date");

        String description = readDescriptionOrVendor("Enter A Description");
        String vendor = readDescriptionOrVendor("Enter A Vendor");

        System.out.println("Enter An Amount Or Press Enter To Skip");
        Double amount = validAmountNumber();

        filterByCustomSearch(startDate, endDate, description, vendor, amount);

    }

    // Outputs the lines in the file that pass the filter
    private static void filterByCustomSearch(LocalDate startDate, LocalDate endDate, String description, String vendor, double amount) {
        for (String t : transactionsFile()) {
            String[] lineParsed = t.split("\\|");
            if (lineParsed.length < 5) continue; // skips lines that are missing pipes

            try {
                LocalDate transactionDate = LocalDate.parse(lineParsed[0]);
                double transactionAmount = Double.parseDouble(lineParsed[4]);

                // if start date is null it skips to next if statement (nothing to filter)
                // if start date is not null and transactionDate is before the start date ->
                // -> then throw the line out and loop again. (it does not match the filter)
                if (startDate != null && transactionDate.isBefore(startDate)) continue;

                // if transaction date is after the end date, throw out the line, restart loop
                if (endDate != null && transactionDate.isAfter(endDate)) continue;

                //if the line Description/Vendor from the file doesn't contain or equal to the parameter description/vendor, restart loop
                if (description != null && !lineParsed[2].toLowerCase().contains(description.toLowerCase())) continue;
                if (vendor != null && !lineParsed[3].toLowerCase().contains(vendor.toLowerCase())) continue;

                if (amount != -1 && transactionAmount != amount) continue;

                System.out.println(t); // printing out after all filters are passed

            } catch (NumberFormatException | DateTimeException e) {
                // skips header and bad line of data
            }
        }
    }

    //Returns null if user presses enter to have no description or vendor
    public static String readDescriptionOrVendor(String promptUser) {
        System.out.println(promptUser + " To Search By Or Press Enter To Skip ");
        String userInput = myScanner.nextLine().trim();

        if (userInput.isEmpty())
            return null;

        return userInput;
    }

    // checks to see if the double passed in through scanner is a legal type and not something else
    public static double validAmountNumber() {
        String amount = myScanner.nextLine().trim();

        if (amount.isEmpty()) return -1;

        try {
            return Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            System.out.println("Enter A Valid Number");
        }
        return -1;
    }

    // Takes in a String (Start/End Date) and prompts user to either give a date or skip and returns that value as a local date
    private static LocalDate readDate(String promptUser) {
        while (true) {
            System.out.println(promptUser + " In ISO Format YYYY-MM-DD Or Press Enter to Skip ->");
            String userDate = myScanner.nextLine().trim();

            if (userDate.isEmpty())
                return null;

            try {
                return LocalDate.parse(userDate);

            } catch (DateTimeParseException e) {
                System.out.println("Enter The Date In ISO Format YYYY-MM-DD");
            }
        }
    }

    // Prompts user for a vendor they want to search for and compares each file line for a match
    private static void searchByVendor() {
        ArrayList<String> specificVendor = new ArrayList<>();

        System.out.println("What Vendor Do You Want To Sort By? ");
        String userInputVendor = myScanner.nextLine().trim();

        for (String line : transactionsFile()) {
            String[] lineSplit = line.split("\\|");
            String vendor = lineSplit[3];

            if (vendor.toLowerCase().contains(userInputVendor.toLowerCase()))
                specificVendor.add(line);
        }

        if (specificVendor.isEmpty()) {
            System.out.println("You Have No Transactions With That Vendor Name");
        } else {
            for (String v : specificVendor) {
                System.out.println(v);
            }
        }
    }

    // Displays all transactions from previous year
    private static void searchByPreviousYear() {
        ArrayList<String> previousYearTransactions = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();

        System.out.println("Your Previous Year Transactions: ");

        for (String line : transactionsFile()) {
            String[] lineSplit = line.split("\\|", 2); // returns [YYYY-MM-DD], [other stuff](not important)

            try {
                int parsedDate = LocalDate.parse(lineSplit[0]).getYear(); // getting year for each line

                if (parsedDate == currentYear - 1)
                    previousYearTransactions.add(line);

            } catch (DateTimeException e) {
            }  //catches and skips line that can't be parsed
        }

        if (previousYearTransactions.isEmpty()) {
            System.out.println("No Previous Year Activity To Show");
        } else {
            for (String transaction : previousYearTransactions) {
                System.out.println(transaction);
            }
        }
    }

    // Displays all transactions from current year
    private static void searchByYearToDate() {
        ArrayList<String> yearToDateTransactions = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();

        System.out.println("Your Year-To-Date Transactions: ");

        for (String line : transactionsFile()) {
            String[] lineSplit = line.split("\\|", 2); // returns [YYYY-MM-DD], [other stuff](not important)

            try {
                int parsedDate = LocalDate.parse(lineSplit[0]).getYear(); // getting year for each line

                if (parsedDate == currentYear) yearToDateTransactions.add(line);

            } catch (DateTimeException e) {
            }  //catches and skips line that can't be parsed
        }

        if (yearToDateTransactions.isEmpty()) {
            System.out.println("No Year-To-Date Activity To Show");
        } else {
            for (String transaction : yearToDateTransactions) {
                System.out.println(transaction);
            }
        }
    }

    // Displays all transactions from previous month
    private static void searchByPreviousMonth() {
        ArrayList<String> previousMonthTransactions = new ArrayList<>();
        LocalDate previous = LocalDate.now().minusMonths(1); //Goes from Jan 2026 -> Dec 2025 if needed
        int previousMonth = previous.getMonthValue();
        int previousYear = previous.getYear();

        System.out.println("Your Previous Month Transactions: ");

        for (String line : transactionsFile()) {
            String[] lineSplit = line.split("\\|", 2); // returns [YYYY-MM-DD], [other stuff](not important)

            try {
                int parsedMonth = LocalDate.parse(lineSplit[0]).getMonthValue(); // getting month for each line
                int parsedYear = LocalDate.parse(lineSplit[0]).getYear(); // getting year for each line

                if (parsedMonth == previousMonth && (parsedYear == previousYear))
                    previousMonthTransactions.add(line);

            } catch (DateTimeException e) {
            }  //catches and skips line that can't be parsed
        }

        if (previousMonthTransactions.isEmpty()) {
            System.out.println("No Previous Month Activity To Show");
        } else {
            for (String transaction : previousMonthTransactions) {
                System.out.println(transaction);
            }
        }
    }

    // Displays all transactions from current month
    private static void searchByMonthToDate() {
        ArrayList<String> monthToDateTransactions = new ArrayList<>();
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        System.out.println("Your Month-To-Date Transactions: ");

        for (String line : transactionsFile()) {
            String[] lineSplit = line.split("\\|", 2); // returns [YYYY-MM-DD], [other stuff](not important)

            try {
                int parsedMonth = LocalDate.parse(lineSplit[0]).getMonthValue(); // getting month for each line
                int parsedYear = LocalDate.parse(lineSplit[0]).getYear(); // getting year for each line

                if (parsedMonth == currentMonth && parsedYear == currentYear) monthToDateTransactions.add(line);

            } catch (DateTimeException e) {
            }  //catches and skips line that can't be parsed
        }

        if (monthToDateTransactions.isEmpty()) {
            System.out.println("No Previous Month Activity To Show");
        } else {
            for (String transaction : monthToDateTransactions) {
                System.out.println(transaction);
            }
        }
    }
    //endregion

    // Reads transactionsFile to reduce redundant code.
    private static ArrayList<String> transactionsFile() {
        ArrayList<String> fileRead = new ArrayList<>();

        try (BufferedReader bufReader = new BufferedReader((new FileReader("src/main/resources/transactions.csv")))) {
            String line;
            while ((line = bufReader.readLine()) != null) {
                fileRead.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error Reading File");
        }
        fileRead.sort(Comparator.reverseOrder()); // Sorts the Transaction file before passing it

        return fileRead;
    }

    // displays choices for Reports menu
    private static void displayReportsMenu() {
        String MENU = """
                 ▄████████████████████████████████████████████████▄
                ████████████████████████████████████████████████████
                ███ ╭──────────────────────────────────────────╮ ███
                ███ │                                          │ ███
                ███ │             * REPORTS MENU *             │ ███
                ███ │             Make a Selection             │ ███
                ███ │            ------------------            │ ███
                ███ │  1) Month To Date                        │ ███
                ███ │  2) Previous Month     6) Custom Search  │ ███
                ███ │  3) Year To Date       0) Back           │ ███
                ███ │  4) Previous Year                        │ ███
                ███ │  5) Search By Vendor                     │ ███
                ███ ╰──────────────────────────────────────────╯ ███
                ███  ▒▒▒▒▒▒▒▒▒▒▒▒▒              (o) (o)  [PWR] ● ███
                 ▀████████████████████████████████████████████████▀
                                ▀██████████████████▀
                            ▄▄████████████████████████▄▄
                """;
        System.out.print(MENU);
        System.out.print("Enter Your Choice: ");
    }
}