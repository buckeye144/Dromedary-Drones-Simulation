import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class OrderSettings {
	
	public VBox orders(MainMenu mm) {
		
		VBox blank = new VBox();
		Button back = new Button("Back");
		
		back.setStyle("-fx-font-size:16");
		back.setMaxWidth(150);
		
		blank.getChildren().addAll(back);
		
		back.setOnAction(e -> {
			mm.menu.setScene(mm.menuWindow);
		});
		
		return blank;
	}
}
