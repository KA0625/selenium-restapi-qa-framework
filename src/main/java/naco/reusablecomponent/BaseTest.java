package naco.reusablecomponent;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterTest;

import org.testng.annotations.BeforeTest;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import io.restassured.config.HttpClientConfig;
import io.restassured.RestAssured;

import io.github.bonigarcia.wdm.WebDriverManager;
import naco.pageobject.Page1WelcomePage;

public class BaseTest {

    public Page1WelcomePage p1;
    public WebDriver driver;
    public ConfigReader config;

    public WebDriver initializeDriver(String browserName) throws IOException {

        config = new ConfigReader();
        boolean runOnGrid = config.getRunOnGrid();

        // Resolve Hub URL from Docker environment variable or default to localhost
        String hubUrl = System.getenv("SELENIUM_HUB_URL");
        if (hubUrl == null || hubUrl.isEmpty()) {
            hubUrl = System.getProperty("hubUrl", "http://localhost:4444/wd/hub");
        }

        System.out.println("Initializing Driver | Browser=" + browserName + " | RunOnGrid=" + runOnGrid + " | HubURL=" + hubUrl);

        try {
            if (runOnGrid) {
                // -----------------------------
                // RUNNING ON SELENIUM GRID
                // -----------------------------
                if (browserName.equalsIgnoreCase("chrome")) {
                    ChromeOptions options = new ChromeOptions();
                    options.setAcceptInsecureCerts(true);
                    options.addArguments("--disable-gpu");
                    options.addArguments("--window-size=1920,1080");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--dns-prefetch-disable");
                    options.addArguments("--ignore-certificate-errors");
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
                } else {
                    throw new IllegalArgumentException("Unsupported browser type: " + browserName);
                }

            } else {
                // -----------------------------
                // RUNNING LOCALLY
                // -----------------------------
                if (browserName.equalsIgnoreCase("chrome")) {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.setAcceptInsecureCerts(true);
                    driver = new ChromeDriver(options);

                } else if (browserName.equalsIgnoreCase("firefox")) {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();

                } else if (browserName.equalsIgnoreCase("edge")) {
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                } else {
                    throw new IllegalArgumentException("Unsupported browser type: " + browserName);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize WebDriver: " + e.getMessage(), e);
        }

        driver.manage().window().maximize();
        return driver;
    }
    
    public void disableRestAssuredRetries() {
        RestAssured.config = RestAssured.config()
            .httpClient(HttpClientConfig.httpClientConfig()
                .httpClientFactory(new HttpClientConfig.HttpClientFactory() {
                    @Override
                    public HttpClient createHttpClient() {
                        return HttpClients.custom()
                            .disableAutomaticRetries()   
                            .build();
                    }
                })
            );
    }

    @BeforeTest(alwaysRun = true)
    public void launchapplication() throws IOException {
    	   disableRestAssuredRetries();
        config = new ConfigReader();
        driver = initializeDriver(config.getBrowser());

        p1 = new Page1WelcomePage(driver);
        driver.get(config.getBaseUrl());
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationPath = System.getProperty("user.dir") 
                + File.separator + "screenshots" 
                + File.separator + testCaseName + ".png";
        FileUtils.copyFile(source, new File(destinationPath));
        return destinationPath;
    }

    @AfterTest(alwaysRun = true)
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}