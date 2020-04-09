import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class exportExcel {
	public void printExcel(ArrayList<ArrayList<Double>> results) {
		//Create blank workbook
		HSSFWorkbook workbook = new HSSFWorkbook();

		//Create a blank sheet
		HSSFSheet spreadsheet = workbook.createSheet( " time results ");

		//Create row object
		HSSFRow row;
		row = spreadsheet.createRow(0);
		for (int i = 0; i < results.size(); i++) {
			if (i % 2 == 0) {
				Cell title = row.createCell(i);
				title.setCellValue("FIFO");
			}
			
			else {
				Cell title = row.createCell(i);
				title.setCellValue("Knapsack");
			}
		}
			
		/**
		row = spreadsheet.createRow(0);
		Cell title = row.createCell(0);
		title.setCellValue("FIFO");
		
		title = row.createCell(1);
		title.setCellValue("Knapsack");
		**/
		
		for (int i = 0; i < results.get(0).size(); i++) {
			row = spreadsheet.createRow(i+1);
			for (int j = 0; j < results.size(); j++) {
				double time = results.get(j).get(i);
				Cell cell = row.createCell(j);
				cell.setCellValue(time);
			}
		}	
			/**
			row = spreadsheet.createRow(j+1);
			double timeFIFO = results.get(0).get(j);
				//double timeKnapsack = results.get(1).get(i);
			
			Cell cell = row.createCell(0);
			cell.setCellValue(timeFIFO);
			
				//cell = row.createCell(1);
				//cell.setCellValue(timeKnapsack);
			
			/**
			for (Object obj : objectArr){
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String)obj);
			}
			
		}
		
		/**
		for (int j = 0; j < results.get(1).size(); j++) {
			row = spreadsheet.createRow(j+1);
			//double timeFIFO = results.get(0).get(j);
			double timeKnapsack = results.get(1).get(j);
			
			//Cell cell = row.createCell(0);
			//cell.setCellValue(timeFIFO);
			
			Cell cell = row.createCell(1);
			cell.setCellValue(timeKnapsack);
			
			/**
			for (Object obj : objectArr){
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String)obj);
			}
			
		}
		**/
		//Write the workbook in file system
		try {
			// System.out.println("Creating output stream");
			FileOutputStream out = new FileOutputStream(new File("results.xls"));
			// System.out.println("Writing workbook");
			workbook.write(out);
			// System.out.println("Workbook written");
			out.close();
			// System.out.println("results.xls written successfully");
			workbook.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
