package naco.reusablecomponent;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import naco.pageobject.Page1WelcomePage;

public class BaseTest {

	public Page1WelcomePage p1;
	public WebDriver driver;

	 public ConfigReader config;

	
	 
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
			driver = new ChromeDriver(options);
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();

		} else {
			throw new RuntimeException("Invalid browser name in config.properties: " + browserName);
		}

		return driver;
	}
	

	
	  @BeforeTest(alwaysRun = true)
	
	    public  void launchapplication() throws IOException {

	        config = new ConfigReader();  

	      WebDriver  driver = initializeDriver(config.getBrowser());
	    
	       p1 = new Page1WelcomePage(driver);
	     

	      driver.get(config.getBaseUrl());
	      
	    }
	

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destinationPath = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
		File file = new File(destinationPath);
		FileUtils.copyFile(source, file);
		return destinationPath;
	}

	public String getScreenshotCucumber(String testCaseName, WebDriver driver) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destinationPath = System.getProperty("user.dir") + "\\reportsCucumber\\" + testCaseName + ".png";
		File file = new File(destinationPath);
		FileUtils.copyFile(source, file);
		return destinationPath;
	}



	@AfterTest

	public void teardown() {
		driver.quit();
	}

}