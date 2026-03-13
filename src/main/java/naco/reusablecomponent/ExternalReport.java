package naco.reusablecomponent;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExternalReport {

	
	public static ExtentReports extentconfig() {
		String path = System.getProperty("user.dir")+"\\reports\\index.html";
		//java ->C:\Users\athir\eclipse-workspace\automation\src\test\java\rahulshetty\testresouce-->user.dir
	ExtentSparkReporter reporter= new ExtentSparkReporter(path);
	reporter.config().setReportName("Automation NACO county result");
	reporter.config().setDocumentTitle("TestResult");
	ExtentReports extent= new ExtentReports();
	extent.attachReporter(reporter);
	extent.setSystemInfo("Tester", "Athirai");
	return extent;
	}
	
	
	
	
	
}
