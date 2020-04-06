import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Results {

	public Scene results(MainMenu mm) {
		
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Time");
		yAxis.setLabel("Number of Orders");
		final LineChart<Number,Number> lineChart =
				new LineChart<Number,Number>(xAxis,yAxis);
		Button back = new Button("Back");
		Label average = new Label("Average FIFO time: ");
		Label average2 = new Label("Average Knapsack time: ");
		Label best = new Label("Best FIFO time: ");
		Label best2 = new Label("Best Knapsack time: ");
		Label worst = new Label("Worst FIFO time: ");
		Label worst2 = new Label("Worst Knapsack time: ");
		
		lineChart.setTitle("Drone Simulation");
		
		XYChart.Series series = new XYChart.Series();
		series.setName("Test");
		series.getData().add(new XYChart.Data(0, 0));
		series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Test 2");
        series2.getData().add(new XYChart.Data(0, 0));
        series2.getData().add(new XYChart.Data(1, 33));
        series2.getData().add(new XYChart.Data(2, 34));
        series2.getData().add(new XYChart.Data(3, 25));
        series2.getData().add(new XYChart.Data(4, 44));
        series2.getData().add(new XYChart.Data(5, 39));
        series2.getData().add(new XYChart.Data(6, 16));
        series2.getData().add(new XYChart.Data(7, 55));
        series2.getData().add(new XYChart.Data(8, 54));
        series2.getData().add(new XYChart.Data(9, 48));
        series2.getData().add(new XYChart.Data(10, 27));
        series2.getData().add(new XYChart.Data(11, 37));
        series2.getData().add(new XYChart.Data(12, 29));
        
        lineChart.getData().addAll(series,series2);
        
        average.setStyle("-fx-font-size:20");
        average2.setStyle("-fx-font-size:20");
        best.setStyle("-fx-font-size:20");
        best2.setStyle("-fx-font-size:20");
        worst.setStyle("-fx-font-size:20");
        worst2.setStyle("-fx-font-size:20");
        
		back.setStyle("-fx-font-size:16");
		back.setMaxWidth(150);
		back.setOnAction(e -> {
			mm.menu.setScene(mm.menuWindow);
		});
		
		GridPane subRoot = new GridPane();
		subRoot.setPadding(new Insets(10, 10, 10, 10));
		subRoot.setVgap(5);
		subRoot.setHgap(150);
		GridPane.setConstraints(average, 0, 0);
		GridPane.setConstraints(average2, 0, 1);
		GridPane.setConstraints(back, 0, 2);
		GridPane.setConstraints(best, 1, 0);
		GridPane.setConstraints(best2, 1, 1);
		GridPane.setConstraints(worst, 2, 0);
		GridPane.setConstraints(worst2, 2, 1);
		
		subRoot.getChildren().addAll(average,average2,best,best2,
				worst,worst2,back);
		
		VBox root = new VBox();
		root.setSpacing(10);
		root.getChildren().addAll(lineChart,subRoot);
		Scene scene = new Scene(root,1000,750);
		
		return scene;
	}
}
