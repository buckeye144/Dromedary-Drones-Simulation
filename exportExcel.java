import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.MarkerStyle;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFScatterChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class exportExcel {
	public void printExcel(ArrayList<ArrayList<Double>> results) {
		//Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		//Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet( " time results ");

		//Create row object
		Row row;
		row = spreadsheet.createRow(0);
		
		//Assigns where in the document the chart will appear
		XSSFDrawing drawing = spreadsheet.createDrawingPatriarch();
		XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 4, 7, 26);
		
		//Creates the Chart and assigns a title
		XSSFChart chart = drawing.createChart(anchor);
		chart.setTitleText("Delivery Times of Orders");
		chart.setTitleOverlay(false);
		
		//Creates the x and y axes and their titles
		XDDFCategoryAxis xAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
		xAxis.setTitle("Delivery Times (min)");
		XDDFValueAxis yAxis = chart.createValueAxis(AxisPosition.LEFT);
		yAxis.setTitle("Order Number");
		
		//Adds the axes to the chart
		XDDFScatterChartData data = (XDDFScatterChartData) chart.createData(ChartTypes.SCATTER, xAxis, yAxis);
		
		//Creates column titles
		for (int i = 0; i < results.size(); i++) {
			
			if (i % 2 == 0) {
				Cell title = row.createCell(i+1);
				title.setCellValue("FIFO " + i + 1);
			}
			
			else {
				Cell title = row.createCell(i+1);
				title.setCellValue("Knapsack " + i);
			}
		}
		
		//Sets order numbers
		for (int i = 0; i < results.get(0).size(); i++) {
			row = spreadsheet.createRow(i+1);
			Cell order = row.createCell(0);
			order.setCellValue(i+1);
		}
		
		XDDFDataSource<String> orderNum = XDDFDataSourcesFactory.fromStringCellRange(spreadsheet, new CellRangeAddress(1,results.get(0).size(),0,0));
		
		//Builds spreadsheet
		for (int i = 0; i < results.get(0).size(); i++) {
			row = spreadsheet.getRow(i+1);
			for (int j = 0; j < results.size(); j++) {
				double time = results.get(j).get(i);
				Cell cell = row.createCell(j+1);
				cell.setCellValue(time);
			}
		}	
		
		//Adds data series to the chart
		for (int i = 0; i < results.size(); i++) {
			XDDFNumericalDataSource<Double> times = XDDFDataSourcesFactory.fromNumericCellRange(spreadsheet, new CellRangeAddress(1, results.get(0).size(), i, i));
			XDDFScatterChartData.Series series = (XDDFScatterChartData.Series) data.addSeries(orderNum, times);
			
			if (i % 2 == 0) {
				series.setTitle("Fifo " + i + 1, null);
			}
			
			else {
				series.setTitle("Knapsack " + i, null);
			}
		}
		
		//Plots the data on the chart
		chart.plot(data);
		
		//Write the workbook in file system
		try {
			FileOutputStream out = new FileOutputStream(new File("results.xlsx"));
			workbook.write(out);
			out.close();
			workbook.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
