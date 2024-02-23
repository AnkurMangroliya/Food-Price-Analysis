import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class FoodPriceAnalysis {
    private List<Product> products = new ArrayList<>();

    public void readDataFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                Product product = parseProduct(line);
                if (product != null) {
                    products.add(product);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Product parseProduct(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, "|");
        if (tokenizer.countTokens() >= 3) {
            String title = tokenizer.nextToken().trim();
            String weight = tokenizer.nextToken().trim();
            String price = tokenizer.nextToken().trim();
            return new Product(title, weight, price);
        }
        return null;
    }

    public List<Product> getTopNLowestPrices(String fileName, String searchTerm, int topN) {
        // Filter products based on the search term
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredProducts.add(product);
            }
        }

        // Use a PriorityQueue to sort products based on price (ascending order)
        PriorityQueue<Product> priceQueue = new PriorityQueue<>((p1, p2) ->
                Double.compare(parsePrice(p1.getPrice()), parsePrice(p2.getPrice())));

        // Add filtered products to the PriorityQueue
        priceQueue.addAll(filteredProducts);

        // Retrieve the top N products with the lowest prices
        List<Product> topNProducts = new ArrayList<>();
        for (int i = 0; i < Math.min(topN, priceQueue.size()); i++) {
            topNProducts.add(priceQueue.poll());
        }

        // Print the results including the filename
        processAndPrintResults(fileName, topNProducts);

        return topNProducts;
    }

    public void processAndPrintResults(String fileName, List<Product> matchedProducts) {
        System.out.println("----------------------------------------" + fileName + "----------------------------------------");

        if (matchedProducts.isEmpty()) {
            System.out.println("No matching products found.");
        } else {
            for (Product product : matchedProducts) {
                System.out.println("Title: " + product.getTitle() +
                                ", Weight: " + product.getWeight() +
                                ", Price: " + product.getPrice());
            }
        }
    }

    private double parsePrice(String price) {
        if ("Price not found".equalsIgnoreCase(price.trim())) {
            // Special value for "Price not found"
            return Double.NaN;
        }
        try {
            // Remove the "$" character and parse the price as a double
            return Double.parseDouble(price.replace("$", ""));
        } catch (NumberFormatException e) {
            // Handle the case where the price is not a valid number
            return Double.NaN;
        }
    }

    // Inner class representing a product
    static class Product {
        private String title;
        private String weight;
        private String price;

        public Product(String title, String weight, String price) {
            this.title = title;
            this.weight = weight;
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public String getWeight() {
            return weight;
        }

        public String getPrice() {
            return price;
        }

        // Add this method if it doesn't exist in your original code
        public String getSource() {
            return "";  // Replace this with the actual source if available
        }
    }

}

