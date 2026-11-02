package com.pluralsight;

public class ApplicationMainMenu {
    public static void main(String[] args) {
        displayMenu();
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
                X) Exit """;
        System.out.println(menu);
    }
}
