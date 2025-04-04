package stepDefinitionFiles;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void setUp() {
        // Set up ChromeOptions to run in Incognito mode
        //ChromeOptions options = new ChromeOptions();
        //options.addArguments("--incognito");  

        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://patinformed.wipo.int/");  
    }

	/*
	 * @After public void tearDown() { if (driver != null) { driver.quit(); // Close
	 * the browser after the test } }
	 */
}
