import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CombinedTextData {

    private List<Product> Pp_products = new ArrayList<>();

    public void readDataFromFiles(String[] fi_fileNames) {
        for (String f_fileName : fi_fileNames) {
            try {
                readDataFromFile(f_fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void combineAndWriteToFile(String combinedFileName) {
        // Write the combined data to the new file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(combinedFileName))) {
            for (Product product : Pp_products) {
                String line = product.getTitle() + "| " + product.getWeight() + "| " + product.getPrice() + "| " + product.getFileName();
                writer.write(line);
                writer.newLine();
            }
//            System.out.println("Combined data written to: " + combinedFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDataFromFile(String fffileName) throws IOException {
        try (BufferedReader p_br = new BufferedReader(new FileReader(fffileName))) {
            String ll_line;
            while ((ll_line = p_br.readLine()) != null) {
                Product product = parseProduct(ll_line, fffileName);
                if (product != null) {
                    Pp_products.add(product);
                }
            }
        }
    }

    private Product parseProduct(String l_lline, String f___fileName) {
        String[] t__tokens = l_lline.split("\\|");
        if (t__tokens.length >= 3) {
            String title = t__tokens[0].trim();
            String weight = t__tokens[1].trim();
            String price = t__tokens[2].trim();
            return new Product(title, weight, price, f___fileName);
        }
        return null;
    }

    // Inner class representing a product
    static class Product {
        private String title;
        private String weight;
        private String price;
        private String fileName;

        public Product(String title, String weight, String price, String fileName) {
            this.title = title;
            this.weight = weight;
            this.price = price;
            this.fileName = fileName;
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

        public String getFileName() {
            return fileName;
        }
    }
}
