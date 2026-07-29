package naco.pageobject;

import static io.restassured.RestAssured.given;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v147.network.Network;
import org.openqa.selenium.devtools.v147.network.model.Response;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebDriverException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import naco.reusablecomponent.AbstractComponents;

public class Page3CountyInfo extends AbstractComponents {

    public WebDriver driver;

    public Page3CountyInfo(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Extract county website links from API
    public Map<String, String> getCountyInfo(Map<String, String> countyData) {
        RestAssured.useRelaxedHTTPSValidation();
        Map<String, String> countywebsite = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : countyData.entrySet()) {

            String countyName = entry.getKey();
            String fips = entry.getValue();

            RestAssured.baseURI = "https://explorer.naco.org";

            String response = given()
                    .relaxedHTTPSValidation()
                    .queryParam("fips", fips)
                    .header("accept", "application/json")
                    .header("User-Agent", "Mozilla/5.0")
                    .when()
                    .get("/get/county")
                    .then()
                    .assertThat().statusCode(200).and().contentType(ContentType.JSON)
                    .extract().asString();

            JsonPath js = new JsonPath(response);
            String websitelink = js.get("county.County_Website");
            countywebsite.put(countyName, websitelink);
        }
        return countywebsite;
    }

    // Simple RestAssured GET status
    public int getLinkStatusCode(String link) {
        try {
            return RestAssured.given().header("User-Agent", "Mozilla/5.0")
                    .when().get(link).then().extract().statusCode();
        } catch (Exception e) {
            return -1;
        }
    }

    // Robust HTTP probe fallback
    public int getHttpStatusFallback(String urlString) {
        HttpURLConnection connection = null;
        try {
            if (urlString == null || urlString.trim().isEmpty()) return -1;
            if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
                urlString = "https://" + urlString;
            }
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setInstanceFollowRedirects(true);

            connection.connect();
            return connection.getResponseCode();
        } catch (java.net.UnknownHostException uhe) {
            return 503;
        } catch (java.net.ConnectException ce) {
            return 503;
        } catch (java.net.SocketTimeoutException ste) {
            return 408;
        } catch (Exception e) {
            return -1;
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    // Classify WebDriverException
    public int classifyWebDriverException(WebDriverException e) {
        String msg = e.getMessage() == null ? "" : e.getMessage().toLowerCase();
        Throwable cause = e.getCause();

        if (msg.contains("err_connection_timed_out") || msg.contains("timeout")) return 408;
        if (msg.contains("err_name_not_resolved") || cause instanceof java.net.UnknownHostException) return 503;
        if (msg.contains("err_connection_refused") || cause instanceof java.net.ConnectException) return 503;
        if (msg.contains("ssl") || cause instanceof javax.net.ssl.SSLHandshakeException) return 525;

        return 520;
    }

    // CDP status capture
    private int tryGetStatusWithCDP(String link) {
        if (!(driver instanceof ChromeDriver)) {
            return -1;
        }

        ChromeDriver chrome = (ChromeDriver) driver;
        DevTools devTools = chrome.getDevTools();
        try {
            devTools.createSession();
        } catch (Exception e) {
            return -1;
        }

        final int[] statusCode = { -1 };

        try {
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

            devTools.addListener(Network.responseReceived(), responseReceived -> {
                try {
                    Response resp = responseReceived.getResponse();
                    if (resp == null) return;
                    String resUrl = resp.getUrl() == null ? "" : resp.getUrl();
                    int st = resp.getStatus();

                    if (resUrl.equalsIgnoreCase(link) || resUrl.startsWith(link)) {
                        if (statusCode[0] == -1) statusCode[0] = st;
                    }
                } catch (Exception ignored) {}
            });
        } catch (Exception e) {
            return -1;
        }

        try {
            driver.get(link);
        } catch (WebDriverException e) {
            try { devTools.close(); } catch (Exception ignored) {}
            throw e;
        }

        long deadline = System.currentTimeMillis() + 8000;
        while (statusCode[0] == -1 && System.currentTimeMillis() < deadline) {
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        }

        try { devTools.close(); } catch (Exception ignored) {}

        return statusCode[0];
    }

    // Main method used by tests
    public int getBrokenLinkCDP(String link) {
        if (link == null || link.trim().isEmpty()) return -1;

        if (!link.startsWith("http://") && !link.startsWith("https://")) {
            link = "https://" + link;
        }

        if (!(driver instanceof ChromeDriver)) {
            return getHttpStatusFallback(link);
        }

        try {
            int cdpStatus = tryGetStatusWithCDP(link);
            if (cdpStatus != -1) {
                return cdpStatus;
            }
        } catch (WebDriverException e) {
            int classified = classifyWebDriverException(e);
            if (classified != 520) {
                return classified;
            } else {
                int probe = getHttpStatusFallback(link);
                if (probe == 200) return 520;
                else if (probe > 0) return probe;
                else return 520;
            }
        } catch (Exception e) {
            // no logging here
        }

        return getHttpStatusFallback(link);
    }

    // Hybrid helper
    public int getStatusHybrid(String url) {
        int status = getBrokenLinkCDP(url);
        if (status == -1) {
            status = getHttpStatusFallback(url);
        }
        return status;
    }
}
