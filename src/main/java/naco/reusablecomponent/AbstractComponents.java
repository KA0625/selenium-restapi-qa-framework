package naco.reusablecomponent;

import java.time.Duration;
//import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
//import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractComponents {
	WebDriver driver;
	WebDriverWait wait;

	public AbstractComponents(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	public void waitForElementVisible(By visiblefindby) {
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(visiblefindby));

	}

	public void waitforWebElementVisible(WebElement visiblefindby) {
	
		wait.until(ExpectedConditions.visibilityOf(visiblefindby));

	}

	   public void waitForElementInvisible(By locator) {
	        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	        
	    }
	   public void waitForClickable(WebElement element) {
	        wait.until(ExpectedConditions.elementToBeClickable(element));
	    }
	   public void clickWhenReady(WebElement element) {
	        waitForClickable(element);
	        element.click();
	    }
	

	public void impwait() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}

	

	public boolean IsElementVisible(WebElement visible) {
		try {
			
			wait.until(ExpectedConditions.visibilityOf(visible));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

}
