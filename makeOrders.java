import java.util.ArrayList;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class makeOrders{
	Meal twoBurgerMeal;
	Meal typical;
	Meal burgerFries;
    Meal twoBurgers;
    Meal justFries;
	ObservableList<Meal> meals;
	ArrayList<Order> orderList;
	ArrayList<FoodItem> foodList;
	Drone drone;
	ArrayList<Integer> shiftOrders;
	
	public makeOrders() {
		meals = FXCollections.observableArrayList();
		foodList = new ArrayList<FoodItem>();
		shiftOrders = new ArrayList<Integer>();
	}
	
    public void simulation(Map map) {
        //standard location
        Location defaultLoc = new Location("null", 0, 0);
        
        
        //Put the meals into orders as they are probable
        ArrayList<Order> possOrders = new ArrayList<>();
        //add all the possible meals to and possOrder list
        for(int i = 0; i < meals.size(); i++){
            possOrders.add(new Order(0, "admin", meals.get(i), defaultLoc, 0.0));
        }
        
        //Put the orders into the list of all orders for the day
        orderList = new ArrayList<>();
        
        //add the orders to the list as they are listed
        //1st hour
        Random r = new Random();
        for(int i = 0; i < shiftOrders.size(); i++){
            int minuteMarker = 0;
            for(int j = 0; j < shiftOrders.get(i); j++){
                double orderNum = r.nextDouble();
                double prevProbability = 0.0;
                for(int o = 0; o < possOrders.size(); o++){
                    if(orderNum < possOrders.get(o).meals.probability + prevProbability) {
                        Order temp = new Order(possOrders.get(o));
                        temp.timeIn = i * 60 + minuteMarker;
                        minuteMarker += r.nextInt(Math.max((int)(60 /shiftOrders.get(i)),2));
                        if(minuteMarker > 59) minuteMarker = 59;
                        temp.destination = map.getRandom();
                        orderList.add(temp);
                        break;
                    }
                    prevProbability += possOrders.get(o).meals.probability;
                }
            }
        }
    }
        
    //Drone deliver groupings with FIFO
    public ArrayList<Double> FIFO() {
        drone = new Drone();
        ArrayList<Double> turnAroundTime = new ArrayList<>();
        ArrayList<Order> queue = new ArrayList<>();
        TimeCalc tc = new TimeCalc();
        int orderNum = 0;//tracks which orders have been shipped
        double travelTime = 0.0;
        //go through the order list minute by minute and once you have at least two orders send the drone out
        int curMin = 0;
        while(orderNum < orderList.size()){ //how many minutes there are in the shift
            while(orderNum < orderList.size() && orderList.get(orderNum).timeIn < curMin){ //add all the orders that have come in before the current time
            	queue.add(orderList.get(orderNum));
                orderNum++;
            }
            
            if(queue.size() > 1){ //send out the drone
                //calculate how large the queue is
                int queueSize = 0;
                for(int i = 0; i < queue.size(); i++){
                    queueSize += drone.OrderCapacity(queue.get(i));
                }
//                System.out.println("FIFO " + queueSize);
                //check if the whole queue can fit on the drone, if so send it all
                if(queueSize <= drone.maxCapacity * .95){
                    travelTime = tc.time(queue, true);
                    for(int i = 0; i < queue.size(); i++){
                        turnAroundTime.add((double)(curMin + queue.get(i).timeOut - queue.get(i).timeIn));
                    }
                    queue.clear();
                    curMin += travelTime;
                }
                else{//split up the queue by FIFO and delver it all
                    ArrayList<ArrayList<Order>> megaQueue = drone.FIFO(queue);
                    //deleiver all the batches
                    for(int smallQueue = 0; smallQueue < megaQueue.size(); smallQueue++){
                        travelTime = tc.time(megaQueue.get(smallQueue), true);
                        for(int i = 0; i < megaQueue.get(smallQueue).size(); i++){
                            turnAroundTime.add((double)(curMin + megaQueue.get(smallQueue).get(i).timeOut - megaQueue.get(smallQueue).get(i).timeIn));
                        }
                        curMin += travelTime;
                    }
                    queue.clear();
                }
                
            }
            else{ //queue isn't big enough
                curMin++;
            }
        }
        return turnAroundTime;
    }
        
        
    //Knapsack calculation
    public ArrayList<Double> KnapSack(){
        drone = new Drone();
        ArrayList<Double> turnAroundTime = new ArrayList<>();
        ArrayList<Order> queue = new ArrayList<>();
        TimeCalc tc = new TimeCalc();
        int orderNum = 0;//tracks which orders have been shipped
        double travelTime = 0.0;
        //go through the order list minute by minute and once you have at least two orders send the drone out
        int curMin = 0;
        while(orderNum < orderList.size()){ //how many minutes there are in the shift
            while(orderNum < orderList.size() && orderList.get(orderNum).timeIn < curMin){ //add all the orders that have come in before the current time
                queue.add(orderList.get(orderNum));
                orderNum++;
            }
            if(queue.size() > 1){ //send out the drone
                //calculate how large the queue is
                int queueSize = 0;
                for(int i = 0; i < queue.size(); i++){
                    queueSize += drone.OrderCapacity(queue.get(i));
                }
                //check if the whole queue can fit on the drone, if so send it all
                if(queueSize <= drone.maxCapacity * .95){
                    travelTime = tc.time(queue, true);
                    for(int i = 0; i < queue.size(); i++){
                        turnAroundTime.add((double)(curMin + queue.get(i).timeOut - queue.get(i).timeIn));
                    }
                    queue.clear();
                    curMin += travelTime;
                }
                else{//split up the queue by knapsack and delver it all
                    ArrayList<ArrayList<Order>> megaQueue = drone.knapsacking(queue);
                    //deleiver all the batches
                    for(int smallQueue = 0; smallQueue < megaQueue.size(); smallQueue++){
                        travelTime = tc.time(megaQueue.get(smallQueue), true);
                        for(int i = 0; i < megaQueue.get(smallQueue).size(); i++){
                            turnAroundTime.add((double)(curMin + megaQueue.get(smallQueue).get(i).timeOut - megaQueue.get(smallQueue).get(i).timeIn));
                        }
                        curMin += travelTime;
                    }
                    queue.clear();
                }
                
            }
            else{ //queue isn't big enough
                curMin++;
            }
        }
        return turnAroundTime;
    }
}