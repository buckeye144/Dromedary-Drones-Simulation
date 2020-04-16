import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class LocationSettings {
	
	ListView<String> locations;
	TextField name;
	TextField x;
	TextField y;
	ObservableList<String> items;
	XML xml;

	public VBox locations(MainMenu mm, Map map) {
		
		xml = new XML();
		GridPane grid = new GridPane();
		GridPane grid2 = new GridPane();
		VBox page = new VBox();
		locations = new ListView<String>();
		items = FXCollections.observableArrayList();
		name = new TextField();
		x = new TextField();
		y = new TextField();
		TextField removeLocation = new TextField();
		Label title = new Label("List of current locations");
		Label addLabel = new Label("Add/edit a location");
		Label removeLabel = new Label("Remove a location");
		Label confirm = new Label();
		Label confirm2 = new Label();
		Label locationsName = new Label("Name: ");
		Label locationsX = new Label("X Coordinate: ");
		Label locationsY = new Label("Y Coordinate: ");
		Button add = new Button("Add");
		Button remove = new Button("Remove");
		Button update = new Button("Update");
		Button back = new Button("Back");
		
		//Button/text field styles
		addLabel.setStyle("-fx-font-size:16");
		name.setPromptText("Enter name of location");
		name.setStyle("-fx-font-size:16");
		name.setMaxWidth(200);
		name.setMinHeight(30);
		x.setPromptText("Enter X coordinate");
		x.setStyle("-fx-font-size:16");
		x.setMaxWidth(200);
		x.setMinHeight(30);
		y.setPromptText("Enter Y coordinate");
		y.setStyle("-fx-font-size:16");
		y.setMaxWidth(200);
		y.setMinHeight(30);
		removeLabel.setStyle("-fx-font-size:16");
		removeLocation.setPromptText("Enter name of location");
		removeLocation.setStyle("-fx-font-size:16");
		removeLocation.setMinWidth(200);
		removeLocation.setMaxHeight(30);
		locations.setStyle("-fx-font-size:20");
		locations.setItems(items);
		locations.setMinWidth(500);
		locations.setMinHeight(mm.menu.getHeight() / 2);
		back.setStyle("-fx-font-size:16");
		back.setMaxWidth(150);
		
		for(int i = 0; i < map.waypoints.size(); i++) {
			items.add(map.waypoints.get(i).getName());
		}
		
		locations.getSelectionModel().selectedItemProperty().addListener(e ->{
			int index = locations.getSelectionModel().getSelectedIndex();
			try {
				name.setText(map.waypoints.get(index).getName());
				x.setText(Integer.toString(map.waypoints.get(index).getX()));
				y.setText(Integer.toString(map.waypoints.get(index).getY()));
			} catch (Exception e1) {
			}
		});
		
		locationsName.setStyle("-fx-font-size:20");
		locationsX.setStyle("-fx-font-size:20");
		locationsY.setStyle("-fx-font-size:20");
		
		grid2.setPadding(new Insets(10, 10, 10, 10));
		grid2.setVgap(5);
		grid2.setHgap(5);
		GridPane.setConstraints(addLabel, 0, 0);
		GridPane.setConstraints(name, 0, 1);
		GridPane.setConstraints(x, 0, 2);
		GridPane.setConstraints(y, 0, 3);
		GridPane.setConstraints(confirm, 0, 4);
		GridPane.setConstraints(removeLabel, 0, 6);
		GridPane.setConstraints(removeLocation, 0, 7);
		GridPane.setConstraints(confirm2, 0, 8);
		GridPane.setConstraints(add, 1, 2);
		GridPane.setConstraints(update, 1, 3);
		GridPane.setConstraints(remove, 1, 7);
		grid2.getChildren().addAll(addLabel,name,x,y,confirm,add,update,
				removeLabel,removeLocation,confirm2,remove);
		
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(locations, 0, 1);
		GridPane.setConstraints(grid2, 1, 1);
		grid.getChildren().addAll(title,locations,grid2);
		
		//TODO: Implement XML adding
		//Currently a temporary change, if program shuts down, changes are lost
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
			//Adds new location item
			else if (x.getText().matches("[^//d]") || x.getText().contains("-") ||
					y.getText().matches("[^//d]") || y.getText().contains("-")) {
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
		
		//Changes not lost
		update.setOnAction(e -> {
			if (name.getText().matches("") || x.getText().matches("")
					|| y.getText().matches("")) {
				confirm.setText("Invalid name/coordinates");
			}
			else if (!x.getText().matches("[0-9]*") || !y.getText().matches("[0-9]*")) {
				confirm.setText("Invalid name/coordinates");
			}
			else if (name.getText() != null && x.getText() != null && y.getText() != null) {
				String current = locations.getSelectionModel().selectedItemProperty().get();
				int index = locations.getSelectionModel().getSelectedIndex();
				xml.edit(current, name.getText(), x.getText(), y.getText());
				map.waypoints.get(index).setName(name.getText());
				map.waypoints.get(index).setX(Integer.parseInt(x.getText()));
				map.waypoints.get(index).setY(Integer.parseInt(y.getText()));
				items.clear();
				for(int i = 0; i < map.waypoints.size(); i++) {
					items.add(map.waypoints.get(i).getName());
				}
				confirm.setText("Updated!");
				name.clear();
				x.clear();
				y.clear();
			}
		});
		
		//Changes are lost
		remove.setOnAction(e -> {
			//Can't remove nothing
			if (removeLocation.getText().matches("")) {
				confirm2.setText("Invalid location name");
			}
			//Search for location
			else {
				//Found location
				for(int i = 0; i < map.waypoints.size(); i++) {
					if(map.waypoints.get(i).getName().matches(removeLocation.getText())) {
						map.waypoints.remove(i);
						items.clear();
						for(int j = 0; j < map.waypoints.size(); j++) {
							items.add(map.waypoints.get(j).getName());
						}
						confirm2.setText("Removed " + removeLocation.getText());
						removeLocation.clear();
						break;
					} else {
						confirm2.setText("Location not found");
					}
				}
			}
		});
		
		back.setOnAction(e -> {
			mm.menu.setScene(mm.menuWindow);
		});
		
		title.setStyle("-fx-font-size:20");
		page.setSpacing(10);
		page.getChildren().addAll(grid,back);
		
		return page;
	}
}
