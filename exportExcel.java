import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.BarDirection;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class exportExcel {
	public void printExcel(ArrayList<ArrayList<Double>> results) {
		double fifoMax = results.get(0).get(results.get(0).size() - 1);
		double knapsackMax = results.get(1).get(results.get(1).size() - 1);
		double maxTime = Math.max(fifoMax, knapsackMax); //find the max time it took
		double FIFOAverage = 0;
		double KnapsackAverage = 0;
		
		//Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		//Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet( " time results ");

		//Create row object
		Row row;
		row = spreadsheet.createRow(0);
		
		//Creates column titles
		Cell title = row.createCell(1);
		title.setCellValue("FIFO");
		title = row.createCell(2);
		title.setCellValue("Knapsack");
		
		//Builds spreadsheet
		for (int i = 0; i < results.get(0).size(); i++) {
			row = spreadsheet.getRow(i+1);
			for (int j = 0; j < results.size(); j++) {
				double time = results.get(j).get(i);
				//System.out.println(time);
				Cell cell = row.createCell(j+1);
				cell.setCellValue(time);
			}
		}
		
		for (int i = 0; i < results.get(0).size(); i++) {
			row = spreadsheet.createRow(i+1);
			Cell order = row.createCell(0);
			order.setCellValue(i+1);
		}
		
		XSSFDrawing drawing = spreadsheet.createDrawingPatriarch();
		XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 4, 7, 26);
		
		XSSFChart chart = drawing.createChart(anchor);
		chart.setTitleText("Drone Simulation");
		chart.setTitleOverlay(false);
		
		//Creates the legend
		XDDFChartLegend legend = chart.getOrAddLegend();
		legend.setPosition(LegendPosition.TOP_RIGHT);
		
		//Sets axis titles
		XDDFCategoryAxis xAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
		xAxis.setTitle("Turn Around Time (in minutes)");
		XDDFValueAxis yAxis = chart.createValueAxis(AxisPosition.LEFT);
		yAxis.setTitle("# of Orders");
		
		//Create data series for FIFO orders
		int curOrder = 0; //keep track of what order you're on
		ArrayList<Integer> FIFOorders = new ArrayList<>();
		ArrayList<String> timeRange = new ArrayList<>();
		for (Integer min = 1; min < maxTime; min++) {
			int numOrders = 0;
			while(curOrder < results.get(0).size()){
				if(results.get(0).get(curOrder) < min){
					FIFOAverage += results.get(0).get(curOrder);
					curOrder++;
					numOrders++;
				}
				else{
					FIFOorders.add(numOrders);
					timeRange.add(min.toString());
					break;
				}
			}
		}
		
		Integer[] FIFOarray = FIFOorders.toArray(new Integer[FIFOorders.size()]);
		String[] timeArray = timeRange.toArray(new String[timeRange.size()]);
		XDDFNumericalDataSource<Integer> FIFOnumOrders = XDDFDataSourcesFactory.fromArray(FIFOarray);
		XDDFDataSource<String> times = XDDFDataSourcesFactory.fromArray(timeArray);
		FIFOAverage = FIFOAverage / (double)results.get(0).size();
		
		//Create data series for Knapsack orders
        curOrder = 0; //keep track of what order you're on
        ArrayList<Integer> KnapsackOrders = new ArrayList<>();
		for (int min = 1; min < maxTime; min++) {
			int numOrders = 0;
			while(curOrder < results.get(0).size()){
				if(results.get(1).get(curOrder) < min){
					KnapsackAverage += results.get(1).get(curOrder);
					curOrder++;
					numOrders++;
				}
				else{
					KnapsackOrders.add(numOrders);
					break;
				}
			}
		}
		Integer[] KnapsackArray = KnapsackOrders.toArray(new Integer[KnapsackOrders.size()]);
		XDDFNumericalDataSource<Integer> KnapsackNumOrders = XDDFDataSourcesFactory.fromArray(KnapsackArray);
		KnapsackAverage = KnapsackAverage / (double)results.get(1).size();
		
		//Create data series and chart them
		XDDFChartData data = chart.createData(ChartTypes.BAR, xAxis, yAxis);
		XDDFChartData.Series FIFOseries = data.addSeries(times, FIFOnumOrders);
		XDDFChartData.Series KnapsackSeries = data.addSeries(times, KnapsackNumOrders);
		FIFOseries.setTitle("FIFO", null);
		KnapsackSeries.setTitle("Knapsack", null);
		data.setVaryColors(true);
		chart.plot(data);
		
		//Turn chart into a bar chart
		XDDFBarChartData bar = (XDDFBarChartData) data;
		bar.setBarDirection(BarDirection.COL);
		
		//Writes the average and worst times to a spreadsheet
		XSSFSheet averageWorst = workbook.createSheet(" average and worst times ");
		Row fifo = averageWorst.createRow(0);
		Row knapsack = averageWorst.createRow(1);
		
		//Calculate the averages for FIFO and Knapsack
		Cell cell = fifo.createCell(0);
		cell.setCellValue("FIFO Average: ");
		cell = fifo.createCell(1);
		cell.setCellValue(FIFOAverage);
		
		cell = knapsack.createCell(0);
		cell.setCellValue("Knapsack Average: ");
		cell = knapsack.createCell(1);
		cell.setCellValue(KnapsackAverage);
		
		//Write the workbook in file system
		try {
			// System.out.println("Creating output stream");
			FileOutputStream out = new FileOutputStream(new File("results.xlsx"));
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
