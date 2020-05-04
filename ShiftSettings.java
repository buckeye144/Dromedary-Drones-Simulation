import java.io.IOException;
import java.util.ArrayList;
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

public class ShiftSettings {
	
	MainMenu mm;
	ObservableList<Integer> orderList;
	XML xml = new XML();
	int previous;

	public VBox shiftSettings(MainMenu mm) {
		
		this.mm = mm;
		VBox pane = new VBox();
		GridPane shifts = new GridPane();
		GridPane grid2 = new GridPane();
		ListView<Integer> orders = new ListView<Integer>();
		orderList = FXCollections.observableArrayList();
		Label title = new Label("List of shifts and number of orders");
		Label orderLabel = new Label("Add or change a shift");
		Label confirm = new Label("");
		Label confirm2 = new Label("");
		Label order = new Label("Orders: ");
		Button back = new Button("Back");
		Button remove = new Button("Remove");
		Button add = new Button("Add");
		Button update = new Button("Update");
		TextField orderNumber = new TextField();
		
		loadShifts();
		orders.setItems(orderList);
		
		orders.getSelectionModel().selectedItemProperty().addListener(e ->{
			int index = orders.getSelectionModel().getSelectedIndex();
			try {
				orderNumber.setText(Integer.toString(orderList.get(index)));				
			} catch(Exception e1) {
			}
		});
		
		shifts.setPadding(new Insets(10, 10, 10, 10));
		shifts.setVgap(5);
		shifts.setHgap(5);
		
		grid2.setPadding(new Insets(10, 10, 10, 10));
		grid2.setVgap(5);
		grid2.setHgap(5);
		
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(orders, 0, 1);
		GridPane.setConstraints(remove, 0, 2);
		GridPane.setConstraints(confirm, 0, 3);
		GridPane.setConstraints(grid2, 1, 1);
		
		GridPane.setConstraints(orderLabel, 1, 1);
		GridPane.setConstraints(orderNumber, 1, 2);
		GridPane.setConstraints(add, 2, 2);
		GridPane.setConstraints(update, 3, 2);
		GridPane.setConstraints(order, 0, 2);
		GridPane.setConstraints(confirm2, 1, 3);
		
		
		grid2.getChildren().addAll(orderLabel,orderNumber,add,update,confirm2,order);
		
		title.setStyle("-fx-font-size:20");
		orderLabel.setStyle("-fx-font-size:16");
		back.setStyle("-fx-font-size:16");
		back.setMaxWidth(150);
		orderNumber.setStyle("-fx-font-size:16");
		orderNumber.setPromptText("Enter number of orders");
		orderNumber.setMaxWidth(200);
		orderNumber.setMinHeight(30);
		order.setStyle("-fx-font-size:16");
		orders.setMaxHeight(200);
		orders.setMinWidth(300);
		orders.setStyle("-fx-font-size:20");
		
		shifts.getChildren().addAll(title,orders,remove,confirm,grid2);
		
		pane.setSpacing(10);
		pane.getChildren().addAll(shifts,back);
		
		add.setOnAction(e -> {
			if(!orderNumber.getText().matches("[0-9]*")) {
				confirm2.setText("Invalid number");
			} else {
				xml.addShift(Integer.parseInt(orderNumber.getText()));
				loadShifts();
				confirm2.setText("Added shift");
				orderNumber.clear();
			}
		});
		
		update.setOnAction(e -> {
			int index = orders.getSelectionModel().getSelectedIndex();
			if(orders.getSelectionModel().isEmpty()) {
				confirm2.setText("Nothing selected to update");
			}
			else if(!orderNumber.getText().matches("[0-9]*")) {
				confirm2.setText("Invalid number");
			} else {
				previous = (int) (mm.os.shiftOrders.get(index));
				xml.updateShift(Integer.parseInt(orderNumber.getText()), orderList.get(index));
				loadShifts();
				confirm2.setText("Updated");
				orderNumber.clear();
			}
			
		});
		
		//TODO: FIX
		remove.setOnAction(e -> {
			if(orders.getSelectionModel().isEmpty()) {
				confirm2.setText("Nothing selected");
			} else {
				int index = orders.getSelectionModel().getSelectedIndex();
				confirm.setText("Removed shift " + (index + 1) + ": " + orderList.get(index) + " orders");
				xml.removeShift(orderList.get(index), "orders", "orders", "shifts.xml");
				orderList.remove(index);
				mm.os.shiftOrders.remove(index);
				orders.getSelectionModel().clearSelection();
				orderNumber.clear();
			}
			
		});
		
		back.setOnAction(e -> {
			mm.menu.setScene(mm.menuWindow);
		});
		
		return pane;
	}
	
	private void loadShifts(){
		orderList.clear();
		mm.os.shiftOrders.clear();
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse("shifts.xml");
			
			//Get a list of shifts
			NodeList orders = doc.getElementsByTagName("orders");
			for(int j = 0; j < orders.getLength(); j++) {
				Element order = (Element) orders.item(j);
				int number = Integer.parseInt(order.getTextContent());
				mm.os.shiftOrders.add(number);
				orderList.add(number);
			}
		} catch (ParserConfigurationException | 
				SAXException | 
				IOException tfe) {
			tfe.printStackTrace();
		}
	}
}
