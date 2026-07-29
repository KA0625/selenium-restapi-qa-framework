package naco.testcases;


import java.io.IOException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import naco.datadriven.JsonReader;
import naco.pageobject.Page2CountyExpolorerPage;
import naco.reusablecomponent.BaseTest;
import naco.reusablecomponent.RetryFailedTest;
public class TC1_ValidateCountyNos extends BaseTest{

	Page2CountyExpolorerPage p2;
	
	@Test
	public void findcounty() {
		p2 = p1.GoToCountyExplorer();
		p2.toggleclick();
	}
	
	@Test(priority =1, dependsOnMethods="findcounty" )
	public void ILcountValidate() throws IOException {
		
		 
		p2.ILselectstate();
	System.out.println(p2.ILcountysize());	
		Assert.assertEquals(p2.ILcountysize(), 102);
		
	}
/*	@DataProvider(name="excel")
	public Object[][] getExcelData() throws IOException {
	    ExcelReader reader = new ExcelReader();
	    return reader.getExcelDatatc1();   // call method from ExcelReader
	}

	@Test( dependsOnMethods="findcounty",dataProvider="excel")
		public void StateCountyValidateExcel(String statename,String state)
		{
		
			
			p2.selectstate(state);	
			int Count = p2.countysize(state,statename);

		    if (Count == 0) {
		        Assert.fail("County count returned 0 for state: " + state);
		    }
		    else
		    {
		    	System.out.println(state+ "the state " + statename +" have "+ Count +" counties");
		    }
			
			
			
	}*/
	@DataProvider(name="jsondata")
	public Object[][] getjsonData() throws IOException {
	    JsonReader reader = new JsonReader();
	    return reader.getDatatc1();   // call method from ExcelReader
	}
	
	
	@Test( dependsOnMethods="findcounty",dataProvider="jsondata",retryAnalyzer=RetryFailedTest.class)
	public void StateCountyValidate(HashMap<String,String> input)
	{
	
		
		p2.selectstate(input.get("state"));
		
		int Count = p2.countysize(input.get("state"), input.get("statename"));

	    if (Count == 0) {
	        Assert.fail("County count returned 0 for state: " + input.get("state"));
	    }
	    else
	    {
	    	System.out.println("the state " +input.get("statename")+" have "+ Count +" counties");
	    }
	}
  

}


