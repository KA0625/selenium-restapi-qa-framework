package naco.pageobject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import naco.reusablecomponent.AbstractComponents;

public class Page2CountyExpolorerPage extends AbstractComponents{

	WebDriver driver;
	public Page2CountyExpolorerPage(WebDriver driver) {
	super(driver);	
	this.driver=driver;
	PageFactory.initElements(driver, this);
	}
	
	
	@FindBy (xpath="//*[@class='icon search_toggle']")
	WebElement searchtoggle;
	@FindBy(id="ce-search")
	WebElement searchbox;
	@FindBy (xpath="//*[clas='icon menu']")
	WebElement expandbar;
	@FindBy(id="ce_nav_var")
	WebElement map;
	@FindBy (id="ce-search-clear")
	WebElement clearsearch;
	@FindBy(id="ce-search-results-list")
	List<WebElement> searchlist;
	@FindBy (xpath="//*[@id='ce-search-results-list']/div/b[text()='IL']")
	List<WebElement> ILcounty;
	@FindBy(xpath="//*[@id='ce-search-results-list']/div/b[text()='IL']/parent::div")	
	List<WebElement> ILCountynames;
	
	public void toggleclick() {
		impwait();
		searchtoggle.click();
	}
	
	public void ILselectstate() {
		
		searchbox.sendKeys("IL");
		searchbox.click();
	}
	
	public int ILcountysize() {
		impwait();
		map.click();
		map.click();
		int ILcountyCount =ILcounty.size();
		return ILcountyCount;
	}
	
	public void selectstate(String State) {
		
		
	
		searchbox.click();
		searchbox.sendKeys(State);
		searchbox.click();
		
		//clearsearch.click();
	}
	
	public List<WebElement> getStateCounty(String state) {
		String statexpath ="//*[@id='ce-search-results-list']/div/b[text()='"+state+"']";
		List<WebElement> County =driver.findElements(By.xpath(statexpath));
		return County;
	}
	public int countysize(String state,String statename) {
		impwait();
		map.click();
		map.click();
		int StateCountyCount =getStateCounty(state).size();
		clearsearch.click();
		//System.out.println("the state " +statename+" have "+StateCountyCount+" counties");
		return StateCountyCount;
	}

	
	
}
