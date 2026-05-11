package naco.reusablecomponent;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExternalReport {

	private static ExtentReports extent;

    public static ExtentReports getReportObject() {

        if (extent == null) {
            String path = System.getProperty("user.dir") + "\\reports\\index.html";

            ExtentSparkReporter reporter = new ExtentSparkReporter(path);
            reporter.config().setReportName("Automation NACO county result");
            reporter.config().setDocumentTitle("TestResult");
            reporter.config().setTimelineEnabled(true);
          
            reporter.config().setTheme(Theme.STANDARD);
            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester", "Athirai");
        }

        return extent;
    }
}
	
	
	
	

