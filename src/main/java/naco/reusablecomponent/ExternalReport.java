package naco.reusablecomponent;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExternalReport {

    private static ExtentReports extent;

    public static ExtentReports getReportObject() {

        if (extent == null) {

            // Read report name from environment variable first
            String reportName = System.getenv("REPORT_NAME");
            if (reportName == null || reportName.isEmpty()) {
                reportName = System.getProperty("reportName", "DefaultReport");
            }

            String path = System.getProperty("user.dir") + "/test-output/" + reportName + ".html";

            ExtentSparkReporter reporter = new ExtentSparkReporter(path);
            reporter.config().setReportName("Automation NACO county result");
            reporter.config().setDocumentTitle("TestResult");
            reporter.config().setTimelineEnabled(true);
            reporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(reporter);

            // Read browser from environment variable first
            String browser = System.getenv("BROWSER");
            if (browser == null || browser.isEmpty()) {
                browser = System.getProperty("browser", "unknown");
            }

            extent.setSystemInfo("Tester", "Athirai");
            extent.setSystemInfo("Browser", browser);
        }

        return extent;
    }
}

	
	
//  String path = System.getProperty("user.dir")+"/Extentreports/index.html";
	

