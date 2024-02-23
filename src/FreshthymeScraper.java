
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FreshthymeScraper {

    public void scrape(String baseUrl, int totalPages) {
        // Specify the folder path where you want to save the HTML files
        String folderPath = "freshDirectFiles" + File.separator;

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ankur\\Downloads\\chromedriver-win64 (1)\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-websocket");

        WebDriver driver = new ChromeDriver(options);

        for (int page_number = 1; page_number <= totalPages; ++page_number) {
            String pageUrl = baseUrl + page_number + "&skip=" + ((page_number - 1) * 48);
//            System.out.println(pageUrl);

            // Load the page
            driver.get(pageUrl);

            // Instead of extracting, save the HTML content to a file
            String fileName = "FreshDirect_page" + page_number + ".html";
            createFile(folderPath + fileName, driver.getPageSource());
        }

        // Quit the WebDriver
        driver.quit();

        // You can call the HTMLParser here if needed
         FreshthymeHTMLparser.parseHtmlFiles(folderPath, "Freshthyme.txt");
    }

    public void createFile(String filePath, String content) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
            System.out.println("File created: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
