import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ShiftSettings {

	public VBox shiftSettings(MainMenu mm) {
		
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
