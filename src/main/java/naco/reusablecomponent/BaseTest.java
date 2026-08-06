package naco.reusablecomponent;

import java.io.File;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import naco.pageobject.Page1WelcomePage;

public class BaseTest {

    public Page1WelcomePage p1;
    public WebDriver driver;
    public ConfigReader config;

    private boolean isRunningInContainer() {
        try {
            // Primary check for Docker
            if (new File("/.dockerenv").exists()) {
                return true;
            }
            // Secondary check: if OS is Linux but not WSL/host, assume container
            String os = System.getProperty("os.name");
            if (os != null && os.toLowerCase().contains("linux")) {
                // This is a heuristic; /.dockerenv is the most reliable check.
                return false;
            }
        } catch (Exception e) {
            // ignore and assume not in container
        }
        return false;
    }

    public WebDriver initializeDriver(String browserName) throws IOException {

        config = new ConfigReader();
        boolean runOnGrid = config.getRunOnGrid();

        // Decide hub URL based on runtime environment (same JVM that creates the driver)
        String hubUrl = "http://localhost:4444/wd/hub";
        boolean inContainer = isRunningInContainer();
        if (inContainer) {
            hubUrl = "http://selenium-hub:4444/wd/hub";
        }
        System.out.println("RunOnGrid=" + runOnGrid + " | RunningInContainer=" + inContainer + " | HubURL=" + hubUrl);

        try {

            if (runOnGrid) {
                // -----------------------------
                // RUNNING ON SELENIUM GRID
                // -----------------------------
                if (browserName.equalsIgnoreCase("chrome")) {
                    ChromeOptions options = new ChromeOptions();
                    options.setAcceptInsecureCerts(true);
                    options.addArguments("--disable-gpu");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");

                    driver = new RemoteWebDriver(new URL(hubUrl), options);

                } else if (browserName.equalsIgnoreCase("firefox")) {
                    FirefoxOptions options = new FirefoxOptions();
                    options.setAcceptInsecureCerts(true);
                    driver = new RemoteWebDriver(new URL(hubUrl), options);

                } else if (browserName.equalsIgnoreCase("edge")) {
                    EdgeOptions options = new EdgeOptions();
                    options.setAcceptInsecureCerts(true);
                    driver = new RemoteWebDriver(new URL(hubUrl), options);
                }

            } else {
                // -----------------------------
                // RUNNING LOCALLY
                // -----------------------------
                if (browserName.equalsIgnoreCase("chrome")) {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.setAcceptInsecureCerts(true);
                    driver = new org.openqa.selenium.chrome.ChromeDriver(options);

                } else if (browserName.equalsIgnoreCase("firefox")) {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new org.openqa.selenium.firefox.FirefoxDriver();

                } else if (browserName.equalsIgnoreCase("edge")) {
                    WebDriverManager.edgedriver().setup();
                    driver = new org.openqa.selenium.edge.EdgeDriver();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize WebDriver: " + e.getMessage(), e);
        }

        driver.manage().window().maximize();
        return driver;
    }

    @BeforeTest(alwaysRun = true)
    public void launchapplication() throws IOException {

        config = new ConfigReader();
        driver = initializeDriver(config.getBrowser());

        p1 = new Page1WelcomePage(driver);
        driver.get(config.getBaseUrl());
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationPath = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
        FileUtils.copyFile(source, new File(destinationPath));
        return destinationPath;
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
