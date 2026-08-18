package naco.testcases;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import naco.datadriven.DataBaseReader;
import naco.pageobject.Page2CountyExpolorerPage;
import naco.pageobject.Page3CountyInfo;
import naco.reusablecomponent.BaseTest;
import naco.reusablecomponent.listeners.Listeners;

public class TC10_West_DB_CountyValidation extends BaseTest { 
	Page2CountyExpolorerPage p2;
	Page3CountyInfo p3;

	

	@DataProvider(name = "MidWestCountyFipsData", parallel = true)
	public Object[][] countyFipsProvider() throws SQLException {

		p2 = p1.GoToCountyExplorer();
		p2.toggleclick();
	
		 String EastNorthQuery = "SELECT StateCode FROM USA WHERE StateCode IN ('MI','NC')";
		    List<String> states = DataBaseReader.getDatafromDB(EastNorthQuery);
		List<Object[]> allCountyRows = new ArrayList<>();
		for (String stateCode : states) {
			p2.selectstate(stateCode);

			Map<String, String> fipsMap = p2.getcountyfips(stateCode);

			for (Map.Entry<String, String> entry : fipsMap.entrySet()) {
				allCountyRows.add(new Object[] { stateCode, entry.getKey(), // county name
						entry.getValue() // fips
				});

			}
		}
		return allCountyRows.toArray(new Object[0][]);

	}

	@Test(dataProvider = "MidWestCountyFipsData")
	public void validateCounty(String statecode, String countyName, String fips) throws IOException {

		p3 = new Page3CountyInfo(driver);
		Map<String, String> websiteMap = p3.getCountyInfo(Map.of(countyName, fips));
		String website = websiteMap.get(countyName);
		//int status = p3.getLinkStatusCode(website);
		int status=   p3.getStatusHybrid(website);
		Listeners.test.get().info("Validating: " + countyName + " | URL: " + website);
		Assert.assertNotNull(website, "FAIL: Website link is NULL for county: " + countyName);
		Assert.assertFalse(website.isEmpty(), "FAIL: Website link is EMPTY for county: " + countyName);

		try {
			Assert.assertEquals(status, 200, "HTTP Status " + status + " for " + website);
		} catch (Throwable t) {

			driver.get(website);
			String path = getScreenshot("FAIL_" + countyName, driver);
			Listeners.test.get().addScreenCaptureFromPath(path, "Failure Screenshot: " + t.getMessage());
			// Custom classification
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
