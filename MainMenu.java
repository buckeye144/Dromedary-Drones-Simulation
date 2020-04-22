import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {
	Stage menu;
	Scene menuWindow;
	ArrayList<ArrayList<Double>> results;
	makeOrders os;
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		menu = primaryStage;
		
		os = new makeOrders();
		Map m = new Map("locations.copy.xml");
		
//		menu.setOnCloseRequest(e -> {
//			e.consume();
//			try {
//				closeProgram();
//			} catch(Exception ex) {
//				ex.printStackTrace();
//			}
//		});
		
		BorderPane menuScreen = new BorderPane();
		VBox menuButtons = new VBox();
		menuWindow = new Scene(menuScreen, 1000, 750);
		Button start = new Button("Start Simulation");
		Button settings = new Button("Settings");
		Button viewResults = new Button("View Results");
		Button quit = new Button("Quit");
		
		//Button styles
		double BUTTON_WIDTH = 150;
		start.setStyle("-fx-font-size:16");
		start.setMaxWidth(BUTTON_WIDTH);
		settings.setStyle("-fx-font-size:16");
		settings.setMaxWidth(BUTTON_WIDTH);
		viewResults.setStyle("-fx-font-size:16");
		viewResults.setMaxWidth(BUTTON_WIDTH);
		quit.setStyle("-fx-font-size:16");
		quit.setMaxWidth(BUTTON_WIDTH);
		
		//VBox button list
		menuButtons.setSpacing(10);
		menuButtons.setPadding(new Insets(0, 20, 10, 20));
		menuButtons.getChildren().addAll(start,settings,quit);
		
		//Button position
		menuButtons.setAlignment(Pos.CENTER);
		menuScreen.setBottom(menuButtons);
		
		//Button jobs
		start.setOnAction(e -> {
			results = new ArrayList<ArrayList<Double>>();

			ArrayList<Double> fifo = new ArrayList<Double>();
			ArrayList<Double> knapsack = new ArrayList<Double>();
			ArrayList<Double> fifoTemp = new ArrayList<Double>();
			ArrayList<Double> knapsackTemp = new ArrayList<Double>();

			//initialize the values to 0
			for(int i = 0; i < 38+45+60+30; i++){
				knapsack.add(0.0);
				fifo.add(0.0);
			}

			for(int i = 0; i < 50; i++){ //run it 50 times and average the results
				os.simulation(m);
				fifoTemp = os.FIFO();
				knapsackTemp = os.KnapSack();
				for(int j = 0; j < fifoTemp.size(); j++){
					knapsack.set(j, knapsack.get(j) + knapsackTemp.get(j));
					fifo.set(j, fifo.get(j) + fifoTemp.get(j));
				}
				if(i == 49){// take the average
					for(int j = 0; j < fifoTemp.size(); j++){
						knapsack.set(j, knapsack.get(j)/50);
						fifo.set(j, fifo.get(j)/50);
					}
				}
			}
			results.add(fifo);
			results.add(knapsack);
			Results r = new Results();
			menu.setScene(r.results(this, results));
//			exportExcel excel = new exportExcel();
//			excel.printExcel(results);
		});
		
		settings.setOnAction(e -> {
			SettingsPage sp = new SettingsPage();
			menu.setScene(sp.settingsPage(this, m));
		});
		
		viewResults.setOnAction(e -> {
			Results r = new Results();
			menu.setScene(r.results(this, results));
		});
		
		quit.setOnAction(e -> {
//			try {
//				closeProgram();
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
			menu.close();
		});
		
		menu.setTitle("Drone Delivery Simulation");
		menu.setScene(menuWindow);
		menu.setResizable(true);
		menu.show();
	}
	
	private void closeProgram() throws Exception {
		boolean answer = ConfirmBox.display();
		if (answer) {
			menu.close();
		}
	}
}
