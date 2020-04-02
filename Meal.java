import java.util.ArrayList;
import java.util.List;

public class Meal{
    String name;
    ArrayList<FoodItem> items;

    public Meal(String name){
        this.name = name;
        this.items= new ArrayList<>();
    }

    public Meal(String name, FoodItem item){
        this.name = name;
        this.items= new ArrayList<>();
        items.add(item);
    }

    public Meal(String name, List<FoodItem> items){
        this.name = name;
        this.items= new ArrayList<>();
        this.items.addAll(items);
    }
}