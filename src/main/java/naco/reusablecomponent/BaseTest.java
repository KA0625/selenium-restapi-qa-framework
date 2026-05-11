package naco.reusablecomponent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

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
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import naco.pageobject.Page1WelcomePage;

public class BaseTest {

	public Page1WelcomePage p1;
	public WebDriver driver;

	@Test
	public WebDriver initializeDriver() throws IOException {

		Properties prop = new Properties();

		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\global.properties");

		prop.load(fis);

		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();

			ChromeOptions options = new ChromeOptions();
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

	public List<HashMap<String, String>> getJsonData() throws IOException {

		String jsonfilepath = System.getProperty("user.dir") + "\\src\\test\\resources\\data.json";

		String content = FileUtils.readFileToString(new File(jsonfilepath), StandardCharsets.UTF_8);
		ObjectMapper obm = new ObjectMapper();
		List<HashMap<String, String>> data = obm.readValue(content, new TypeReference<List<HashMap<String, String>>>() {
		});

		return data;
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

	@BeforeTest(alwaysRun = true)

	public Page1WelcomePage launchapplication() throws IOException {

		driver = initializeDriver();
		p1 = new Page1WelcomePage(driver);
		p1.url();
		return p1;
	}

	@AfterTest
	public void teardown() {
		driver.quit();
	}

}
