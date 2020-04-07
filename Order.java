public class Order{
    int id;
    String name;
    Meal meals;
    Location destination;

    public Order(int id, String name, Meal meal, Location destination){
        this.id = id;
        this.name = name;
        this.meals = meal;
        this.destination = destination;
    }
}
