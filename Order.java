public class Order{
    int id;
    String name;
    Meal meals;
    Location destination;
    double timeIn;
    double timeOut;

    public Order(int id, String name, Meal meal, Location destination, double ti){
        this.id = id;
        this.name = name;
        this.meals = meal;
        this.destination = destination;
        this.timeIn = ti;
    }
    
    public Order(Order o) {
    	this.id = o.id;
    	this.name = o.name;
    	this.meals = o.meals;
    	this.timeIn = o.timeIn;
    	this.timeOut = o.timeOut;
    	this.destination = o.destination;
    }
}
