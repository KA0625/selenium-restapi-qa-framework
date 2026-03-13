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
	public AbstractComponents(WebDriver driver)
	{
		this.driver=driver;
	}
	public void waitforelement(By visiblefindby) {
		 wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated( visiblefindby));
		
	}
	
	public void waitforwEBelement(WebElement visiblefindby) {
		 wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf( visiblefindby));
		
	}
	
	public void waitforelementin(By invisiblefindby) {
	
	wait.until(ExpectedConditions.invisibilityOf(driver.findElement(invisiblefindby)));
	}
	public void impwait() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}
	
	public boolean IsElementVisible(WebElement visible) {
		try {
			 wait = new WebDriverWait(driver, Duration.ofSeconds(30));
				wait.until(ExpectedConditions.visibilityOf(visible));
				return true;
		}
		catch(Exception e) {
			return false;
		}
		
	}
	
}
