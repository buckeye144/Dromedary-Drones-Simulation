import java.util.ArrayList;
import java.util.List;

public class Order{
    int id;
    ArrayList<Meal> meals;
    Location destination;
    float probability;

    public Order(int id, List<Meal> meals, Location destination, float probability){
        this.id = id;
        this.meals = new ArrayList<>();
        this.meals.addAll(meals);
        this.probability = probability;
    }
}