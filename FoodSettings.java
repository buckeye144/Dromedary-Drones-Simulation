import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

public class FoodSettings {
	
	ObservableList<String> food;
	String sameFood;
	XML xml = new XML();
	
	public VBox food(MainMenu mm) {
		
		VBox foodPage = new VBox();
		GridPane grid = new GridPane();
		GridPane grid2 = new GridPane();
		ListView<String> foodList = new ListView<String>();
		food = FXCollections.observableArrayList();
		TextField name = new TextField();
		TextField weight = new TextField();
		Label title = new Label("List of food items");
		Label addLabel = new Label("Add or change a food item");
		Label removeLabel = new Label("Remove selected food");
		Label confirm = new Label();
		Label confirm2 = new Label();
		Label foodName = new Label("Name: ");
		Label foodWeight = new Label("Weight: ");
		Button add = new Button("Add");
		Button update = new Button("Update");
		Button remove = new Button("Remove");
		Button back = new Button("Back");
		
		//Reads the xml file and updates the viewable list
		readFoodItems(mm);
		
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
		foodName.setStyle("-fx-font-size:16");
		foodWeight.setStyle("-fx-font-size:16");
		removeLabel.setStyle("-fx-font-size:16");
		back.setStyle("-fx-font-size:16");
		back.setMaxWidth(150);
		
		//Alligns the buttons/text boxes on the page
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		grid2.setPadding(new Insets(10, 10, 10, 10));
		grid2.setVgap(5);
		grid2.setHgap(5);
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(foodList, 0, 1);
		GridPane.setConstraints(grid2, 1, 1);
		GridPane.setConstraints(foodName, 0, 1);
		GridPane.setConstraints(foodWeight, 0, 2);
		GridPane.setConstraints(addLabel, 1, 0);
		GridPane.setConstraints(name, 1, 1);
		GridPane.setConstraints(weight, 1, 2);
		GridPane.setConstraints(confirm, 1, 3);
		GridPane.setConstraints(removeLabel, 1, 4);
		GridPane.setConstraints(remove, 1, 5);
		GridPane.setConstraints(confirm2, 1, 6);
		GridPane.setConstraints(add, 2, 2);
		GridPane.setConstraints(update, 3, 2);
		
		//Adds each item to each grid
		grid2.getChildren().addAll(foodName,foodWeight,addLabel,name,weight,confirm,removeLabel,
				confirm2,add,update,remove);
		grid.getChildren().addAll(title,foodList,grid2);
		
		//Checks if an item in the list has been clicked on and
		//Fills in the information of that clicked item into text boxes
		foodList.getSelectionModel().selectedItemProperty().addListener(e ->{
			int index = foodList.getSelectionModel().getSelectedIndex();
			try {
				name.setText(mm.os.foodList.get(index).name);
				weight.setText(Integer.toString(mm.os.foodList.get(index).weight));
			} catch (Exception e1) {
			}
		});
		
		//Adds a new food item
		add.setOnAction(e -> {
			//If one of the boxes is empty, print error message
			if (name.getText().matches("") || weight.getText().matches("")) {
				confirm.setText("Invalid name/weight");
			}
			else if (checkForExistingName(name.getText())) {
				confirm.setText("Food name already exists");
			}
			//Prevents letters/characters being used as weights
			else if (!weight.getText().matches("[0-9]*")) {
				confirm.setText("Invalid name/weight");
			}
			//Adds new food item
			else if (name.getText() != null && weight.getText() != null) {
				FoodItem f = new FoodItem(name.getText(), Integer.parseInt(weight.getText()));
				xml.addFood(f);
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
			else if (checkForExistingNameUpdating(name.getText(), foodList.getSelectionModel().getSelectedIndex())) {
				confirm.setText("Food name already exists");
			}
			//Prevents letters/characters being used as weights
			else if (weight.getText().matches("[0-9]*")) {
				int index = foodList.getSelectionModel().getSelectedIndex();
				mm.os.foodList.get(index).name = name.getText();
				mm.os.foodList.get(index).weight = Integer.parseInt(weight.getText());
				xml.updateFood(mm.os.foodList.get(index).name, name.getText(), weight.getText());
				food.clear();
				readFoodItems(mm);
				confirm.setText("Updated");
			}
		});
		
		//Removes a food item
		remove.setOnAction(e -> {
			if(foodList.getSelectionModel().isEmpty()) {
				confirm2.setText("Nothing selected");
			} else {
				int index = foodList.getSelectionModel().getSelectedIndex();
				confirm2.setText("Removed " + food.get(index));
				xml.remove(food.get(index), "food", "foodName", "food.xml");
				mm.os.foodList.remove(index);
				food.remove(index);
				foodList.getSelectionModel().clearSelection();
				name.clear();
				weight.clear();
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

	//Reads and stores what's listed in the xml file
	private void readFoodItems(MainMenu mm) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse("food.xml");
			NodeList nodes = doc.getElementsByTagName("food");
			for (int i = 0; i < nodes.getLength(); i++) {
				Element foodThing = (Element)nodes.item(i);
				Element foodName = (Element)foodThing.getElementsByTagName("foodName").item(0);
				String fName = foodName.getTextContent();
				Element foodWeight = (Element)foodThing.getElementsByTagName("weight").item(0);
				int weight = Integer.parseInt(foodWeight.getTextContent());
				food.add(fName);
				FoodItem temp = new FoodItem(fName, weight);
				mm.os.foodList.add(temp);
			}
		} catch (ParserConfigurationException | 
				SAXException | 
				IOException tfe) {
			tfe.printStackTrace();
		}
	}
	
	//Checks if a food item has the same name as the new food item
	private boolean checkForExistingName(String toSearchFor) {
		for(int i = 0; i < food.size(); i++) {
			if(food.get(i).matches(toSearchFor)) {
				return true;
			}
		}
		return false;
	}
	
	//Checks if updating a food item has the same name as
	//A already existing food item
	private boolean checkForExistingNameUpdating(String toSearchFor, int index) {
		for(int i = 0; i < food.size(); i++) {
			if(i != index) {
				if(food.get(i).matches(toSearchFor)) {
					return true;
				}
			}
		}
		return false;
	}
}