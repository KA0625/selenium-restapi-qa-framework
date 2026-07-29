
package naco.testcases;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import naco.datadriven.DataBaseReader;
import naco.pageobject.Page1WelcomePage;
import naco.pageobject.Page2CountyExpolorerPage;
import naco.pageobject.Page3CountyInfo;
import naco.reusablecomponent.ConfigReader;
import naco.reusablecomponent.listeners.Listeners;

public class TC_3_1_MidWest_WestNorth_CountyWebsitevalidation {
	public Page1WelcomePage p1;
	//public WebDriver driver;

	 public ConfigReader config;

	 public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
	 
	 public WebDriver getDriver() {
	        return tlDriver.get();
	    }
	 
	public WebDriver initializeDriver(String browserName) throws IOException {
		 WebDriver driver;
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
	
	
	public static ThreadLocal<Page1WelcomePage> tlP1 = new ThreadLocal<>();
	public Page1WelcomePage getP1() {
	    return tlP1.get();
	}
	
	 //  @BeforeTest(alwaysRun = true)
	@BeforeMethod(alwaysRun = true)
	    public void launchapplication() throws IOException {

	        config = new ConfigReader();  

	      WebDriver  driver = initializeDriver(config.getBrowser());
	      tlDriver.set(driver);
	      Page1WelcomePage page = new Page1WelcomePage(driver);
	      tlP1.set(page);

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

	Page2CountyExpolorerPage p2;
	Page3CountyInfo p3;
	
	
	List<Object[]> allcountyRows;
	
	@DataProvider(name = "StateProvider", parallel = true)
	public Object[][] stateProvider() throws SQLException {
//,'MN','MO','NE','ND','SD'
	    String Midwestquery = "SELECT StateCode FROM USA WHERE StateCode IN ('IA','KS')";
	    List<String> states = DataBaseReader.getDatafromDB(Midwestquery);

	    Object[][] dp = new Object[states.size()][1];

	    for (int i = 0; i < states.size(); i++) {
	        dp[i][0] = states.get(i);
	    }

	    return dp;
	}
	@Test(dataProvider = "StateProvider")
	
	public void runState(String stateCode) throws IOException {

		SoftAssert softAssert = new SoftAssert();
		
	    // Go to county explorer
	    p2 = getP1().GoToCountyExplorer();
	    p2.toggleclick();
	    p2.selectstate(stateCode);

	    // Get all counties for the state
	    Map<String, String> fipsMap = p2.getcountyfips(stateCode);

	    // Loop through each county
	    for (Map.Entry<String, String> entry : fipsMap.entrySet()) {

	        String countyName = entry.getKey();
	        String fips = entry.getValue();

	        p3 = new Page3CountyInfo(getDriver());

	        Map<String, String> websiteMap = p3.getCountyInfo(Map.of(countyName, fips));
	        String website = websiteMap.get(countyName);
	        int status = p3.getLinkStatusCode(website);

	        ExtentTest logger = Listeners.test.get();
	        if (logger != null) {
	            logger.info("Validating: " + countyName + " | URL: " + website);
	        }

	        softAssert.assertNotNull(website, "FAIL: Website link is NULL for county: " + countyName);
	        softAssert.assertFalse(website.isEmpty(), "FAIL: Website link is EMPTY for county: " + countyName);

	        try {
	            softAssert.assertEquals(status, 200, "HTTP Status " + status + " for " + website);
	        } catch (Throwable t) {

	            getDriver().get(website);
	            String path = getScreenshot("FAIL_" + countyName, getDriver());

	            if (logger != null) {
	                logger.addScreenCaptureFromPath(path);
	            }

	            throw t;
	        }
	    }
	    softAssert.assertAll();
	}
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		 getDriver().quit();
	        tlDriver.remove();
	}

	/*public List<List<Object[]>> splitIntoBatches(List<Object[]> rows) {

	    int total = rows.size();
	    int batchSize = 10;

	    List<List<Object[]>> batches = new ArrayList<>();

	    int index = 0;
	    while (index < total) {
	        int end = Math.min(index + batchSize, total);
	        batches.add(rows.subList(index, end));
	        index = end;
	    }

	    return batches;
	}
/*	public void runCountyBatches(List<Object[]> countyRows) throws IOException {

	    List<List<Object[]>> batches = splitIntoBatches(countyRows);

	    String originalTab = getDriver().getWindowHandle();
	    List<String> orderedTabs = new ArrayList<>();
	    orderedTabs.add(originalTab);

	    // open tabs and capture correct order
	    for (int i = 0; i < batches.size(); i++) {
	        ((JavascriptExecutor) getDriver()).executeScript("window.open('about:blank','_blank');");

	        Set<String> handles = getDriver().getWindowHandles();
	        handles.removeAll(orderedTabs);

	        String newTab = handles.iterator().next();
	        orderedTabs.add(newTab);
	    }

	    // now switch in correct order
	    for (int i = 0; i < batches.size(); i++) {

	        getDriver().switchTo().window(orderedTabs.get(i));

	        for (Object[] row : batches.get(i)) {

	            String statecode = (String) row[0];
	            String countyName = (String) row[1];
	            String fips = (String) row[2];

	            validateCounty(statecode, countyName, fips);
	        }
	    }
	}*/





}