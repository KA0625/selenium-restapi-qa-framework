package naco.StepDefinitionLatest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.Map;

import org.junit.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import naco.hooks.Hooks;
//import naco.pageobject.Page1WelcomePage;
import naco.pageobject.Page2CountyExpolorerPage;
import naco.pageobject.Page3CountyInfo;

public class StepDefinitionCucumber {

    private final Hooks hooks;   // injected by Cucumber via Picocontainer
    private Page2CountyExpolorerPage p2;
    private Page3CountyInfo p3;
    //private Page1WelcomePage p1;

    // Picocontainer will inject the same Hooks instance used in @Before/@After
    public StepDefinitionCucumber(Hooks hooks) {
        this.hooks = hooks;
    }

    @Given("User fetches state codes from database")
    public void fetch_state_codes() throws Exception {
        String url = "jdbc:mysql://localhost:3306/naco";
        Connection con = DriverManager.getConnection(url, "root", "Selenium2025");
        Statement s = con.createStatement();

        ResultSet rs = s.executeQuery("SELECT StateCode FROM USA");

        while (rs.next()) {
            hooks.stateCodes.add(rs.getString("StateCode"));
        }
        con.close();
    }

    @And("User navigates to County Explorer page")
    public void navigate_to_page() throws IOException {
        p2 = hooks.p1.GoToCountyExplorer();
        p2.toggleclick();
    }

    @And("User retrieves county and fips for state {string}")
    public void retrieve_counties(String Code) {
        p2.selectstate(Code);
        Map<String, String> fipsMap = p2.getcountyfips(Code);

        for (Map.Entry<String, String> entry : fipsMap.entrySet()) {
            Hooks.dynamicData.add(new Object[]{
                    Code,
                    entry.getKey(),
                    entry.getValue()
            });
        }
    }

    @When("User validates website for county {string} with fips {string}")
    public void validate_website(String countyName, String fips) throws Exception {
        p3 = new Page3CountyInfo(hooks.driver);
        Map<String, String> countyData = Map.of(countyName, fips);
        Map<String, String> websiteMap = p3.getCountyInfo(countyData);
        String website = websiteMap.get(countyName);

        if (website == null || website.isEmpty()) {
            hooks.takeScreenshot("NULL_URL_" + countyName);
            throw new AssertionError("CRITICAL FAILURE: Website URL is NULL or empty for " + countyName);
        }

        try {
            hooks.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
            hooks.driver.get(website);
        } catch (Throwable t) {
            hooks.takeScreenshot("NAV_ERROR_" + countyName);
            Assert.fail("FAILED: Navigation error for " + countyName + " | " + t.getMessage());
        }

        int status = -1;
        try {
            status = RestAssured.given()
                    .relaxedHTTPSValidation()
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .when().head(website)
                    .then().extract().statusCode();
        } catch (Throwable t) {
            hooks.takeScreenshot("REQUEST_CRASH_" + countyName);
            Assert.fail("FAILED: RestAssured could not connect to " + website + " | " + t.getMessage());
        }

        System.out.println("County: " + countyName + " | Status: " + status);

        if (status != 200) {
            hooks.takeScreenshot("STATUS_FAIL_" + status + "_" + countyName);
            Assert.assertEquals("FAILED: Expected 200 but got " + status + " for " + countyName, 200, status);
        }
    }
}
