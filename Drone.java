import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Drone{
    int speed; //MPH
    int maxCapacity; //ounces
    int flightTime; //minutes
    int turnAround; //minutes
    double dropOffTime; //minutes

    public Drone(){
        this.speed = 25;
        this.maxCapacity = 192;
        this.flightTime = 20;
        this. turnAround = 3;
        this.dropOffTime = .5;
    }

    public ArrayList<ArrayList<Order>> FIFO(List<Order> orders){
        ArrayList<ArrayList<Order>> orderGrouping = new ArrayList<>();
        ArrayList<Order> nthGroup = new ArrayList<>();
        int filledCapacity = 0;

        for(int i = 0; i < orders.size(); i++){
            while(i < orders.size() && this.maxCapacity > filledCapacity + OrderCapacity(orders.get(i))){
                filledCapacity += OrderCapacity(orders.get(i));
                nthGroup.add(orders.get(i));
                i++;
            }
            i--; //adjust back to not skip over items
            filledCapacity = 0;
            orderGrouping.add(new ArrayList<Order>(nthGroup));
            nthGroup.clear();
        }
        return orderGrouping;
    }

    public ArrayList<ArrayList<Order>> knapsacking(List<Order> orders){
        ArrayList<ArrayList<Order>> orderGrouping = new ArrayList<>();
        ArrayList<Order> nthGroup = new ArrayList<>();
        HashSet<Integer> packedIndexes = new HashSet<>(); //list of all the items that have been packed out of order
        int filledCapacity = 0;

        for(int i = 0; i < orders.size(); i++){
            if(packedIndexes.contains(i)){
                continue; //we already packed this item
            }
            while(i < orders.size() && this.maxCapacity > filledCapacity + OrderCapacity(orders.get(i))){
                filledCapacity += OrderCapacity(orders.get(i));
                nthGroup.add(orders.get(i));
                i++;
            }
            i--; //adjust back to not skip over items
            //jump over items to find a item that will fit, we already know i doesn't fit
            int skippedWeight = OrderCapacity(orders.get(i));
            for(int j = i + 1; j < orders.size() && skippedWeight < this.maxCapacity; j++){
                if(OrderCapacity(orders.get(j)) + filledCapacity < this.maxCapacity){
                    filledCapacity += OrderCapacity(orders.get(j));
                    nthGroup.add(orders.get(j));
                    packedIndexes.add(j);
                }
                else{
                    skippedWeight += OrderCapacity(orders.get(j));
                }
            }
            filledCapacity = 0;
            orderGrouping.add(new ArrayList<Order>(nthGroup));
            nthGroup.clear();
        }
        return orderGrouping;
    }

    public int OrderCapacity(Order order){
        int size = 0;
        for(int i = 0; i < order.meals.items.size(); i++){
            size += order.meals.items.get(i).weight;
        }
        return size;
    }
}