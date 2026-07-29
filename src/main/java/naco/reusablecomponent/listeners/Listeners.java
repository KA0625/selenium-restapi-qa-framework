

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

        // Ensure an ExtentTest exists for this thread
        if (test.get() == null) {
            ExtentTest fallback = extent.createTest(result.getMethod().getMethodName() + " (Auto-created)");
            test.set(fallback);
        }

        // Original throwable from TestNG
        Throwable original = result.getThrowable();

        // 1) Classify into a friendly label
        String friendlyLabel = friendlyFailureLabel(original);

        // 2) Log friendly label as the main failure message
        test.get().log(Status.FAIL, "**" + friendlyLabel + "**");

        // 3) Attach original exception details as a warning/info (keeps stacktrace for debugging)
        if (original != null) {
          

            // Convert stacktrace to string and log it (so it's searchable in the report)
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement el : original.getStackTrace()) {
                sb.append(el.toString()).append("\n");
            }
            test.get().warning("Original stacktrace:\n" + sb.toString());
        }

        // 4) Optionally assign a category so you can filter by failure type in Extent UI
        try {
            test.get().assignCategory(friendlyLabel);
        } catch (Exception ignore) {
            // assignCategory may not be available in older Extent versions; ignore if not supported
        }

        // 5) Report the failure to Extent using a new Throwable that contains only the friendly label.
        // This prevents Extent from showing the original exception class as the failure type.
        test.get().fail(new Exception(friendlyLabel));
    }
    private String friendlyFailureLabel(Throwable t) {
        if (t == null) return "Failure";

        String raw = t.getMessage() == null ? "" : t.getMessage().toLowerCase();

        // Chrome net:: errors (map to Website Down)
        if (raw.contains("net::err_connection_timed_out") ||
            raw.contains("err_connection_timed_out") ||
            raw.contains("err_name_not_resolved") ||
            raw.contains("err_connection_refused") ||
            raw.contains("err_connection_closed") ||
            raw.contains("err_connection_reset") ||
            raw.contains("unknownhostexception") ||
            raw.contains("no route to host")) {
            return "Website Down / Unreachable";
        }

        // Timeouts
        if (raw.contains("timeout") || raw.contains("timed out") || raw.contains("timeoutexception")) {
            return "Timeout";
        }

        // SSL / certificate
        if (raw.contains("ssl") || raw.contains("certificate") || raw.contains("handshake")) {
            return "SSL / Certificate Error";
        }

        // Assertion failures from tests
        if (t instanceof AssertionError || raw.contains("assert")) {
            return "Assertion Failure";
        }

        // Default fallback
        return "Test Failure";
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

