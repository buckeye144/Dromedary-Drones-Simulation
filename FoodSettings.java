import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FoodSettings {
	
	public VBox food(MainMenu mm) {
		
		VBox foodPage = new VBox();
		GridPane grid = new GridPane();
		ListView<String> foodList = new ListView<String>();
		ObservableList<String> food = FXCollections.observableArrayList();
		TextField name = new TextField();
		TextField weight = new TextField();
		TextField removeFood = new TextField();
		Label title = new Label("List of food items");
		Label addLabel = new Label("Add/edit a food item");
		Label removeLabel = new Label("Remove a food item");
		Label confirm = new Label();
		Label confirm2 = new Label();
		Button add = new Button("Add");
		Button remove = new Button("Remove");
		Button back = new Button("Back");
		
		back.setStyle("-fx-font-size:16");
		back.setMaxWidth(150);
		
		name.setPromptText("Enter name");
		weight.setPromptText("Enter weight");
		removeFood.setPromptText("Enter name");
		
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		GridPane.setConstraints(addLabel, 0, 0);
		GridPane.setConstraints(removeLabel, 4, 0);
		GridPane.setConstraints(name, 0, 1);
		GridPane.setConstraints(weight, 0, 2);
		GridPane.setConstraints(confirm, 0, 3);
		GridPane.setConstraints(add, 1, 1);
		GridPane.setConstraints(remove, 5, 1);
		GridPane.setConstraints(removeFood, 4, 1);
		GridPane.setConstraints(confirm2, 4, 2);
		grid.getChildren().addAll(addLabel,removeLabel,name,weight,
				add,remove,removeFood,confirm,confirm2);
		
		add.setOnAction(e -> {
			//If one of the boxes is empty, print error message
			if (name.getText().matches("") || weight.getText().matches("")) {
				confirm.setText("Invalid name/weight");
			}
			//Prevents letters/characters being used as weights
			else if (!weight.getText().matches("[0-9]*")) {
				confirm.setText("Invalid name/weight");
			}
			//Adds new food item
			else if (name.getText() != null && weight.getText() != null) {
				FoodItem f = new FoodItem(name.getText(), Integer.parseInt(weight.getText()));
				food.add(f.name);
				foodList.setItems(food);
				confirm.setText(f.name + " added!");
				name.clear();
				weight.clear();
			}
		});
		
		remove.setOnAction(e -> {
			//Can't remove nothing
			if (removeFood.getText().matches("")) {
				confirm2.setText("Invalid food item name");
			}
			//Search for food item
			else {
				//Food item found
				if(food.contains(removeFood.getText())) {
					food.remove(removeFood.getText());
					confirm2.setText("Removed " + removeFood.getText());
				} else {
					confirm2.setText("Food item does not exist");
				}
			}
		});
		
		
		
		back.setOnAction(e -> {
			mm.menu.setScene(mm.menuWindow);
		});
		
		title.setStyle("-fx-font-size:20");
		foodList.setStyle("-fx-font-size:20");
		foodList.setItems(food);
		foodList.setMaxSize(400, 200);
		foodPage.setSpacing(10);
		foodPage.getChildren().addAll(title,foodList,grid,back);
		
		return foodPage;
	}
}
