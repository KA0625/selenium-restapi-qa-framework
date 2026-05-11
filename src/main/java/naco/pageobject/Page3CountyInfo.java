package naco.pageobject;

import static io.restassured.RestAssured.given;

import java.util.LinkedHashMap;

import java.util.Map;
import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v147.network.Network;
import org.openqa.selenium.support.PageFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import naco.reusablecomponent.AbstractComponents;

public class Page3CountyInfo extends AbstractComponents {

	WebDriver driver;

	public Page3CountyInfo(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public Map<String, String> getCountyInfo(Map<String, String> countyData) {

		Map<String, String> countywebsite = new LinkedHashMap<>();
		for (Map.Entry<String, String> entry : countyData.entrySet()) {
			String countyName = entry.getKey();
			String fips = entry.getValue();

			System.out.println("Validating: " + countyName + " --- " + fips);

			RestAssured.baseURI = "https://explorer.naco.org";

			String response = given().queryParam("fips", fips).header("accept", "application/json")
					.header("User-Agent", "Mozilla/5.0").when().get("/get/county").then()

					.assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().asString();

			JsonPath js = new JsonPath(response);
			String websitelink = js.get("county.County_Website");
			countywebsite.put(countyName, websitelink);
			System.out.println(websitelink);

		}
		return countywebsite;

	}

	public int getLinkStatusCode(String link) {
		try {
			return RestAssured.given().header("User-Agent", "Mozilla/5.0")
					// .timeout(5000) // Set a timeout to avoid hanging on unresponsive sites
					.when().get(link).then().extract().statusCode();
		} catch (Exception e) {
			return -1;
		}
		
	}
	
	//new selenium 4 CDP method which runs only on chrome.
	public int getBrokenLinkCDP(String link) {
		if (!(driver instanceof ChromeDriver)) {
	        return -1;
	    }

	    ChromeDriver chrome = (ChromeDriver) driver;
	    DevTools devTools = chrome.getDevTools();
	    devTools.createSession();

	    final int[] statusCode = { -1 };

	    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

	    devTools.addListener(Network.responseReceived(), response -> {
	    	  try {
	    	        if (response != null && response.getResponse() != null) {
	    	            int status = response.getResponse().getStatus();
	    	            statusCode[0] = status;
	    	        }
	    	    } catch (Exception e) {
	    	        // ignore CDP fallback issues
	    	    }
	    });
	    driver.get(link);

	    
	    long end = System.currentTimeMillis() + 5000;
	    while (statusCode[0] == -1 && System.currentTimeMillis() < end) {
	        Thread.onSpinWait();
	    }

	    return statusCode[0];
	}
		
	}


