import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FreshthymeHTMLparser {
    public static void parseHtmlFiles(String folderPath, String outputPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            try (FileWriter fileWriter = new FileWriter(outputPath)) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".html")) {
                        // Parse HTML content
                        String filePath = file.getAbsolutePath();
                        parseHtml(filePath, fileWriter);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void parseHtml(String filePath, FileWriter fileWriter) {
        try {
            // Parse HTML document
            Document document = Jsoup.parse(new File(filePath), "UTF-8");

            // Extract food titles and prices
            Elements titleElements = document.select("div.ProductCardNameWrapper--g2y3vm.gYqZOL");
            Elements priceElements = document.select("div.ProductPrice--w5mr9b.bOJorN, div.ProductPrice--w5mr9b.ihMJGb");

            // Check if the number of titles and prices match
            if (titleElements.size() != priceElements.size()) {
                // Handle the mismatch if needed
                return;
            }

            // Create a list to store the extracted information
            List<Map<String, String>> productList = new ArrayList<>();

            // Regex pattern for additionalInfo
            Pattern additionalInfoPattern = Pattern.compile("([\\d.]+\\s*\\w*)");

            // Regex pattern for extracting price with the $ sign
            Pattern pricePattern = Pattern.compile("\\$([\\d.]+)");

            // Iterate through each title and price
            for (int i = 0; i < titleElements.size(); i++) {
                Map<String, String> productInfo = new HashMap<>();

                String title = titleElements.get(i).text();
                String price = priceElements.get(i).text();

                // Use regex to extract the price with the $ sign
                Matcher priceMatcher = pricePattern.matcher(price);
                price = priceMatcher.find() ? priceMatcher.group(0) : "";

                // Split title based on "-"
                String[] titleParts = title.split(" - ");

                // Choose the part before "-" and after "-"
                String modifiedTitle = titleParts[0];
                String additionalInfo = titleParts.length > 1 ? titleParts[1] : "";

                // Extract relevant data from additionalInfo using regex
                Matcher matcher = additionalInfoPattern.matcher(additionalInfo);
                additionalInfo = matcher.find() ? matcher.group(1) : "";

                // Add the product information to the list
                productInfo.put("Title", modifiedTitle);
                productInfo.put("AdditionalInfo", additionalInfo);
                productInfo.put("Price", price);
                productList.add(productInfo);

                // Write the extracted information to the text file in the desired format
                fileWriter.write(modifiedTitle + "|" + additionalInfo + "|" + price + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
