import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MeijerScraper {

    private final String url;
    private final int numberOfClicks;

    public MeijerScraper(String url, int numberOfClicks) {
        this.url = url;
        this.numberOfClicks = numberOfClicks;
    }

    public void scrape() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ankur\\Downloads\\chromedriver-win64 (1)\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get(url);

        // Click the "Load More" button the specified number of times
        for (int i = 0; i < numberOfClicks; i++) {
            // Locate the "Load More" button using XPath
            List<WebElement> loadMoreButton = driver.findElements(By.xpath("//*[@id='main-content']/div[2]/div[1]/div[3]/div[2]/div[2]/div[2]/div[2]/button"));

            // If the button is found, click it
            if (!loadMoreButton.isEmpty()) {
                loadMoreButton.get(0).click();
            } else {
                System.out.println("Load More button not found.");
                break;
            }
        }

        // Save the HTML content to a file inside the "meijerFiles" folder
        String fileName = "meijerFiles/meijer_page.html";
        String filePath = createFile(fileName, driver.getPageSource());

        // Call the HTMLParser to parse the HTML file and write to the output text file
        meijerHTMLparser.parseHtmlFile(filePath, "meijer.txt");

        driver.quit();
    }

    private String createFile(String fileName, String content) {
        String filePath = fileName;

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
            System.out.println("File created: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }
}


