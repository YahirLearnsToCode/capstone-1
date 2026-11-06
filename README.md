<h1>Accounting Ledger Application</h1>

This ledger application can add a deposit or make a payment and write
it a transactions.csv file. Using this transactions.csv file or a pre-exisiting one,
a user can use pre-existing functions to display either all transactions, deposits,
or payments. This can further be filtered using other pre-existing functions such as,
Month-To-Date, Previous month, Year-To-Date, Previous Year, Search by Vendor, or a custom search
utlizing a start/end date, transaction description, vendor, and amount.

<h2>How To Run</h2>

* Clone the repository into a folder
* Open Intelli J
* File -> Open.. -> Find new cloned porject
* Go to the ApplicationMainMenu.Java and click Run/Play button on the top of your IDE

<h2>Parts I Enjoyed</h2>
I enjoyed creating helper methods that helped reduce redundant code and made many codeblocks more readable.
<i>Below is a bit of code of some helper methods i created</i>

<h3>checks to see if the double passed in through scanner is a legal type and not something else</h3>
```java
public static double validAmountNumber () {
while (true) {
String amount = myScanner.nextLine ().trim ();

            if (amount.isEmpty()) return -1;

            try {
                return Double.parseDouble(amount);
            } catch (NumberFormatException e) {
                System.out.print(" │ Enter A Valid Number: ");
            }
        }
    }
```
<h3 >This Helper Method Color Codes the Transaction Red or Green For Payment or Deposit</h3>
```java
private static String colorCodeTransaction(String line) {
String red = "\u001B[31m"; // ESC characer, 1B -> hex decimal , [ tells its instruction, 31 is color, m ends command
String green = "\u001B[32m";
String reset = "\u001B[0m"; //sets color back to normal for terminal
String[] lineParsed = line.split("\\|");

        try {
            double amount = Double.parseDouble((lineParsed[4]));
            if (amount < 0) {
                return red + line + reset;
            } else {
                return green + line + reset;
            }
        } catch (NumberFormatException e) {
            return line;
        }
    }
```

<h2>Something I've Learned</h2> 
I learned about try-with-resources. A try with resource means that you can something like a buffered reader inside a parenthesis and 
when the block ends, the resource is automatically closed. No need for reader.close(); Helps a lot to error proof and avoid forgetting to close. 

<i>This is another one of my helper methods</i>
```java 
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
```
<h2>Something I'd Do Differently</h2> 
Something i'd definitely do differently in future capstones is creating a transactions class with getters and setters. Rather than my current method which was that everything a task was being performed, it would have to read the file and parse through the array every single time
i think that it would have also made everything so much more shorter and simpler to manage.

<h2>Something I Struggled On</h2> 
Something i struggled on was the challenge portion and creating the filters. I would say that part took me the longest to code just because doing all the ! nots in my head was very difficult for me to interperet. 
Its actually one of the reasons why that part of my code has the most comments, because i had to write everything in plain english step by step for me to know what to do and to remember what is going on.

```java
private static void filterByCustomSearch(LocalDate startDate, LocalDate endDate, String description, String vendor, double amount) {
        System.out.println(" ╭───────────────────────────────────────╮");
        System.out.println(" │          $  Filter Search  $          ╰──────────────────────────────╮");
        System.out.println(" ├──────────────────────────────────────────────────────────────────────┤");

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

                //System.out.println(t); // printing out after all filters are passed
                System.out.printf(" │ %-68s │\n", colorCodeTransaction(t));

            } catch (NumberFormatException | DateTimeException e) {
                // skips header and bad line of data
            }
        }
        System.out.println(" │                                                                      │");
        System.out.println(" ╰──────────────────────────────────────────────────────────────────────╯");
    }
```