package naco.testcases;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import naco.datadriven.DataBaseReader;
import naco.pageobject.Page2CountyExpolorerPage;
import naco.pageobject.Page3CountyInfo;
import naco.reusablecomponent.BaseTest;
import naco.reusablecomponent.listeners.Listeners;
public class TC_4_Master_Parameterized_CountyWebsiteValidation extends BaseTest {
	
		Page2CountyExpolorerPage p2;
		Page3CountyInfo p3;
		//have to trial two data providers
		

		
		@DataProvider(name = "NorthEastCountyFipsData" , parallel = true)
		public Object[][] countyFipsProvider(ITestContext context ) throws SQLException {
			String stateList = context.getCurrentXmlTest().getParameter("stateList");
			p2 = p1.GoToCountyExplorer();
			p2.toggleclick(); 
			//String stateList = "'DE','MD','VA'";
			List<String> states = DataBaseReader.getDatafromDB(
			    "SELECT StateCode FROM USA WHERE StateCode IN (" + stateList + ")");
			
			    List<Object[]> allCountyRows = new ArrayList<>();
			    for (String stateCode : states) {
			p2.selectstate(stateCode);

			Map<String, String> fipsMap = p2.getcountyfips(stateCode);


			
			for (Map.Entry<String, String> entry : fipsMap.entrySet()) {
				allCountyRows.add(new Object[]{
		                stateCode,
		                entry.getKey(),     // county name
		                entry.getValue()    // fips
		            });
				
			}
			    }
			return allCountyRows.toArray(new Object[0][]);
			    
		}
		  
		@Parameters("stateList")
		@Test(dataProvider = "NorthEastCountyFipsData")
		public void validateCounty(String statecode, String countyName, String fips) throws IOException {
		    
		    p3 = new Page3CountyInfo(driver);
		    Map<String, String> websiteMap = p3.getCountyInfo(Map.of(countyName, fips));
		    String website = websiteMap.get(countyName);
		    int status = p3.getStatusHybrid(website);

		  
		   // int status = p3.getBrokenLinkCDP(website);
		  
		    Listeners.test.get().info("Validating: " + countyName + " | URL: " + website);
		    Assert.assertNotNull(website, "FAIL: Website link is NULL for county: " + countyName);
		    Assert.assertFalse(website.isEmpty(), "FAIL: Website link is EMPTY for county: " + countyName);
		      
		    try {
		    	

		            if (status == -1) {
		                Assert.fail("NETWORK FAILURE (-1): Unable to reach " + website + " for county: " + countyName);
		            }

		            if (status >= 400 && status <= 499) {
		                Assert.fail("CLIENT ERROR (" + status + "): " + website + " for county: " + countyName);
		            }

		            if (status >= 500 && status <= 599) {
		                Assert.fail("SERVER ERROR (" + status + "): " + website + " for county: " + countyName);
		            }

		         
		            Listeners.test.get().info("Status OK (" + status + ") for " + website);
		        } 
		    catch (Throwable t) {

		        // Handle WebDriverException classification and logging
		        if (t instanceof org.openqa.selenium.WebDriverException) {
		            int classified = p3.classifyWebDriverException((org.openqa.selenium.WebDriverException) t);  

		            // Fail the test with your custom message
		            throw new AssertionError("Browser Error (" + classified + "): " + website);
		        }

		        // Existing screenshot logic
		        driver.get(website);
		        String path = getScreenshot("FAIL_" + countyName, driver);
		        Listeners.test.get().addScreenCaptureFromPath(path, "Failure Screenshot: " + t.getMessage());

		        // Your existing status-based failure logic
		        if (status == 503) {
		            throw new AssertionError("Website Down: " + website);
		        } else if (status == 408) {
		            throw new AssertionError("Timeout loading: " + website);
		        } else if (status == -1) {
		            throw new AssertionError("No Response / Unknown Error for: " + website);
		        } else {
		            throw new AssertionError("Unexpected HTTP Status " + status + " for: " + website);
		        }
		    }

		    }
		   
		}




