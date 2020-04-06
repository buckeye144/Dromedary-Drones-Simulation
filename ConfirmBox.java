import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

	static boolean answer;
	
	public static boolean display() throws Exception {
		
		Stage window = new Stage();
		VBox root = new VBox();
		Scene confirmBox = new Scene(root);
		int windowX = 500;
		int windowY = 300;
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(250);
		
		
		Label message = new Label("Do you wish to exit the program?");
		message.setFont(new Font("Arial", 24));
		
		
		//Buttons and button layouts
		Button yes = new Button("Yes");
		Button no = new Button("No");	
		
		root.setSpacing(10);
		
		yes.setLayoutX((windowX / 2));
		yes.setLayoutY((windowY / 2));
		yes.setMaxWidth(100);
		yes.setStyle("-fx-font-size:16");
		no.setLayoutX((windowX / 2));
		no.setLayoutY((windowY / 2) + 30);
		no.setStyle("-fx-font-size:16");
		no.setMaxWidth(100);
		
		
		//Button commands
		yes.setOnAction(e -> {
			answer = true;
			window.close();
		});
		no.setOnAction(e ->{
			answer = false;
			window.close();
		});
		
		//Scene layouts
		root.setPrefSize(windowX, windowY);
		root.getChildren().addAll(message, yes, no);
		root.setAlignment(Pos.CENTER);
		window.setScene(confirmBox);
		window.setResizable(false);
		window.showAndWait();
		
		return answer;
	}
}
