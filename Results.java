import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Results {

	public Scene results(MainMenu mm, ArrayList<ArrayList<Double>> results) {
		
		double fifoMin = results.get(0).get(1);
		double knapsackMin = results.get(1).get(1);
		double fifoMax = results.get(0).get(results.get(0).size() - 1);
		double knapsackMax = results.get(1).get(results.get(1).size() - 1);
		double maxTime = Math.max(fifoMax, knapsackMax); //find the max time it took
		double FIFOAverage = 0;
		double KnapsackAverage = 0;

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		
		ObservableList<String> minutes = FXCollections.observableArrayList();
		for(int i = 1; i < maxTime; i++){
			minutes.add(Integer.toString(i));
		}
		xAxis.setCategories(minutes);
		xAxis.setLabel("Turn Around Time (in minutes)");
		yAxis.setLabel("# of Orders");
		final BarChart<String,Number> histogram = new BarChart<String,Number>(xAxis,yAxis);
		histogram.setStyle("-fx-bar-gap: -2");

		Button back = new Button("Back");
		
		histogram.setTitle("Drone Simulation");
		
		XYChart.Series<String,Number> series = new XYChart.Series();
		series.setName("FIFO");
		

		int curOrder = 0; //keep track of what order you're on
		for (int min = 1; min < maxTime; min++) {
			int numOrders = 0;
			while(curOrder < results.get(0).size()){
				if(results.get(0).get(curOrder) < min){
					FIFOAverage += results.get(0).get(curOrder);
					curOrder++;
					numOrders++;
				}
				else{
					series.getData().add(new XYChart.Data(Integer.toString(min), numOrders));
					break;
				}
			}
		}
		FIFOAverage = FIFOAverage / (double)results.get(0).size();
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Knapsack");
        curOrder = 0; //keep track of what order you're on
		for (int min = 1; min < maxTime; min++) {
			int numOrders = 0;
			while(curOrder < results.get(0).size()){
				if(results.get(1).get(curOrder) < min){
					KnapsackAverage += results.get(1).get(curOrder);
					curOrder++;
					numOrders++;
				}
				else{
					series2.getData().add(new XYChart.Data(Integer.toString(min), numOrders));
					break;
				}
			}
		}
		KnapsackAverage = KnapsackAverage / (double)results.get(1).size();
        
        histogram.getData().addAll(series,series2);
        
		Label FIFOAverageLabel = new Label(String.format("FIFO Average: %.4f", FIFOAverage));
		Label KnapsackAverageLabel = new Label(String.format("Knapsack Average: %.4f", KnapsackAverage));
		Label maxFifoLabel = new Label(String.format("Max FIFO time: %.4f", fifoMax));
		Label maxKnapsackLabel = new Label(String.format("Max Knapsack time: %.4f", knapsackMax));
		Label minFifoLabel = new Label(String.format("Min FIFO time: %.4f", fifoMin));
		Label minKnapsackLabel = new Label(String.format("Min Knapsack time: %.4f", knapsackMin));
        
        FIFOAverageLabel.setStyle("-fx-font-size:20");
        KnapsackAverageLabel.setStyle("-fx-font-size:20");
        maxFifoLabel.setStyle("-fx-font-size:16");
        minFifoLabel.setStyle("-fx-font-size:16");
        maxKnapsackLabel.setStyle("-fx-font-size:16");
        minKnapsackLabel.setStyle("-fx-font-size:16");
        
		back.setStyle("-fx-font-size:16");
		back.setMaxWidth(150);
		back.setOnAction(e -> {
			mm.menu.setScene(mm.menuWindow);
		});
		
		GridPane subRoot = new GridPane();
		subRoot.setPadding(new Insets(10, 10, 10, 10));
		subRoot.setVgap(5);
		subRoot.setHgap(150);
		GridPane.setConstraints(FIFOAverageLabel, 0, 0);
		GridPane.setConstraints(maxFifoLabel, 0, 1);
		GridPane.setConstraints(minFifoLabel, 0, 2);
		GridPane.setConstraints(KnapsackAverageLabel, 1, 0);
		GridPane.setConstraints(maxKnapsackLabel, 1, 1);
		GridPane.setConstraints(minKnapsackLabel, 1, 2);
		GridPane.setConstraints(back, 0, 3);
		
		subRoot.getChildren().addAll(back,FIFOAverageLabel,KnapsackAverageLabel,
				maxFifoLabel,minFifoLabel,maxKnapsackLabel,minKnapsackLabel);
		
		VBox root = new VBox();
		root.setSpacing(10);
		root.getChildren().addAll(histogram,subRoot);
		Scene scene = new Scene(root,1000,750);
		
		return scene;
	}
}