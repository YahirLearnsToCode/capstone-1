package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class LedgerReports {

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

    // Will display only negative entries in the transaction.csv
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
}
