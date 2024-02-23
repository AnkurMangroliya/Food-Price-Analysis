
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class meijerHTMLparser {
    public static void parseHtmlFile(String filePath, String outputPath) {
        try {
            // Parse HTML document
            Document document = Jsoup.parse(new File(filePath), "UTF-8");

            // Extract food titles
            Elements titleElements = document.select("h2.product-tile--line-clamp-text");
            Elements priceElements = document.select("div.product-tile__price");

            // Check if the number of titles and prices match
            if (titleElements.size() != priceElements.size()) {
                System.out.println("Error: Number of titles and prices do not match!");
                return;
            }

            // Write the extracted information to the text file
            try (FileWriter fileWriter = new FileWriter(outputPath)) {
                for (int i = 0; i < titleElements.size(); i++) {
                    String title = titleElements.get(i).text().replace(",", "|");
                    Elements currentPriceElements = priceElements.get(i).select("span");
                    String price = extractPrice(currentPriceElements);

                    fileWriter.write(title + "|" + price + "\n");

//                    System.out.println("Title: " + title);
//                    System.out.println("Price: " + price);
//                    System.out.println("-----");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String extractPrice(Elements priceElements) {
        // Extract price from the span elements within div.product-tile__price
        String pricePattern = "\\$[\\d.]+";
        return priceElements.stream()
                .map(element -> element.text().trim())
                .filter(price -> price.matches(pricePattern))
                .findFirst()
                .orElse("Price not found");
    }
}
