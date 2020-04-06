import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class MealSettings {

	
	int test = 0;
	
public VBox meals(MainMenu mm) {
		
		VBox page = new VBox();
		ListView<String> locations = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList (
				"Two burgers, fries and drink", "Burger and fries, no drink", 
				"Two burgers and fries, no drink");
		Label title = new Label("List of current meals");
		Button back = new Button("Back");
		
		back.setStyle("-fx-font-size:16");
		back.setMaxWidth(150);
		
		title.setStyle("-fx-font-size:20");
		locations.setStyle("-fx-font-size:20");
		locations.setItems(items);
		locations.setMaxSize(400, 200);
		
		page.setSpacing(10);
		page.getChildren().addAll(title,locations,back);
		
		back.setOnAction(e -> {
			mm.menu.setScene(mm.menuWindow);
		});
		
		return page;
	}
	
	
}
