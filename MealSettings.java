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
import javafx.geometry.VPos;
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
	
	public VBox meals(MainMenu mm) {
		
		VBox page = new VBox();
		GridPane grid = new GridPane();
		GridPane grid2 = new GridPane();
		ListView<String> meals = new ListView<String>();
		ListView<String> mealItems = new ListView<String>();
		items = FXCollections.observableArrayList ();
		ObservableList<String> foodItems = FXCollections.observableArrayList();
		TextField addMealBox = new TextField();
		Label title = new Label("List of meals");
		Label title2 = new Label("Food items in meal");
		Label spacing = new Label("");
		Label confirm =  new Label();
		Label confirm2 = new Label();
		Label probability = new Label("Meal probabilty: ");
		Label totalProbability = new Label("Total probability: ");
		Label addMeal = new Label ("Add or change a meal");
		Label mealName = new Label("Name: ");
		Label errorMessage = new Label("");
		Button add = new Button("Add");
		Button remove = new Button("Remove");
		Button back = new Button("Back");
		Button remove2 = new Button("Remove");
		
		//Button/label styling
		back.setStyle("-fx-font-size:16");
		back.setMaxWidth(150);
		title.setStyle("-fx-font-size:20");
		title2.setStyle("-fx-font-size:20");
		probability.setStyle("-fx-font-size:20");
		totalProbability.setStyle("-fx-font-size:20");
		addMeal.setStyle("-fx-font-size:16");
		mealName.setStyle("-fx-font-size:16");
		addMealBox.setMaxWidth(200);
		addMealBox.setMinHeight(30);
		addMealBox.setStyle("-fx-font-size:16");
		addMealBox.setPromptText("Enter a meal name");
		
		grid2.setPadding(new Insets(10, 10, 10, 10));
		grid2.setVgap(5);
		grid2.setHgap(5);
		GridPane.setConstraints(mealName, 0, 1);
		GridPane.setConstraints(addMeal, 1, 0);
		GridPane.setConstraints(addMealBox, 1, 1);
		GridPane.setConstraints(add, 2, 1);
		GridPane.setConstraints(confirm, 1, 2);
		
		grid2.getChildren().addAll(addMeal,mealName,addMealBox,confirm,add);
		
		//Inserting items into the grid
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(meals, 0, 1);
		GridPane.setConstraints(remove, 0, 2);
		GridPane.setConstraints(confirm2, 0, 3);
		GridPane.setConstraints(spacing, 0, 4);
		GridPane.setConstraints(probability, 0, 5);
		GridPane.setConstraints(totalProbability, 0, 6);
		GridPane.setConstraints(title2, 1, 0);
		GridPane.setConstraints(mealItems, 1, 1);
		GridPane.setConstraints(remove2, 1, 2);
		GridPane.setConstraints(grid2, 2, 1);
		
		GridPane.setValignment(probability, VPos.TOP);
		grid.getChildren().addAll(title,title2,meals,mealItems,totalProbability,
				remove,spacing,remove2,confirm2,probability,grid2);
		
		//Add names of meals to the viewable/clickable list in settings
		//Also count the total probability percentage
		double percentage = loadMeals(mm);
		totalProbability.setText("Total probability: " + percentage * 100.0 + "%");
		
		//Adds names of food items in a meal to the list in settings
		meals.getSelectionModel().selectedItemProperty().addListener(e -> {
			foodItems.clear();
			int index = meals.getSelectionModel().getSelectedIndex();
			try {
				for(int i = 0; i < mm.os.meals.get(index).items.size(); i++) {
					foodItems.add(mm.os.meals.get(index).items.get(i).name);
				}
				String temp = String.format(" probability: %.0f", 
						mm.os.meals.get(index).probability * 100);
				probability.setText(items.get(index) + temp + "%");
				addMealBox.setText(items.get(index));
			} catch (Exception e1) {
			}
		});
		
		add.setOnAction(e -> {
			if(addMealBox.getText().matches("")) {
				confirm.setText("Invalid Name");
			}
			else if (checkForExistingName(addMealBox.getText())) {
				confirm.setText("Meal name already exists");
			}
			else {
				ObservableList<FoodItem> empty = FXCollections.observableArrayList();
				Meal temp = new Meal(addMealBox.getText(), empty, 0.0);
				mm.os.meals.add(temp);
				foodItems.clear();
				items.clear();
				for(int i = 0; i < mm.os.meals.size(); i++) {
					items.add(mm.os.meals.get(i).name);
				}
				confirm.setText("Added: " + addMealBox.getText());
			}
		});
		
		
		//Works
		remove.setOnAction(e -> {
			if(meals.getSelectionModel().isEmpty()) {
				confirm2.setText("Nothing selected");
			} else {
				int index = meals.getSelectionModel().getSelectedIndex();
				confirm2.setText("Removed " + items.get(index));
				xml.remove(items.get(index), "meal", "mealName", "meals.xml");
				items.remove(index);
				mm.os.meals.remove(index);
				meals.getSelectionModel().clearSelection();
				probability.setText("Meal probability: ");
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
				xml.removeMealFood(items.get(index), foodItems.get(index2), index2);
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
	
	private double loadMeals(MainMenu mm) {
		double totalProbability = 0.0;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse("meals.xml");
			NodeList meals = doc.getElementsByTagName("meal");
			NodeList allFood = doc.getElementsByTagName("foodList");
			for(int i = 0; i < meals.getLength(); i++) {
				Element mealThing = (Element)meals.item(i);
				Element mealName = (Element)mealThing.getElementsByTagName("mealName").item(0);
				String mName = mealName.getTextContent();
				Element mealProbability = (Element)mealThing.getElementsByTagName("probability").item(0);
				double probability = Double.parseDouble(mealProbability.getTextContent());
				totalProbability += probability;
				ObservableList<FoodItem> foodTemp = FXCollections.observableArrayList();
				Element foodList = (Element)allFood.item(i);
				NodeList foods = foodList.getChildNodes();
				for(int j = 0; j < foods.getLength(); j++) {
					Node n = foods.item(j);
					if (n.getNodeType() == Node.ELEMENT_NODE) {
						Element food = (Element)foods.item(j);
						String foodName = food.getTextContent();
						int weight = 0;
						for(int k = 0; k < mm.os.foodList.size(); k++) {
							if(mm.os.foodList.get(k).name.matches(foodName)) {
								weight = mm.os.foodList.get(k).weight;
							}
						}
						FoodItem tempFoodItem = new FoodItem(foodName, weight);
						foodTemp.add(tempFoodItem);
					}
				}
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
}
