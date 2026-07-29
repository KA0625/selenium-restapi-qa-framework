package naco.testcases;

import java.sql.SQLException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import naco.datadriven.DataBaseReader;
import naco.pageobject.Page2CountyExpolorerPage;
import naco.reusablecomponent.BaseTest;

public class TC2_DataBaseValidateCountyNames extends BaseTest {
	Page2CountyExpolorerPage p2;

@Test
	public void findcounty() {
		p2 = p1.GoToCountyExplorer();
		p2.toggleclick();
	}
	@Test(dataProvider = "DBdata", dependsOnMethods = "findcounty")
	public void StateCountyValidate(String stateCode, String stateName) {
		
		p2.selectstate(stateCode);
	
		List<String> countyNames = p2.getStateCountyNames(stateCode);
		Assert.assertTrue(countyNames.size() > 0, "No counties found");
		System.out.println("County names for state " + stateName + ": " + countyNames);
	}

	@DataProvider(name = "DBdata")
	public Object[][] getDBData() throws SQLException {

	    String queryCodes = "SELECT StateCode AS StateCode FROM USA";
	    String queryNames = "SELECT StateName AS StateCode FROM USA";

	    List<String> codes = DataBaseReader.getDatafromDB(queryCodes);
	    List<String> names = DataBaseReader.getDatafromDB(queryNames);

	    Object[][] data = new Object[codes.size()][2];
	    for (int i = 0; i < codes.size(); i++) {
	        data[i][0] = codes.get(i);  // StateCode
	        data[i][1] = names.get(i);  // StateName
	    }
	    return data;
	}
}





