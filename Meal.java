import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Meal{
    String name;
    int probability;
    ObservableList<FoodItem> items;

    public Meal(String name, int probability){
        this.name = name;
        this.probability = probability;
        this.items= FXCollections.observableArrayList();
    }

    public Meal(String name, FoodItem item, int probability){
        this.name = name;
        this.probability = probability;
        this.items= FXCollections.observableArrayList();
        items.add(item);
    }

    public Meal(String name, ObservableList<FoodItem> items, int probability){
        this.name = name;
        this.probability = probability;
        this.items= FXCollections.observableArrayList();
        this.items.addAll(items);
    }
}