package naco.reusablecomponent.listeners;




import java.util.Arrays;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import naco.reusablecomponent.BaseTest;
import naco.reusablecomponent.ExternalReport;

public class Listeners extends BaseTest implements ITestListener {

    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    ExtentReports extent = ExternalReport.getReportObject();

    @Override
    public void onTestStart(ITestResult result) {


    	
    	
        String testName = result.getMethod().getMethodName();

        Object[] params = result.getParameters();
        if (params != null && params.length > 0) {
            testName += " - " + Arrays.toString(params);
        }

        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        if (test.get() == null) {
            ExtentTest fallback = extent.createTest(result.getMethod().getMethodName() + " (Auto-created)");
            test.set(fallback);
        }

        test.get().log(Status.FAIL, "Test Failed");
        test.get().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (test.get() == null) {
            ExtentTest fallback = extent.createTest(result.getMethod().getMethodName() + " (Skipped)");
            test.set(fallback);
        }
        test.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
