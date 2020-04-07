import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Meal{
    String name;
    int probability;
    ObservableList<FoodItem> items;

	public Meal(String name, ObservableList<FoodItem> meal0, int probability) {
		this.name = name;
        this.probability = probability;
        this.items= FXCollections.observableArrayList();
        this.items.addAll(items);
	}
}