import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class exportExcel {
	public void printExcel(ArrayList<Double> results) {
		//Create blank workbook
		HSSFWorkbook workbook = new HSSFWorkbook();

		//Create a blank sheet
		HSSFSheet spreadsheet = workbook.createSheet( " time results ");

		//Create row object
		HSSFRow row;

		for (int i = 0; i < results.size(); i++) {
			row = spreadsheet.createRow(i);
			double time = results.get(i);
			
			Cell cell = row.createCell(0);
			cell.setCellValue(time);
			
			/**
			for (Object obj : objectArr){
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String)obj);
			}
			**/
		}
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
