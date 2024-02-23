import java.io.File;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class MainScraper {
    public static void main(String[] args) throws Exception {
        Scanner inp = new Scanner(System.in);

        Hashtable<String, String> url_Map = new Hashtable<String, String>();

        System.out.println("Welcome to Price matcher \n");

        // Create a list of categories
        List<String> categories = Arrays.asList("fruits", "dairy", "vegetables", "snacks", "frozen",
                "pantry", "breakfast", "candy", "bread", "coffee", "fresh", "beverages");

        // Display category options
        System.out.println("List of categories that are available...");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }

        // Get user input for category number
//        System.out.println("Select a category by entering the corresponding number:");
        int categoryNumber = readCategoryNumberInput(inp, categories.size());
        String category = categories.get(categoryNumber - 1);

//        System.out.print("Enter the total number of pages for Scraping : ");
        int totalpages = readIntegerInputoftotalpages(inp);

        removeFiles("meijerFiles");
        removeFiles("freshDirectFiles");
        removeFiles("SaveonfoodsFiles");

        // Call Meijer Scraper
        MeijerScraper meijerScraper = new MeijerScraper("https://www.meijer.com/shopping/search.html?text=" + category + "&sort_order=relevance-descending", totalpages);
        meijerScraper.scrape();

        // Call Freshthyme Scraper
        FreshthymeScraper freshthymeScraper = new FreshthymeScraper();
        freshthymeScraper.scrape("https://ww2.freshthyme.com/sm/planning/rsid/104/results?q=" + category + "&page=", totalpages);

        // Call SaveOnFoods Scraper
        SaveOnFoodsScraper saveOnFoodsScraper = new SaveOnFoodsScraper();
        saveOnFoodsScraper.scrapePages("https://www.saveonfoods.com/sm/pickup/rsid/1982/results?q=" + category + "&page=", totalpages);

//        FoodPriceAnalysis analyzer = new FoodPriceAnalysis(); // declare analyzer outside the loop

        boolean isValidInputValidation = getYesNoInput("\nValidate data", inp);

        if (isValidInputValidation) {
            SimpleDataValidation.validateData();
            System.out.println("Validation done..");
        } else {
            System.out.println("Validation skipped...");
        }




        boolean repeat = true;
        while (repeat) {
            String product = getProductNameInput(inp);
            String p_product = product;

            // Spell Checking of product
            boolean spellCheck = SpellChecking.SpellChecker(product);

            if (!spellCheck) {
                System.out.println("\nPlease check the spelling");
                // Using word completion to give suggestions of words
                List<String> suggestedWords = WordCompletion.findSimilarWords(product);
                System.out.println("\nSome suggested words are:");
                for (String word : suggestedWords) {
                    System.out.println(word);
                }
                continue;
            }

//            System.out.println("Enter the number of lowest prices you want : ");
            int topN = readIntegerInput(inp);

            System.out.println("\nList of products: ");

            String[] fileNames = {"meijer.txt", "Freshthyme.txt", "Saveonfoods.txt"};

            for (String fileName : fileNames) {
                FoodPriceAnalysis analyzer = new FoodPriceAnalysis();  // Create a new instance inside the loop
                analyzer.readDataFromFile(fileName);
                List<FoodPriceAnalysis.Product> top3LowestPrices = analyzer.getTopNLowestPrices(fileName, product, topN);
            }

            CombinedTextData c_CombinedTextData = new CombinedTextData();
            c_CombinedTextData.readDataFromFiles(fileNames);
            c_CombinedTextData.combineAndWriteToFile("combinedData.txt");

            // Analyze data from all files combined
            System.out.println("\n\n\nAnalyzed data from all files");
            CombinedFoodpriceAnalysis combinedFoodPriceAnalysis = new CombinedFoodpriceAnalysis();
            combinedFoodPriceAnalysis.readDataFromFile("combinedData.txt");
            combinedFoodPriceAnalysis.getTopNLowestPrices(product, topN);


            System.out.println("\nSearch Frequency:");

//            InvertedIndexing.Indexing(url_Map, product);

            SearchFrequency.SearchCount(product);


            boolean isValidInputFrequency = getYesNoInput("\nFrequency count", inp);
            if (isValidInputFrequency) {
                int overallFrequency = FrequencyCount.Frequency(product);
                System.out.println("Overall frequency of " + product + " is: " + overallFrequency);
            }

            boolean isValidInputPageRank = getYesNoInput("\nPage rank", inp);
            if (isValidInputPageRank) {
                PageRanking.pageRank(product);
            }

            boolean isValidInputAnotherProduct = getYesNoInput("\nEnter another product", inp);
            if (!isValidInputAnotherProduct) {
                System.out.println("Quitting the program. Goodbye!");
                return; // Exit the program
            }

        }
        inp.close();
    }

    private static String getCategoryInput(Scanner inp) {
        String category;
        do {
            System.out.print("Enter the category of products to want to scrape (single word only): ");
            category = inp.nextLine().toLowerCase().trim();
        } while (!category.matches("^[a-zA-Z]+$")); // Check if it's a single word with only letters
        return category;
    }

    private static int readCategoryNumberInput(Scanner scanner, int maxNumber) {
        while (true) {
            try {
                System.out.print("\nSelect a category by entering the corresponding number (1-" + maxNumber + "): ");
                int value = Integer.parseInt(scanner.nextLine());

                if (value >= 1 && value <= maxNumber) {
                    return value;
                } else {
                    System.out.println("Invalid number. Please enter a valid number between 1 and " + maxNumber + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }


    private static int readIntegerInputoftotalpages(Scanner scanner) {
        while (true) {
            try {
                System.out.print("\nEnter the total number of pages for Scraping : ");
                String input = scanner.nextLine();
                int value = Integer.parseInt(input);

                if (value != 0) {
                    return value;
                } else {
                    System.out.println("Invalid input. Please enter a non-zero integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static int readIntegerInput(Scanner scanner) {
        while (true) {
            try {
                System.out.print("\nEnter the number of lowest prices you want in each website : ");
                String input = scanner.nextLine();
                int value = Integer.parseInt(input);

                if (value != 0) {
                    return value;
                } else {
                    System.out.println("Invalid input. Please enter a non-zero integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }


    private static String getProductNameInput(Scanner inp) {
        String product;
        do {
            try {
                System.out.print("\nEnter the product name (single word only): ");
                product = inp.nextLine().toLowerCase().trim();

                if (product.length() < 3) {
                    throw new IllegalArgumentException("Product name must have at least three characters.");
                }

                if (product.contains(" ")) {
                    throw new IllegalArgumentException("Product name must be a single word.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                product = ""; // Reset the product to trigger the loop
            }
        } while (product.isEmpty() || !product.matches("^[a-zA-Z]+$"));

        return product;
    }


    private static void removeFiles(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
//                    System.out.println("File deleted: " + file.getAbsolutePath());
                }
            }
        }
    }

    private static boolean getYesNoInput(String prompt, Scanner scanner) {
        boolean isValidInput = false;
        do {
            System.out.println(prompt + ": ");
            System.out.print("Do you want this functionality to perform ? (Y/N): ");
            String input = scanner.nextLine().toUpperCase();

            try {
                if (!input.matches("[YN]")) {
                    throw new IllegalArgumentException("Invalid input. Please enter 'Y' or 'N'.\n");
                }

                isValidInput = true; // exit the loop if the input is valid
                return "Y".equals(input); // return the boolean value directly
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!isValidInput);

        return false; // this line should not be reached
    }

}
