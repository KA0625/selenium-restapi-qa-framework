package naco.testresource;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import naco.reusablecomponent.ExternalReport;


public class Listeners extends BaseTest implements ITestListener {
ExtentTest test;
	
	ExtentReports ext= ExternalReport.extentconfig();
	ThreadLocal <ExtentTest>extenTest =new ThreadLocal<ExtentTest>();
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("New Test Started" +result.getName());
		test =ext.createTest(result.getMethod().getMethodName());
		extenTest.set(test);
	}
	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("onTestSuccess Method" +result.getName());
		test.log(Status.PASS, "Test Passed");
	}
	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("onTestFailure Method" +result.getName());
		test.log(Status.FAIL, "Test Fail");
	extenTest.get().fail(result.getThrowable());
		
		
		try {
			driver=(WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
	
		String filepath=null;
		try {
			filepath = getScreenshot(result.getMethod().getMethodName(), driver);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		extenTest.get().addScreenCaptureFromPath(filepath, result.getMethod().getMethodName());
		
	}	
	
	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("onTestSkipped Method" +result.getName());
		test.log(Status.SKIP, "Test Skip");
	}
	
	@Override
	public void onFinish(ITestContext context) {
		ext.flush();
	}
	
	
}
