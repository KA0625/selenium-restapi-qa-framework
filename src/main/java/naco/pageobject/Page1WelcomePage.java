package naco.pageobject;



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

	

	
	public Page2CountyExpolorerPage GoToCountyExplorer() {
		
		waitforWebElementVisible(County);
		waitForClickable(County);

		
		County.click();
		Page2CountyExpolorerPage p2 =new Page2CountyExpolorerPage(driver);
		return p2;
		
		
	}
	
}
