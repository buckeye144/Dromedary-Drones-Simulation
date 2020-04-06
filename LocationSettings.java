import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class LocationSettings {

	public VBox locations(MainMenu mm) {
		
		XML xml = new XML();
		GridPane grid = new GridPane();
		VBox page = new VBox();
		ListView<String> locations = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList();
		TextField name = new TextField();
		TextField x = new TextField();
		TextField y = new TextField();
		TextField removeLocation = new TextField();
		TextField editLocation = new TextField();
		Label title = new Label("List of current locations");
		Label addLabel = new Label("Add/edit a location");
		Label removeLabel = new Label("Remove a location");
		Label confirm = new Label();
		Label confirm2 = new Label();
		Button add = new Button("Add");
		Button remove = new Button("Remove");
		Button update = new Button("Update");
		Button back = new Button("Back");
		ToggleGroup group = new ToggleGroup();
		RadioButton r1 = new RadioButton("Location name");
		RadioButton r2 = new RadioButton("X coordinate");
		RadioButton r3 = new RadioButton("Y coordinate");
		
		for(int i = 0; i < xml.items.getLength(); i++) {
			Node p = xml.items.item(i);
			if (p.getNodeType() == Node.ELEMENT_NODE) {
				Element q = (Element) p;
				NodeList r = q.getChildNodes();
				items.add(r.item(1).getTextContent());
			}
		}
		
		r1.setToggleGroup(group);
		r2.setToggleGroup(group);
		r3.setToggleGroup(group);
		
		back.setStyle("-fx-font-size:16");
		back.setMaxWidth(150);
		
		name.setPromptText("Enter name of location");
		x.setPromptText("Enter X coordinate");
		y.setPromptText("Enter Y coordinate");
		removeLocation.setPromptText("Enter name of location");
		editLocation.setPromptText("Enter changes");
		
		locations.setStyle("-fx-font-size:16");
		locations.setItems(items);
		locations.setMaxSize(300, 1000);
		locations.getSelectionModel().selectedItemProperty().addListener(e-> {
			name.setText(locations.getSelectionModel().getSelectedItem());
			outerloop:
			for (int i = 0; i < xml.items.getLength(); i++) {
				Node p = xml.items.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE) {
					Element food = (Element) p;
					NodeList list = food.getChildNodes();
					for (int j = 0; j < list.getLength(); j++) {
						Node n = list.item(j);
						if (n.getNodeType() == Node.ELEMENT_NODE) {
							Element attribute = (Element) n;
							if(attribute.getTextContent().matches(locations.getSelectionModel().getSelectedItem())) {
								x.setText(list.item(3).getTextContent());
								y.setText(list.item(5).getTextContent());
								break outerloop;
							}
						}
					}
				}
			}
		});
		
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		GridPane.setConstraints(locations, 0, 0);
		GridPane.setConstraints(addLabel, 1, 1);
		GridPane.setConstraints(name, 1, 2);
		GridPane.setConstraints(x, 1, 3);
		GridPane.setConstraints(y, 1, 4);
		GridPane.setConstraints(confirm, 1, 5);
		GridPane.setConstraints(add, 2, 2);
		GridPane.setConstraints(update, 2, 3);
		GridPane.setConstraints(removeLabel, 5, 1);
		GridPane.setConstraints(removeLocation, 5, 2);
		GridPane.setConstraints(confirm2, 5, 3);
		GridPane.setConstraints(remove, 6, 2);
		
		grid.getChildren().addAll(addLabel,removeLabel,name,x,y,
				add,remove,removeLocation,confirm,confirm2,update);
		
		add.setOnAction(e -> {
			//If one of the boxes is empty, print error message
			if (name.getText().matches("") || x.getText().matches("")
					|| y.getText().matches("")) {
				confirm.setText("Invalid name/coordinates");
			}
			//Prevents letters/characters being used as weights
			else if (!x.getText().matches("[^\\d]") || !y.getText().matches("[^\\d]")) {
				confirm.setText("Invalid name/coordinates");
			}
			//Adds new food item
			else if (name.getText() != null && x.getText() != null && y.getText() != null) {
				Location l = new Location(name.getText(), Integer.parseInt(x.getText()),
						Integer.parseInt(y.getText()));
				items.add(l.getName());
				locations.setItems(items);
				confirm.setText(l.getName() + " added!");
				name.clear();
				x.clear();
				y.clear();
			}
		});
		
//		update.setOnAction(e -> {
//			if (name.getText().matches("") || x.getText().matches("")
//					|| y.getText().matches("")) {
//				confirm.setText("Invalid name/coordinates");
//			}
//			else if (!x.getText().matches("[0-9]*") || !y.getText().matches("[0-9]*")) {
//				confirm.setText("Invalid name/coordinates");
//			}
//			else if (name.getText() != null && x.getText() != null && y.getText() != null) {
//				xml.edit("Student Activity Center", "xCoord", "0");
//				confirm.setText("Student Activity Center" + " updated!");
//				name.clear();
//				x.clear();
//				y.clear();
//			}
//		});
		
		remove.setOnAction(e -> {
			//Can't remove nothing
			if (removeLocation.getText().matches("")) {
				confirm2.setText("Invalid food item name");
			}
			//Search for food item
			else {
				//Food item found
				if(items.contains(removeLocation.getText())) {
					items.remove(removeLocation.getText());
					confirm2.setText("Removed " + removeLocation.getText());
				} else {
					confirm2.setText("Location not found");
				}
			}
		});
		
		back.setOnAction(e -> {
			mm.menu.setScene(mm.menuWindow);
		});
		
		title.setStyle("-fx-font-size:20");
		page.setSpacing(10);
		page.getChildren().addAll(title,locations,grid,back);
		
		return page;
	}
}
