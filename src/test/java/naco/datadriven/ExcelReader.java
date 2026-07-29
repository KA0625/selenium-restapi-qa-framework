package naco.datadriven;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.*;

public class ExcelReader {

	//tc5
		public List<String> getDatafromExceltc5()  {

			List<String> states = new ArrayList<>();
			try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\USA_State.xlsx");
					XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
				XSSFSheet sheet = workbook.getSheet("East"); //"NorthEast"
				DataFormatter formatter = new DataFormatter();
				

				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					String stateCode = formatter.formatCellValue(sheet.getRow(i).getCell(1));
					System.out.println("Read state code from Excel: " + stateCode);
					states.add(stateCode);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return states;
		}
		//tc1
		DataFormatter format=new DataFormatter();
		
		   public Object[][] getExcelDatatc1() throws IOException  {
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

