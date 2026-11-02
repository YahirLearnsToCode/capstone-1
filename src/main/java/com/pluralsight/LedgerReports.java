package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class LedgerReports {

    public static void main(String[] args) {
        displayAllEntries();
    }

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
}
