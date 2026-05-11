
package naco.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import naco.pageobject.Page1WelcomePage;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
//import io.qameta.allure.model.*;
public class Hooks {

	public static WebDriver driver;
	
	public static Page1WelcomePage p1;
	public static List<String> stateCodes = new ArrayList<>();
	public static List<Object[]> dynamicData = new ArrayList<>();
	
     @Before
	 
	  public void setup() throws Exception { 
		 
		  driver = initializeDriver();
	   p1 = new Page1WelcomePage(driver); 
	   p1.url();
     }
	    
	 

	public static WebDriver initializeDriver() throws IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\global.properties");
		prop.load(fis);

		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			throw new RuntimeException("Invalid browser name: " + browserName);
		}

		return driver;
	}
	
	public static void takeScreenshot(String name) {
	    if (driver != null) {
	        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	        Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
	    }
	}

	@After
	public void teardown(Scenario scenario) {
	    try {
	        if (scenario.isFailed() && driver != null) {
	            // Allure standard way to attach screenshot on failure
	            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
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

