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
			os.simulation(m);
			results = new ArrayList<ArrayList<Double>>();
			results.add(os.FIFO());
			results.add(os.KnapSack());
			Results r = new Results();
			menu.setScene(r.results(this, results));
			exportExcel excel = new exportExcel();
			excel.printExcel(results);
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
		boolean answer = true; //ConfirmBox.display();
		if (answer) {
			menu.close();
		}
	}
}
