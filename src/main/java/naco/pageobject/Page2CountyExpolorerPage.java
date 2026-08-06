package naco.pageobject;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import naco.reusablecomponent.AbstractComponents;

public class Page2CountyExpolorerPage extends AbstractComponents {

	WebDriver driver;

	public Page2CountyExpolorerPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//*[@class='icon search'])[2]")
	WebElement searchtoggle;
	@FindBy(id = "ce-search")
	WebElement searchbox;
	
	@FindBy(id = "ce_nav_var")
	WebElement map;
	@FindBy(id = "ce-search-clear")
	WebElement clearsearch;
	@FindBy(id = "ce-search-results-list")
	List<WebElement> searchlist;
	@FindBy(xpath = "//*[@id='ce-search-results-list']/div/b[text()='IL']")
	List<WebElement> ILcounty;
	@FindBy(xpath = "//*[@id='ce-search-results-list']/div/b[text()='IL']/parent::div")
	List<WebElement> ILCountynames;
	@FindBy(css = ".controllers .close-btn")
	WebElement countyclosebtn;
	@FindBy(css = "#ce-search-results-list")
	WebElement searchresultlist;

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
		map.click();//need to click twice due to DOM refesh issue.
		int ILcountyCount = ILcounty.size();
		return ILcountyCount;
	}

	public void selectstate(String State) {
		searchbox.click();
		searchbox.sendKeys(State);
		searchbox.click();

	}

	public List<WebElement> getStateCounty(String state) {
		String statexpath = "//*[@id='ce-search-results-list']/div/b[text()='" + state + "']";
		List<WebElement> County = driver.findElements(By.xpath(statexpath));
		return County;
	}

	public int countysize(String state, String statename) {
		impwait();
		map.click();
		map.click();
		int StateCountyCount = getStateCounty(state).size();
		clearsearch.click();

		return StateCountyCount;
	}

	public List<String> getStateCountyNames(String state) {
		List<WebElement> names = driver
				.findElements(By.xpath("//*[@id='ce-search-results-list']/div/b[text()='" + state + "']/parent::div"));
		List<String> countyNames = names.stream().map(WebElement::getText).collect(Collectors.toList());
		clearsearch.click();
		
		return countyNames;

	}

	
	public Map<String, String> getcountyfips(String state) {
		
		List<String> countyinfolist = getStateCountyNames(state);
		
		Map<String, String> info=new LinkedHashMap<>();
		for (String county : countyinfolist) {
			System.out.println(county);
			//driver.manage().window().maximize();
			searchbox.click();
			searchbox.sendKeys(county);
			waitforWebElementVisible(searchresultlist);

			Actions actions = new Actions(driver);
			actions.moveToElement(searchresultlist).click().perform();
			waitforWebElementVisible(countyclosebtn);

		String newurl = driver.getCurrentUrl();
		System.out.println(county + "--" + newurl);
			String fips = newurl.split("county_info=")[1];
			info.put(county, fips);
			impwait();
			countyclosebtn.click();

			clearsearch.click();

		}
		return info;
	}

}
