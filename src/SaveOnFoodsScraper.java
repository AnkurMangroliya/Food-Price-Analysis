import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveOnFoodsScraper {

    public void scrapePages(String baseUrl, int totalPages) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ankur\\Downloads\\chromedriver-win64 (1)\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-websocket");

        WebDriver driver = new ChromeDriver(options);

        // Specify the folder path where you want to save the HTML files
        String folderPath = "SaveonfoodsFiles" + File.separator;

        for (int page_number = 1; page_number <= totalPages; ++page_number) {
            String pageUrl = baseUrl + page_number + "&skip=" + ((page_number - 1) * 30);
//            System.out.println(pageUrl);

            // Load the page
            driver.get(pageUrl);

            // Create a unique file name based on the URL and page number
            String fileName = "Saveonfoods_page" + page_number + ".html";
            String filePath = folderPath + fileName;

            // Save the HTML content to a file
            createFile(filePath, driver.getPageSource());
        }

        // Quit the WebDriver
        driver.quit();

        // Call the HTMLParser to parse the HTML files and write to the output text file
        SaveOnFoodsHTMLParser.parseHtmlFiles(folderPath, "Saveonfoods.txt");
    }

    private void createFile(String filePath, String content) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
            System.out.println("File created: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
