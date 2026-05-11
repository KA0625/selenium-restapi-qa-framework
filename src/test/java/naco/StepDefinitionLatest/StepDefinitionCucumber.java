package naco.StepDefinitionLatest;


import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import naco.hooks.Hooks;
import naco.pageobject.Page1WelcomePage;
import naco.pageobject.Page2CountyExpolorerPage;
import naco.pageobject.Page3CountyInfo;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.*;

import featuregenrator.naco.DynamicFeatureGenerator;

import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.util.*;
public class StepDefinitionCucumber {


		    Page2CountyExpolorerPage p2;
		    Page3CountyInfo p3;  
		    public static Page1WelcomePage p1;
		    

		    @Given("User fetches state codes from database")
		    public void fetch_state_codes() throws Exception {
		   
		  //  	Hooks.stateCodes.clear(); enable comment out line for feature generator.
			//    Hooks.dynamicData.clear();
		    	
		        String url = "jdbc:mysql://localhost:3306/naco";
		        Connection con = DriverManager.getConnection(url, "root", "Selenium2025");
		        Statement s = con.createStatement();

		        ResultSet rs = s.executeQuery("SELECT StateCode FROM USA");

		        while (rs.next()) {
		        	Hooks.stateCodes.add(rs.getString("StateCode"));
		        }
		        con.close();
		    }

		    @And("User navigates to County Explorer page")
		    public void navigate_to_page() throws IOException {
		        p2 = Hooks.p1.WelcomeOptions();  
		        p2.toggleclick();
		    }

		  @And("User retrieves county and fips for state {string}")
		 //  @And("User retrieves county and fips for all states") 
		    public void retrieve_counties(String Code) {
		    	
		    	
		//   for(String Code:Hooks.stateCodes) {
		        p2.selectstate(Code);
		        Map<String, String> fipsMap = p2.getcountyfips(Code);

		        for (Map.Entry<String, String> entry : fipsMap.entrySet()) {
		            Hooks.dynamicData.add(new Object[]{
		                    Code,
		                    entry.getKey(),
		                    entry.getValue()}
		                    );
		           
		       }
		    	 
		    }
		   //}
		
		   
		
		
		   @When("User validates website for county {string} with fips {string}")
		    public void validate_website(String countyName, String fips) throws Exception {

			   p3 = new Page3CountyInfo(Hooks.driver);
			    Map<String, String> countyData = Map.of(countyName, fips);
			    Map<String, String> websiteMap = p3.getCountyInfo(countyData);
			    String website = websiteMap.get(countyName);

			    // 1. Check for Null/Empty
			    if (website == null || website.isEmpty()) {
			        Hooks.takeScreenshot("NULL_URL_" + countyName);
			        throw new AssertionError("CRITICAL FAILURE: Website URL is NULL or empty for " + countyName);
			    }
			    

			    // 2. Navigation with Timeout Protection
			    try {
			      
			        Hooks.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
			        Hooks.driver.get(website);
			        Hooks.driver.navigate().to(website);
			        
			    } catch (Throwable t) {
			        // If it times out or connection fails, we take a screenshot and FAIL
			        Hooks.takeScreenshot("NAV_ERROR_" + countyName);
			        Assert.fail("FAILED: Navigation error for " + countyName + " | " + t.getMessage());
			    }

			    // 3. Status Code Check via RestAssured
			    int status = -1;
			    try {
			        status = RestAssured.given()
			                .relaxedHTTPSValidation()
			                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
			                .when().get(website)
			                .then().extract().statusCode();
			    } catch (Throwable t) {
			        Hooks.takeScreenshot("REQUEST_CRASH_" + countyName);
			        Assert.fail("FAILED: RestAssured could not connect to " + website + " | " + t.getMessage());
			    }

			    System.out.println("County: " + countyName + " | Status: " + status);

			    // 4. Final Hard Assertion for 403s/404s/etc.
			    if (status != 200) {
			        Hooks.takeScreenshot("STATUS_FAIL_" + status + "_" + countyName);
			 
			        Assert.assertEquals("FAILED: Expected 200 but got " + status + " for " + countyName, 200, status);

			        
			    }
			}
}

	


