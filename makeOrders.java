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
	
	public makeOrders() {
		//Make the default meals
		meals = FXCollections.observableArrayList();
		foodList = new ArrayList<FoodItem>();
		FoodItem hamburger = new FoodItem("hamburger", 6);
        FoodItem fries = new FoodItem("fries", 4);
        FoodItem drink = new FoodItem("drink", 14);
        ObservableList<FoodItem> meal0 = FXCollections.observableArrayList();
        meal0.add(hamburger);
        meal0.add(fries);
        meal0.add(drink);
        typical = new Meal("typical", meal0, .50);
        ObservableList<FoodItem> meal1 = FXCollections.observableArrayList();
        meal1.add(hamburger);
        meal1.add(hamburger);
        meal1.add(fries);
        meal1.add(drink);
        twoBurgerMeal = new Meal("twoBurgerMeal", meal1, .20);
        ObservableList<FoodItem> meal2 = FXCollections.observableArrayList();
        meal2.add(hamburger);
        meal2.add(fries);
        burgerFries = new Meal("burgerFries", meal2, .15);
        ObservableList<FoodItem> meal3 = FXCollections.observableArrayList();
        meal3.add(hamburger);
        meal3.add(hamburger);
        meal3.add(fries);
        twoBurgers = new Meal("twoBurgers", meal3, .10);
        ObservableList<FoodItem> meal4 = FXCollections.observableArrayList();
        meal4.add(fries);
        justFries = new Meal("justFries", meal4, .5);

        foodList.add(hamburger);
        foodList.add(fries);
        foodList.add(drink);

        meals.add(typical);
        meals.add(twoBurgerMeal);
        meals.add(burgerFries);
        meals.add(twoBurgers);
        meals.add(justFries);
	     
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
        int[] shiftOrders = {38, 45, 60, 30};
        for(int i = 0; i < shiftOrders.length; i++){
            int minuteMarker = 0;
            for(int j = 0; j < shiftOrders[i]; j++){
                double orderNum = r.nextDouble();
                double prevProbability = 0.0;
                for(int o = 0; o < possOrders.size(); o++){
                    if(orderNum < possOrders.get(o).meals.probability + prevProbability) {
                        Order temp = new Order(possOrders.get(o));
                        temp.timeIn = i * 60 + minuteMarker;
                        minuteMarker += r.nextInt(Math.max((int)(60 /shiftOrders[i]),2));
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
                //check if the whole queue can fit on the drone, if so send it all
                //TODO: turn around time is the time between when the order is generated and when the drone leaves the order’s delivery point on its flight
                if(queueSize <= drone.maxCapacity){
                    travelTime = tc.time(queue, true);
                    for(int i = 0; i < queue.size(); i++){
                        turnAroundTime.add((double)(curMin + travelTime - queue.get(i).timeIn));
                    }
                    queue.clear();
                    curMin += travelTime;
                }
                else{//split up the queu by FIFO and delver it all
                    ArrayList<ArrayList<Order>> megaQueue = drone.FIFO(queue);
                    //deleiver all the batches
                    for(int smallQueue = 0; smallQueue < megaQueue.size(); smallQueue++){
                        travelTime = tc.time(megaQueue.get(smallQueue), true);
                        for(int i = 0; i < megaQueue.get(smallQueue).size(); i++){
                            turnAroundTime.add((double)(curMin + travelTime - megaQueue.get(smallQueue).get(i).timeIn));
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
//        System.out.println("Turn Around Times (FIFO): ");
//        for(int i = 0; i < Math.min(turnAroundTime.size(), orderList.size()); i++){
//            System.out.println("\tTime in: " + orderList.get(i).timeIn + "\n\tTOT: " + turnAroundTime.get(i));
//        }
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
            //TODO: check and make sure all the orders are delivered
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
                if(queueSize <= drone.maxCapacity){
                    travelTime = tc.time(queue, true);
                    for(int i = 0; i < queue.size(); i++){
                        turnAroundTime.add((double)(curMin + travelTime - queue.get(i).timeIn));
                    }
                    queue.clear();
                    curMin += travelTime;
                }
                else{//split up the queu by FIFO and delver it all
                    ArrayList<ArrayList<Order>> megaQueue = drone.knapsacking(queue);
                    //deleiver all the batches
                    for(int smallQueue = 0; smallQueue < megaQueue.size(); smallQueue++){
                        travelTime = tc.time(megaQueue.get(smallQueue), true);
                        for(int i = 0; i < megaQueue.get(smallQueue).size(); i++){
                            turnAroundTime.add((double)(curMin + travelTime - megaQueue.get(smallQueue).get(i).timeIn));
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
//        System.out.println("Turn Around Times (Knapsacking): ");
//        for(int i = 0; i < Math.min(turnAroundTime.size(), orderList.size()); i++){
//            System.out.println("\tTime in: " + orderList.get(i).timeIn + "\n\tTOT: " + turnAroundTime.get(i));
//        }
        return turnAroundTime;
    }
}