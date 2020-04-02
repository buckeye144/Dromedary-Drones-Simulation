import java.util.ArrayList;
import java.util.List;

public class Drone{
    int speed; //MPH
    int maxCapacity; //ounces
    int flightTime; //minutes
    int turnAround; //minutes
    double dropOffTime; //minutes

    public Drone(){
        this.speed = 20;
        this.maxCapacity = 192;
        this.flightTime = 20;
        this. turnAround = 3;
        this.dropOffTime = .5;
    }

    public ArrayList<ArrayList<Order>> FIFOTime(List<Order> orders){
        ArrayList<ArrayList<Order>> orderGrouping = new ArrayList<>();
        ArrayList<Order> nthGroup = new ArrayList<>();
        int filledCapacity = 0;

        for(int i = 0; i < orders.size(); i++){
            for(;i < orders.size() && this.maxCapacity > filledCapacity + OrderCapacity(orders.get(i)); i++){
                filledCapacity += OrderCapacity(orders.get(i));
                nthGroup.add(orders.get(i));
            }
            filledCapacity = 0;
            orderGrouping.add(new ArrayList<Order>(nthGroup));
            nthGroup.clear();
        }
        return orderGrouping;
    }

    private int OrderCapacity(Order order){
        int size = 0;
        for(int i = 0; i < order.meals.items.size(); i++){
            size += order.meals.items.get(i).weight;
        }
        return size;
    }
}