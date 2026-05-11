package naco.testcases;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import naco.pageobject.Page2CountyExpolorerPage;
import naco.pageobject.Page3CountyInfo;
import naco.reusablecomponent.BaseTest;
import naco.reusablecomponent.listeners.Listeners;
public class TC_4_Master_Parameterized_CountyWebsiteValidation extends BaseTest {
	
		Page2CountyExpolorerPage p2;
		Page3CountyInfo p3;
		
		
		@Parameters("stateList")
		public List<String> getDatafromDB(String stateList) throws SQLException {

			
			String url = "jdbc:mysql://localhost:3306/naco";
			Connection con = DriverManager.getConnection(url, "root", "Selenium2025");
			Statement s = con.createStatement();
			
			String query = "SELECT StateCode FROM USA WHERE StateCode IN ("+ stateList +")";
			ResultSet rs = s.executeQuery(query);
			
			List<String> states = new ArrayList<>();
		    while (rs.next()) {
		        states.add(rs.getString("StateCode"));
		    }
		    con.close();
		    return states;
		}
		
		@DataProvider(name = "NorthEastCountyFipsData")
		public Object[][] countyFipsProvider(ITestContext context) throws SQLException {
			String stateList = context.getCurrentXmlTest().getParameter("stateList");
			p2 = p1.WelcomeOptions();
			p2.toggleclick(); 
			 List<String> states = getDatafromDB(stateList);
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

		@Test(dataProvider = "NorthEastCountyFipsData")
		public void validateCounty(String statecode, String countyName, String fips) throws IOException {
		    
		    p3 = new Page3CountyInfo(driver);
		    Map<String, String> websiteMap = p3.getCountyInfo(Map.of(countyName, fips));
		    String website = websiteMap.get(countyName);
		    int status = p3.getBrokenLinkCDP(website);
		  
		    Listeners.test.get().info("Validating: " + countyName + " | URL: " + website);
		    Assert.assertNotNull(website, "FAIL: Website link is NULL for county: " + countyName);
		    Assert.assertFalse(website.isEmpty(), "FAIL: Website link is EMPTY for county: " + countyName);
		      
		    try {
		        Assert.assertEquals(status, 200, "HTTP Status " + status + " for " + website);
		        } 
		    catch (Throwable t) {
		        
		        driver.get(website);
		        String path = getScreenshot("FAIL_" + countyName, driver);
		        Listeners.test.get().addScreenCaptureFromPath(path, "Failure Screenshot: " + t.getMessage());
		        
		        throw t; 
		    }
		   
		    }
		   
		}




