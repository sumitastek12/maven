package mavenforjenkins;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class UITest {

    WebDriver driver = null; // Declare driver at class level

    @Parameters("Browser")
    @Test
    public void startBrowser(String browserName) {
        System.out.println("Parameter value is: " + browserName);

        if (browserName.equalsIgnoreCase("chrome")) { // Fix case sensitivity
            WebDriverManager.chromedriver().setup();
            // REMOVE THIS LINE: WebDriver driver = new ChromeDriver(); 
            // This is creating a local variable that shadows the class variable
            ChromeOptions opt = new ChromeOptions();
            opt.addArguments("--headless"); // Run tests without UI (for Jenkins)
            opt.addArguments("--no-sandbox");
            opt.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver(opt); // This will now assign to class variable
        }
        else if (browserName.equalsIgnoreCase("edge")) { // Fix case sensitivity
            System.setProperty("webdriver.edge.driver", "C:\\WebDriver\\msedgedriver.exe");
            driver = new EdgeDriver();
        }
        else {
            throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        // Ensure driver is initialized before calling driver methods
        if (driver != null) {
            driver.manage().window().maximize();
            driver.get("https://opensource-demo.orangehrmlive.com/");
            Assert.assertTrue(driver.getTitle().contains("Orange"), "Title does not match");
            driver.quit();
        } else {
            System.out.println("Driver is null. Browser initialization failed.");
        }
    }
}