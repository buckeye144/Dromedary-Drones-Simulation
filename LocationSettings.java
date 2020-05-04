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
	
	ListView<Location> locations;
	TextField name;
	TextField x;
	TextField y;
	ObservableList<Location> items;
	XML xml;
	Map map;
	String sameLocation;

	public VBox locations(MainMenu mm, Map map) {
		
		this.map = map;
		xml = new XML();
		GridPane grid = new GridPane();
		GridPane grid2 = new GridPane();
		VBox page = new VBox();
		locations = new ListView<Location>();
		items = FXCollections.observableArrayList();
		name = new TextField();
		x = new TextField();
		y = new TextField();
		Label title = new Label("List of current locations");
		Label addLabel = new Label("Add or change a location");
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
		locations.setStyle("-fx-font-size:20");
		locations.setItems(items);
		locations.setMinWidth(500);
		locations.setMinHeight(mm.menu.getHeight() / 2);
		back.setStyle("-fx-font-size:16");
		back.setMaxWidth(150);
		
		for(int i = 0; i < map.waypoints.size(); i++) {
			items.add(map.waypoints.get(i));
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
		GridPane.setConstraints(locationsName, 0, 1);
		GridPane.setConstraints(locationsX, 0, 2);
		GridPane.setConstraints(locationsY, 0, 3);
		GridPane.setConstraints(addLabel, 1, 0);
		GridPane.setConstraints(name, 1, 1);
		GridPane.setConstraints(x, 1, 2);
		GridPane.setConstraints(y, 1, 3);
		GridPane.setConstraints(confirm, 1, 4);
		GridPane.setConstraints(removeLabel, 0, 6);
		GridPane.setConstraints(confirm2, 0, 8);
		GridPane.setConstraints(add, 2, 3);
		GridPane.setConstraints(update, 3, 3);
		GridPane.setConstraints(remove, 0, 7);
		grid2.getChildren().addAll(locationsName,locationsY,locationsX,addLabel,name,
				x,y,confirm,add,update,removeLabel,confirm2,remove);
		
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(locations, 0, 1);
		GridPane.setConstraints(grid2, 1, 1);
		grid.getChildren().addAll(title,locations,grid2);
		
		//Adds a new location
		add.setOnAction(e -> {
			//If one of the boxes is empty, print error message
			if (name.getText().matches("")) {
				confirm.setText("Cannot insert blank name");
			}
			else if(x.getText().matches("") || y.getText().matches("")) {
				confirm.setText("Invalid coordinates");
			}
			//Prevents letters/characters being used as weights
			else if (!x.getText().matches("-?[0-9]*") || !y.getText().matches("-?[0-9]*")) {
				confirm.setText("Invalid name/coordinates");
			}
			else if(checkForExistingName(name.getText())) {
				confirm.setText("Location name already exists");
			}
			else if(checkForExistingCoordinates(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()))) {
				confirm.setText("Same coordinates as " + sameLocation);
			}
			//Adds new location item
			else if (x.getText().matches("[0-9]*") || x.getText().contains("-") ||
					y.getText().matches("[0-9]*") || y.getText().contains("-")) {
				Location l = new Location(name.getText(), Integer.parseInt(x.getText()),
						Integer.parseInt(y.getText()));
				xml.addLocation(l);
				map.waypoints.add(l);
				items.add(l);
				locations.setItems(items);
				confirm.setText(l.getName() + " added!");
				name.clear();
				x.clear();
				y.clear();
			}
			else {
				System.out.println("something failed");
			}
		});
		
		//Updates an existing location
		update.setOnAction(e -> {
			if(locations.getSelectionModel().isEmpty()) {
				confirm.setText("Please select something to update");
			} else {
				if (name.getText().matches("")) {
					confirm.setText("Cannot insert blank name");
				}
				else if(x.getText().matches("") || y.getText().matches("")) {
					confirm.setText("Invalid coordinates");
				}
				else if (!x.getText().matches("-?[0-9]*") || !y.getText().matches("-?[0-9]*")) {
					confirm.setText("Invalid name/coordinates");
				}
				else if(checkForExistingNameUpdating(name.getText(), locations.getSelectionModel().getSelectedIndex())) {
					confirm.setText("Location name already exists");
				}
				else if(checkForExistingCoordinates(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()))) {
					confirm.setText("Same coordinates as " + sameLocation);
				}
				else if(locations.getSelectionModel().isEmpty()) {
					confirm.setText("Please select something to update");
				}
				else {
					String current = locations.getSelectionModel().selectedItemProperty().get().getName();
					int index = locations.getSelectionModel().getSelectedIndex();
					xml.updateLocation(current, name.getText(), x.getText(), y.getText());
					map.waypoints.get(index).setName(name.getText());
					map.waypoints.get(index).setX(Integer.parseInt(x.getText()));
					map.waypoints.get(index).setY(Integer.parseInt(y.getText()));
					items.clear();
					for(int i = 0; i < map.waypoints.size(); i++) {
						items.add(map.waypoints.get(i));
					}
					confirm.setText("Updated!");
					name.clear();
					x.clear();
					y.clear();
				}
			}
		});
		
		//Removes a location
		remove.setOnAction(e -> {
			if(locations.getSelectionModel().isEmpty()) {
				confirm2.setText("Nothing selected");
			} else {
				int index = locations.getSelectionModel().getSelectedIndex();
				confirm2.setText("Removed " + items.get(index));
				xml.remove(items.get(index).getName(), "location", "locName", "locations.xml");
				map.removeLocation(items.get(index).getName());
				items.remove(index);
				locations.getSelectionModel().clearSelection();
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
	
	private boolean checkForExistingName(String toSearchFor) {
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).getName().matches(toSearchFor)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkForExistingNameUpdating(String toSearchFor, int index) {
		for(int i = 0; i < items.size(); i++) {
			if(i != index) {
				if(items.get(i).getName().matches(toSearchFor)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkForExistingCoordinates(int x, int y) {
		for(int i = 0; i < map.waypoints.size(); i++) {
			if(map.waypoints.get(i).getX() == x && 
			   map.waypoints.get(i).getY() == y) {
				this.sameLocation = map.waypoints.get(i).getName();
				return true;
			}
		}
		return false;
	}
	
	
}
