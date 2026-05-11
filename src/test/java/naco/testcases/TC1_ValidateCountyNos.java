package naco.testcases;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import naco.pageobject.Page2CountyExpolorerPage;
import naco.reusablecomponent.BaseTest;

import naco.reusablecomponent.*;
public class TC1_ValidateCountyNos extends BaseTest{

	Page2CountyExpolorerPage p2;
	
	@Test
	public void findcounty() {
		p2 = p1.WelcomeOptions();
		p2.toggleclick();
	}
	
	@Test(priority =1, dependsOnMethods="findcounty" )
	public void ILcountValidate() throws IOException {
		
		 
		p2.ILselectstate();
	System.out.println(p2.ILcountysize());	
		Assert.assertEquals(p2.ILcountysize(), 102);
		
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
		    	System.out.println("the state " + statename +" have "+ Count +" counties");
		    }
			
			
			
	}
	
	@DataProvider(name="jsondata")
	public Object[][] getData() throws IOException {
		
		List <HashMap<String,String>> data=	getJsonData();
		System.out.println("JSON DATA: " + data);
		
	    Object[][] dp = new Object[data.size()][1];

	    for (int i = 0; i < data.size(); i++) {
	        dp[i][0] = data.get(i);

	    }
		return dp;
       }
	
	DataFormatter format=new DataFormatter();
   @DataProvider(name="excel")
   public Object[][] getExcelData() throws IOException  {
	   FileInputStream file = new FileInputStream("C:\\Users\\athir\\eclipse-workspace\\AthiraiExecution.NACO\\USA_State.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file); 
		XSSFSheet sheet1 = workbook.getSheetAt(0); 
		
		int rowsCountS1=sheet1.getPhysicalNumberOfRows();
		System.out.println("rowscounts1"+rowsCountS1);
		XSSFRow row1=sheet1.getRow(0);
		int columnCountS1=2;
		Object[][] data = new Object[rowsCountS1-1][columnCountS1];
		
		for(int i=0;i<rowsCountS1-1;i++) {
			row1=sheet1.getRow(i+1);
			for(int j=0;j<columnCountS1;j++) {
				XSSFCell c= row1.getCell(j);
				 data[i][j] = format.formatCellValue(c);
			}
		}
		workbook.close();
		file.close();
		return data;
   }

}

