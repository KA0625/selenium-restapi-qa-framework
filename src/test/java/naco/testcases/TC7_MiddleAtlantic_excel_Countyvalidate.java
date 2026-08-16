package naco.testcases;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import naco.datadriven.ExcelReader;
import naco.pageobject.Page2CountyExpolorerPage;
import naco.pageobject.Page3CountyInfo;
import naco.reusablecomponent.BaseTest;
import naco.reusablecomponent.listeners.Listeners;
public class TC7_MiddleAtlantic_excel_Countyvalidate extends BaseTest {
	


	
				Page2CountyExpolorerPage p2;
				Page3CountyInfo p3;
				
				@DataProvider(name = "NorthEastCountyFipsData", parallel = true)
				public Object[][] countyFipsProvider(ITestContext context)  {
					
					p2 = p1.GoToCountyExplorer();
					p2.toggleclick(); 
					ExcelReader reader = new ExcelReader();
					List<String> states = reader.getDatafromExceltc7();
					
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
				  //  int status = p3.getBrokenLinkCDP(website);
				 int status=   p3.getStatusHybrid(website);
				  
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
