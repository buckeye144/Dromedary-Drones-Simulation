import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FoodSettings {
	
	public VBox food(MainMenu mm) {
		
		VBox foodPage = new VBox();
		GridPane grid = new GridPane();
		GridPane grid2 = new GridPane();
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
		Button update = new Button("Update");
		Button remove = new Button("Remove");
		Button back = new Button("Back");
		
		for(int i = 0; i < mm.os.foodList.size(); i++) {
			food.add(mm.os.foodList.get(i).name);
		}
		
		//Button/list/text field styling
		foodList.setStyle("-fx-font-size:20");
		foodList.setMaxSize(300, 1000);
		addLabel.setStyle("-fx-font-size:16");
		name.setPromptText("Enter name");
		name.setStyle("-fx-font-size:16");
		name.setMaxWidth(200);
		name.setMinHeight(30);
		weight.setPromptText("Enter weight");
		weight.setStyle("-fx-font-size:16");
		weight.setMaxWidth(200);
		weight.setMinHeight(30);
		removeLabel.setStyle("-fx-font-size:16");
		removeFood.setPromptText("Enter name");
		removeFood.setStyle("-fx-font-size:16");
		removeFood.setMaxWidth(200);
		removeFood.setMinHeight(30);
		back.setStyle("-fx-font-size:16");
		back.setMaxWidth(150);
		
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		grid2.setPadding(new Insets(10, 10, 10, 10));
		grid2.setVgap(5);
		grid2.setHgap(5);
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(foodList, 0, 1);
		GridPane.setConstraints(grid2, 1, 1);
		GridPane.setConstraints(addLabel, 0, 0);
		GridPane.setConstraints(name, 0, 1);
		GridPane.setConstraints(weight, 0, 2);
		GridPane.setConstraints(confirm, 0, 3);
		GridPane.setConstraints(removeLabel, 0, 4);
		GridPane.setConstraints(removeFood, 0, 5);
		GridPane.setConstraints(confirm2, 0, 6);
		GridPane.setConstraints(add, 1, 1);
		GridPane.setConstraints(update, 1, 2);
		GridPane.setConstraints(remove, 1, 5);
		
		grid2.getChildren().addAll(addLabel,name,weight,confirm,removeLabel,removeFood,
				confirm2,add,update,remove);
		grid.getChildren().addAll(title,foodList,grid2);
		
		foodList.getSelectionModel().selectedItemProperty().addListener(e ->{
			int index = foodList.getSelectionModel().getSelectedIndex();
			try {
				name.setText(mm.os.foodList.get(index).name);
				weight.setText(Integer.toString(mm.os.foodList.get(index).weight));
			} catch (Exception e1) {
			}
		});
		
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
				mm.os.foodList.add(f);
				food.add(f.name);
				foodList.setItems(food);
				confirm.setText(f.name + " added!");
				name.clear();
				weight.clear();
			}
		});
		
		update.setOnAction(e -> {
			//If one of the boxes is empty, print error message
			if (name.getText().matches("") || weight.getText().matches("")) {
				confirm.setText("Invalid name/weight");
			}
			//Prevents letters/characters being used as weights
			else if (weight.getText().matches("[0-9]*") || weight.getText().contains("-")) {
				int index = foodList.getSelectionModel().getSelectedIndex();
				mm.os.foodList.get(index).name = name.getText();
				mm.os.foodList.get(index).weight = Integer.parseInt(weight.getText());
				food.clear();
				for(int i = 0; i < mm.os.foodList.size(); i++) {
					food.add(mm.os.foodList.get(i).name);
				}
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
					for(int i = 0; i < mm.os.foodList.size(); i++) {
						if(mm.os.foodList.get(i).name.matches(removeFood.getText())) {
							mm.os.foodList.remove(i);
							break;
						}
					}
					confirm2.setText("Removed " + removeFood.getText());
					removeFood.clear();
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
		foodList.setMinSize(500, 300);
		foodPage.setSpacing(10);
		foodPage.getChildren().addAll(grid,back);
		
		return foodPage;
	}
}