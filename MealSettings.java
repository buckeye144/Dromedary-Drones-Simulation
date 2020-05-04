import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
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
	ObservableList<String> items;
	XML xml = new XML();
	MainMenu mm;
	Label totalProbability;
	int previous;
	
	public VBox meals(MainMenu mm) {
		
		this.mm = mm;
		VBox page = new VBox();
		GridPane grid = new GridPane();
		GridPane grid2 = new GridPane();
		ListView<String> meals = new ListView<String>();
		ListView<String> mealItems = new ListView<String>();
		ListView<FoodItem> addFoods = new ListView<FoodItem>();
		ObservableList<FoodItem> list = FXCollections.observableArrayList();
		items = FXCollections.observableArrayList ();
		ObservableList<String> foodItems = FXCollections.observableArrayList();
		TextField addMealBox = new TextField();
		TextField editProbability = new TextField();
		Label title = new Label("List of meals");
		Label title2 = new Label("Food items in meal");
		Label confirm =  new Label();
		Label confirm2 = new Label();
		totalProbability = new Label("Total probability: ");
		Label addMeal = new Label ("Add or change a meal");
		Label mealName = new Label("Name: ");
		Label errorMessage = new Label("");
		Label probabilityLabel = new Label("Probability: ");
		Button add = new Button("Add");
		Button remove = new Button("Remove");
		Button back = new Button("Back");
		Button remove2 = new Button("Remove");
		Button update = new Button("Update");
		Button add2 = new Button("Add Food to Meal");
		Button add3 = new Button("Add Food to Meal");

		//Button/label styling
		back.setStyle("-fx-font-size:16");
		back.setMaxWidth(150);
		title.setStyle("-fx-font-size:20");
		title2.setStyle("-fx-font-size:20");
		totalProbability.setStyle("-fx-font-size:20");
		addMeal.setStyle("-fx-font-size:16");
		mealName.setStyle("-fx-font-size:16");
		addMealBox.setMaxWidth(200);
		addMealBox.setMinHeight(30);
		editProbability.setMaxWidth(200);
		editProbability.setMinHeight(30);
		editProbability.setStyle("-fx-font-size: 16");
		addMealBox.setStyle("-fx-font-size:16");
		addMealBox.setPromptText("Enter a meal name");
		probabilityLabel.setStyle("-fx-font-size: 16");
		editProbability.setPromptText("Enter meal probability");
		
		grid2.setPadding(new Insets(10, 10, 10, 10));
		grid2.setVgap(5);
		grid2.setHgap(5);
		GridPane.setConstraints(mealName, 0, 1);
		GridPane.setConstraints(probabilityLabel, 0, 2);
		GridPane.setConstraints(addMeal, 1, 0);
		GridPane.setConstraints(addMealBox, 1, 1);
		GridPane.setConstraints(editProbability, 1, 2);
		GridPane.setConstraints(confirm, 1, 3);
		GridPane.setConstraints(add, 2, 2);
		GridPane.setConstraints(totalProbability, 1, 4);
		GridPane.setConstraints(update, 3, 2);
		
		grid2.getChildren().addAll(addMeal,mealName,addMealBox,probabilityLabel,
				editProbability,confirm,add,totalProbability,update);
		
		//Inserting items into the grid
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(meals, 0, 1);
		GridPane.setConstraints(remove, 0, 2);
		GridPane.setConstraints(confirm2, 0, 3);
		GridPane.setConstraints(title2, 1, 0);
		GridPane.setConstraints(mealItems, 1, 1);
		GridPane.setConstraints(remove2, 1, 2);
		GridPane.setConstraints(add2, 1, 3);
		GridPane.setConstraints(grid2, 0, 4);
		
		grid.getChildren().addAll(title,title2,meals,mealItems,
				remove,remove2,confirm2,grid2);
		
		//Add names of meals to the viewable/clickable list in settings
		//Also count the total probability percentage
		totalProbability.setText("Total probability: " + loadMeals(mm) + "%");
		
		//Adds names of food items in a meal to the list in settings
		meals.getSelectionModel().selectedItemProperty().addListener(e -> {
			foodItems.clear();
			int index = meals.getSelectionModel().getSelectedIndex();
			try {
				for(int i = 0; i < mm.os.meals.get(index).items.size(); i++) {
					foodItems.add(mm.os.meals.get(index).items.get(i).name);
				}
				double test = mm.os.meals.get(index).probability * 100;
				int test2 = (int)test;
				String temp = String.format("%d", test2);
				editProbability.setText(temp);
				addMealBox.setText(mm.os.meals.get(index).name);
				grid.getChildren().add(add2);
			} catch (Exception e1) {
			}
		});
		
		add.setOnAction(e -> {
			if(addMealBox.getText().matches("") || editProbability.getText().matches("")) {
				confirm.setText("Invalid Name");
			}
			else if (checkForExistingName(addMealBox.getText())) {
				confirm.setText("Meal name already exists");
			}
			else if(checkProbability((int)Double.parseDouble(editProbability.getText()), loadMeals(mm))) {
				confirm.setText("Cannot go above 100% probability");
			}
			else {
				ObservableList<FoodItem> empty = FXCollections.observableArrayList();
				Meal temp = new Meal(addMealBox.getText(), empty, Double.parseDouble(editProbability.getText()) / 100);
				mm.os.meals.add(temp);
				foodItems.clear();
				items.clear();
				for(int i = 0; i < mm.os.meals.size(); i++) {
					items.add(mm.os.meals.get(i).name);
				}
				totalProbability.setText("Total probability: " + loadMeals(mm) + "%");
				confirm.setText("Added: " + addMealBox.getText());
			}
		});
		
		add2.setOnAction(e -> {
			Label addFood = new Label("Add food to a meal");
			
			for(int i = 0; i < mm.os.foodList.size(); i++) {
				list.add(mm.os.foodList.get(i));
			}
			addFood.setStyle("-fx-font-size: 20");
			addFoods.setStyle("-fx-font-size: 20");
			
			addFoods.setItems(list);
			addFoods.setMaxHeight(200);
			addFoods.setMinWidth(300);
			
			grid.getChildren().remove(add2);
			
			GridPane.setConstraints(add3, 2, 2);
			GridPane.setConstraints(addFood, 2, 0);
			GridPane.setConstraints(addFoods, 2, 1);
			
			grid.getChildren().addAll(addFood,addFoods,add3);
		});
		
		add3.setOnAction(e -> {
			int index = meals.getSelectionModel().getSelectedIndex();
			int index2 = addFoods.getSelectionModel().getSelectedIndex();
			mm.os.meals.get(index).items.add(mm.os.foodList.get(index2));
			foodItems.clear();
			try {
				for(int i = 0; i < mm.os.meals.get(index).items.size(); i++) {
					foodItems.add(mm.os.meals.get(index).items.get(i).name);
				}
			} catch (Exception e1) {
			}
		});
		
		update.setOnAction(e -> {
			int index = meals.getSelectionModel().getSelectedIndex();
			if(meals.getSelectionModel().isEmpty()) {
				confirm.setText("Please select something to update");
			} else {
				if(index == -1) {
					for(int i = 0; i < items.size(); i++) {
						if(items.get(i).matches(addMealBox.getText())) {
							index = meals.getItems().indexOf(items.get(i));
						}
					}
				}
				previous = (int) (mm.os.meals.get(index).probability * 100);
				if(addMealBox.getText().matches("")) {
					confirm.setText("Invalid Name");
				}
				else if(checkForExistingNameUpdating(addMealBox.getText(), index)) {
					confirm.setText("Meal name already exists");
				}
				else if(editProbability.getText().contains("-")) {
					confirm.setText("Cannot insert negative values");
				}
				else if(checkProbabilityUpdating((int)Double.parseDouble(editProbability.getText()), loadMeals(mm), previous)) {
					confirm.setText("Cannot go above 100% probability");
				}
				else if(editProbability.getText().matches("[0-9]*")) {
	//				xml.updateMeal(mm.os.meals.get(index), addMealBox.getText(), editProbability.getText());
					mm.os.meals.get(index).name = addMealBox.getText();
					mm.os.meals.get(index).probability = Double.parseDouble(editProbability.getText()) / 100;
					items.clear();
					totalProbability.setText("Total probability: " + loadMeals(mm) + "%");
					confirm.setText("Updated");
					addMealBox.clear();
					editProbability.clear();
				}
			}
		});
		
		//Works
		remove.setOnAction(e -> {
			if(meals.getSelectionModel().isEmpty()) {
				confirm2.setText("Nothing selected");
			} else {
				int index = meals.getSelectionModel().getSelectedIndex();
				confirm2.setText("Removed " + items.get(index));
//				xml.remove(items.get(index), "meal", "mealName", "meals.xml");
				items.remove(index);
				mm.os.meals.remove(index);
				meals.getSelectionModel().clearSelection();
				editProbability.clear();
			}
		});
		
		//Works
		remove2.setOnAction(e -> {
			if(mealItems.getSelectionModel().isEmpty()) {
				confirm.setText("Nothing selected");
			} else {
				int index = meals.getSelectionModel().getSelectedIndex();
				int index2 = mealItems.getSelectionModel().getSelectedIndex();
				confirm.setText("Removed " + foodItems.get(index2));
//				xml.removeMealFood(items.get(index), foodItems.get(index2), index2);
				foodItems.remove(index2);
				mm.os.meals.get(index).items.remove(index2);
				mealItems.getSelectionModel().clearSelection();
			}
		});
		
		meals.setItems(items);
		meals.setMaxHeight(200);
		meals.setMinWidth(300);
		meals.setStyle("-fx-font-size:20");
		errorMessage.setStyle("-fx-font-size:16");
		mealItems.setItems(foodItems);
		mealItems.setStyle("-fx-font-size:20");
		mealItems.setMaxHeight(200);
		mealItems.setMaxWidth(300);
		page.setSpacing(10);
		page.getChildren().addAll(grid,errorMessage,back);
		
		back.setOnAction(e -> {
			mm.menu.setScene(mm.menuWindow);
		});
		
		return page;
	}
	
	private boolean checkForExistingName(String searchFor) {
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).matches(searchFor)) {
				return true;
			}
		}
		return false;
	}
	
	private int loadMeals(MainMenu mm) {
		int totalProbability = 0;
		items.clear();
		mm.os.meals.clear();
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse("meals.xml");
			
			//Get a list of meals
			NodeList meals = doc.getElementsByTagName("meal");
			
			//Get a list of food in each meal
			NodeList allFood = doc.getElementsByTagName("foodList");
			for(int i = 0; i < meals.getLength(); i++) {
				
				//Get one meal
				Element mealThing = (Element)meals.item(i);
				
				//Get the meals name
				Element mealName = (Element)mealThing.getElementsByTagName("mealName").item(0);
				String mName = mealName.getTextContent();
				
				//Get the meals probability
				Element mealProbability = (Element)mealThing.getElementsByTagName("probability").item(0);
				double probability = Double.parseDouble(mealProbability.getTextContent());
				double test = probability * 100.0;
				//Keep a total count of each meals probabilities
				totalProbability += (int)test;
				
				//Make food items for the food in each meal
				ObservableList<FoodItem> foodTemp = FXCollections.observableArrayList();
				Element foodList = (Element)allFood.item(i);
				NodeList foods = foodList.getChildNodes();
				for(int j = 0; j < foods.getLength(); j++) {
					Node n = foods.item(j);
					if (n.getNodeType() == Node.ELEMENT_NODE) {
						Element food = (Element)foods.item(j);
						String foodName = food.getTextContent();
						int weight = 0;
						
						//Find the foods weight previously loaded from food.xml
						for(int k = 0; k < mm.os.foodList.size(); k++) {
							if(mm.os.foodList.get(k).name.toUpperCase().matches(foodName.toUpperCase())) {
								weight = mm.os.foodList.get(k).weight;
							}
						}
						
						//Add the temporary food item
						FoodItem tempFoodItem = new FoodItem(foodName, weight);
						foodTemp.add(tempFoodItem);
					}
				}
				
				//Add the meal to the list for viewing
				Meal temp = new Meal(mName, foodTemp, probability);
				mm.os.meals.add(temp);
				items.add(mName);
			}
			
		} catch (ParserConfigurationException | 
				SAXException | 
				IOException tfe) {
			tfe.printStackTrace();
		}
		return totalProbability;
	}
	
	private boolean checkForExistingNameUpdating(String toSearchFor, int index) {
		for(int i = 0; i < items.size(); i++) {
			if(i != index) {
				if(items.get(i).matches(toSearchFor)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkProbability(int prob, int total) {
		if(total + prob > 100) {
			return true;
		}
		return false;
	}
	
private boolean checkProbabilityUpdating(int prob, int total, int previous) {
		if((total - previous) + prob > 100) {
			return true;
		}
		return false;
	}
	
}
