public class Order{
    int id;
    String name;
    Meal meals;
    Location destination;
    double probability;

    public Order(int id, String name, Meal meal, Location destination, double probability){
        this.id = id;
        this.name = name;
        this.meals = meal;
        this.destination = destination;
        this.probability = probability;
    }
}