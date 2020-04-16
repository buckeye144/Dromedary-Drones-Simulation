import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Meal{
    String name;
    double probability; //Probability of Meal appearing in an order from 0 to 1
    ObservableList<FoodItem> items;

	public Meal(String name, ObservableList<FoodItem> meal0, double probability) {
		this.name = name;
        this.probability = probability;
        this.items= FXCollections.observableArrayList();
        this.items.addAll(meal0);
	}
}