import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Meal{
    String name;
    ObservableList<FoodItem> items;

    public Meal(String name){
        this.name = name;
        this.items= FXCollections.observableArrayList();
    }

    public Meal(String name, FoodItem item){
        this.name = name;
        this.items= FXCollections.observableArrayList();
        items.add(item);
    }

    public Meal(String name, ObservableList<FoodItem> items){
        this.name = name;
        this.items= FXCollections.observableArrayList();
        this.items.addAll(items);
    }
}