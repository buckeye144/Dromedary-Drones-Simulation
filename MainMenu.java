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
		
		double BUTTON_WIDTH = 150;
		BorderPane menuScreen = new BorderPane();
		VBox menuButtons = new VBox();
		Scene menuWindow = new Scene(menuScreen, 1000, 750);
		Button start = new Button("Start Simulation");
		Button settings = new Button("Settings");
		
		//Button styles
		start.setStyle("-fx-font-size:16");
		start.setMaxWidth(BUTTON_WIDTH);
//		settings.setStyle("-fx-font-size:16");
//		settings.setMaxWidth(BUTTON_WIDTH);
		
		//VBox button list
		menuScreen.setPadding(new Insets(20, 0, 20, 20));
		menuButtons.setSpacing(10);
		menuButtons.setPadding(new Insets(0, 20, 10, 20));
		menuButtons.getChildren().addAll(start);
		
		//Button position
		menuButtons.setAlignment(Pos.CENTER);
		menuScreen.setBottom(menuButtons);
		
		//Button jobs
		settings.setOnAction(e -> {
			SettingsPage sp = new SettingsPage();
			menu.getScene().setRoot(sp.settingsPage());
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
