/*package naco.testcases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import naco.pageobject.Page2CountyExpolorerPage;

import naco.reusablecomponent.BaseTest;

public class TC2_DataBaseValidateCounty extends BaseTest {
	Page2CountyExpolorerPage p2;

@Test
	public void findcounty() {
		p2 = p1.WelcomeOptions();
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
	public Object[][] getDatafromDB() throws SQLException {

		String host = "localhost";
		String port = "3306";
		String databaseName = "naco";
		String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
		//jdbc:mysql://localhost:3306/naco

		Connection con = DriverManager.getConnection(url, "root", "Selenium2025");
		Statement s = con.createStatement();
		String query = "SELECT StateCode, StateName FROM USA";
		ResultSet rs = s.executeQuery(query);
		List<Object[]> data = new ArrayList<>();
		while (rs.next()) {
			data.add(new Object[] { rs.getString("StateCode"), rs.getString("StateName") });
		}
		con.close();
		return data.toArray(new Object[0][]);
	}
}
*/