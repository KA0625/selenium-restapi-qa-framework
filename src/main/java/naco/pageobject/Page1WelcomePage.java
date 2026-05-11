package naco.pageobject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import naco.reusablecomponent.AbstractComponents;


public class Page1WelcomePage extends AbstractComponents{
	WebDriver driver;
	public Page1WelcomePage(WebDriver driver) {
		super(driver);
	this.driver=driver;
	PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//*[text()='County Economies 2025']")
	WebElement CountyEconomies;
	@FindBy (xpath="//*[text()='Find county data']")
	WebElement CountyData;
	@FindBy(xpath="//*[text()='Advocacy profiles']")
    WebElement profiles;
    @FindBy(xpath="//*[text()='find a county']")
    WebElement County;
    @FindBy(xpath="//*[text()='Keep me updated']")
    WebElement Update;

	

	public void url() throws IOException {
		// TODO Auto-generated method stub
		Properties	 prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\global.properties");	
				
		prop.load(fis);
		 // Jenkins override OR fallback to global.properties
	    String baseUrl = System.getProperty("baseUrl") != null
	            ? System.getProperty("baseUrl")
	            : prop.getProperty("baseUrl");

	    driver.get(baseUrl);
	 

		
		
	}
	
	
	public Page2CountyExpolorerPage WelcomeOptions() {
		waitforwEBelement(County);
		impwait();
		County.click();
		Page2CountyExpolorerPage p2 =new Page2CountyExpolorerPage(driver);
		return p2;
		
		
	}
	
}
