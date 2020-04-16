import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Results {

	public Scene results(MainMenu mm, ArrayList<ArrayList<Double>> results) {
		
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Time");
		yAxis.setLabel("Number of Orders");
		final LineChart<Number,Number> lineChart =
				new LineChart<Number,Number>(xAxis,yAxis);
		Button back = new Button("Back");
		Label average = new Label("Average FIFO time: ");
		Label average2 = new Label("Average Knapsack time: ");
		double totalOrders = 15 + 17 + 22 + 15;
		
		lineChart.setTitle("Drone Simulation");
		lineChart.setCreateSymbols(false);
		
		XYChart.Series series = new XYChart.Series();
		series.setName("FIFO");
		series.getData().add(new XYChart.Data(0, 0));
		for (int i = 0; i < results.get(0).size(); i++) {
			series.getData().add(new XYChart.Data(i, results.get(0).get(i)));
		}
		int FIFOAverage = (int)(results.get(0).get(results.get(0).size() - 1) / totalOrders);
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Knapsack");
        int index = 0;
        for (int i = 0; i < results.get(1).size(); i++) {
			series2.getData().add(new XYChart.Data(index, results.get(1).get(i)));
			index++;
		}
        int KPAverage = (int)(results.get(1).get(results.get(1).size() - 1) / totalOrders);
        
        lineChart.getData().addAll(series,series2);
        
		Label FIFOAverageLabel = new Label("FIFO Average: " + FIFOAverage);
		Label KPAverageLabel = new Label("Knapsack Average: " + KPAverage);
        
        average.setStyle("-fx-font-size:20");
        average2.setStyle("-fx-font-size:20");
        FIFOAverageLabel.setStyle("-fx-font-size:20");
        KPAverageLabel.setStyle("-fx-font-size:20");
        
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
		GridPane.setConstraints(KPAverageLabel, 0, 1);
		GridPane.setConstraints(back, 0, 2);
		
		subRoot.getChildren().addAll(FIFOAverageLabel,KPAverageLabel,back);
		
		VBox root = new VBox();
		root.setSpacing(10);
		root.getChildren().addAll(lineChart,subRoot);
		Scene scene = new Scene(root,1000,750);
		
		return scene;
	}
}
