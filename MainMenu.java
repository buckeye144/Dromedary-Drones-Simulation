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
	ArrayList<Double> results;
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		menu = primaryStage;
		
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
		menuButtons.getChildren().addAll(start,settings,viewResults,quit);
		
		//Button position
		menuButtons.setAlignment(Pos.CENTER);
		menuScreen.setBottom(menuButtons);
		
		//Button jobs
		start.setOnAction(e -> {
			makeOrders os = new makeOrders();
			results = os.simulation();
			exportExcel excel = new exportExcel();
			excel.printExcel(results);
		});
		
		settings.setOnAction(e -> {
			SettingsPage sp = new SettingsPage();
			menu.setScene(sp.settingsPage(this));
		});
		
		viewResults.setOnAction(e -> {
			Results r = new Results();
			menu.setScene(r.results(this));
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
		menu.setResizable(false);
		menu.show();
	}
	
	private void closeProgram() throws Exception {
		boolean answer = ConfirmBox.display();
		if (answer) {
			menu.close();
		}
	}
}
