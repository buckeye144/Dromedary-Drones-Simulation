import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MealSettings {

	
	int test = 0;
	
public VBox meals(MainMenu mm) {
		
		VBox page = new VBox();
		GridPane grid = new GridPane();
		ListView<String> meals = new ListView<String>();
		ListView<String> mealItems = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList ();
		ObservableList<String> foodItems = FXCollections.observableArrayList();
		TextField name = new TextField();
		TextField removeMeal = new TextField();
		Label title = new Label("List of meals");
		Label title2 = new Label("Food items in meal");
		Label addLabel = new Label("Add/edit a meal");
		Label removeLabel = new Label("Remove a meal");
		Label spacing = new Label("");
		Label spacing2 = new Label("");
		Label confirm =  new Label();
		Label confirm2 = new Label();
		Button add = new Button("Add");
		Button remove = new Button("Remove");
		Button back = new Button("Back");
		
		//Button/label styling
		addLabel.setStyle("-fx-font-size:16");
		name.setPromptText("Enter name");
		name.setStyle("-fx-font-size:16");
		name.setMinHeight(30);
		name.setMaxWidth(300);
		removeLabel.setStyle("-fx-font-size:16");
		removeMeal.setPromptText("Enter name");
		removeMeal.setStyle("-fx-font-size:16");
		removeMeal.setMinHeight(30);
		removeMeal.setMaxWidth(300);
		back.setStyle("-fx-font-size:16");
		back.setMaxWidth(150);
		title.setStyle("-fx-font-size:20");
		title2.setStyle("-fx-font-size:20");
		
		//Inserting items into the grid
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(meals, 0, 1);
		GridPane.setConstraints(spacing, 0, 2);
		GridPane.setConstraints(addLabel, 0, 3);
		GridPane.setConstraints(name, 0, 4);
		GridPane.setConstraints(add, 0, 5);
		GridPane.setConstraints(spacing2, 0, 6);
		GridPane.setConstraints(removeLabel, 0, 7);
		GridPane.setConstraints(removeMeal, 0, 8);
		GridPane.setConstraints(remove, 0, 9);
		GridPane.setConstraints(title2, 1, 0);
		GridPane.setConstraints(mealItems, 1, 1);
		GridPane.setConstraints(confirm, 1, 4);
		GridPane.setConstraints(confirm2, 1, 8);
		grid.getChildren().addAll(title,title2,meals,mealItems,addLabel,removeLabel,
				name,add,remove,removeMeal,spacing,spacing2,confirm,confirm2);
		
		//Add names of meals to the viewable/clickable list in settings
		for(int i = 0; i < mm.os.meals.size(); i++) {
			items.add(mm.os.meals.get(i).name);
		}
		
		//Adds names of food items in a meal to the list in settings
		meals.getSelectionModel().selectedItemProperty().addListener(e -> {
			foodItems.clear();
			int index = meals.getSelectionModel().getSelectedIndex();
			try {
				for(int i = 0; i < mm.os.meals.get(index).items.size(); i++) {
					foodItems.add(mm.os.meals.get(index).items.get(i).name);
				}
			} catch (Exception e1) {
			}
		});
		
//		add.setOnAction(e -> {
//			Meal temp = new Meal(name.getText(), );
//			mm.os.meals.add(temp);
//			foodItems.clear();
//			for(int i = 0; i < mm.os.meals.size(); i++) {
//				items.add(mm.os.meals.get(i).name);
//			}
//			confirm.setText("Added: " + name.getText());
//		});
		
		remove.setOnAction(e -> {
			//Can't remove nothing
			if (removeMeal.getText().matches("")) {
				confirm2.setText("Invalid meal name");
			}
			//Search for food item
			else {
				//Food item found
				if(items.contains(removeMeal.getText())) {
					items.remove(removeMeal.getText());
					removeMeal.setText("Removed " + removeMeal.getText());
				} else {
					confirm2.setText("Meal does not exist!");
				}
			}
		});
		
		meals.setItems(items);
		meals.setMinHeight(300);
		meals.setMinWidth(400);
		meals.setStyle("-fx-font-size:20");
		mealItems.setItems(foodItems);
		mealItems.setStyle("-fx-font-size:20");
		mealItems.setMinHeight(300);
		mealItems.setMinWidth(400);
		
		page.setSpacing(10);
		page.getChildren().addAll(grid,back);
		
		back.setOnAction(e -> {
			mm.menu.setScene(mm.menuWindow);
		});
		
		return page;
	}
	
	
}
