package naco.hooks;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import naco.pageobject.Page1WelcomePage;
import naco.reusablecomponent.ConfigReader;

public class Hooks {

    public WebDriver driver;
    public ConfigReader config;
    public Page1WelcomePage p1;

    // Scenario-level data (no static contamination)
    public List<String> stateCodes;
    public static List<Object[]> dynamicData;

    @Before
    public void launchApplication() throws IOException {

        // Reset scenario data
        stateCodes = new ArrayList<>();
        dynamicData = new ArrayList<>();

        config = new ConfigReader();
        driver = initializeDriver(config.getBrowser());

        p1 = new Page1WelcomePage(driver);

        driver.get(config.getBaseUrl());
    }

    public WebDriver initializeDriver(String browserName) throws IOException {

        if (browserName.equalsIgnoreCase("chrome")) {

            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.setAcceptInsecureCerts(true);
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--allow-insecure-localhost");
            options.setExperimentalOption("excludeSwitches",
                    java.util.Arrays.asList("enable-logging", "enable-automation"));
            options.setExperimentalOption("useAutomationExtension", false);

            return new ChromeDriver(options);

        } else if (browserName.equalsIgnoreCase("firefox")) {

            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver();

        } else if (browserName.equalsIgnoreCase("edge")) {

            WebDriverManager.edgedriver().setup();
            return new EdgeDriver();

        } else {
            throw new RuntimeException("Invalid browser name in config.properties: " + browserName);
        }
    }

    public void takeScreenshot(String name) {
        if (driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        }
    }


    @After
    public void teardown(Scenario scenario) {

        try {
            if (scenario.isFailed() && driver != null) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failed_Screenshot_" + scenario.getName());
            }
        } catch (Exception e) {
            System.err.println("Could not take screenshot: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
