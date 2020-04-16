import java.util.ArrayList;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class makeOrders{
	Meal twoBurgerMeal;
	Meal typical;
	Meal burgerFries;
	Meal twoBurgers;
	ObservableList<Meal> meals;
	ArrayList<FoodItem> foodList;
	ArrayList<Order> orderList;
	ArrayList<Order> possOrders;
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
        typical = new Meal("typical", meal0, .55);
        ObservableList<FoodItem> meal1 = FXCollections.observableArrayList();
        meal1.add(hamburger);
        meal1.add(hamburger);
        meal1.add(fries);
        meal1.add(drink);
        twoBurgerMeal = new Meal("twoBurgerMeal", meal1, .10);
        ObservableList<FoodItem> meal2 = FXCollections.observableArrayList();
        meal2.add(hamburger);
        meal2.add(fries);
        burgerFries = new Meal("burgerFries", meal2, .20);
        ObservableList<FoodItem> meal3 = FXCollections.observableArrayList();
        meal3.add(hamburger);
        meal3.add(hamburger);
        meal3.add(fries);
        twoBurgers = new Meal("twoBurgers", meal3, .15);
	    meals.add(typical);
	    meals.add(twoBurgerMeal);
	    meals.add(burgerFries);
	    meals.add(twoBurgers);
	    foodList.add(hamburger);
	    foodList.add(fries);
	    foodList.add(drink);
	}
    public void simulation(Map map) {
    	
        //standard location
        Location defaultLoc = new Location("null", 0, 0);
        
        Order order1 = new Order(1, "Jonathan", typical, defaultLoc,0.0);
        Order order2 = new Order(2, "Nathan", twoBurgers, defaultLoc,0.0);
        Order order3 = new Order(3, "Daniel", burgerFries, defaultLoc,0.0);
        Order order4 = new Order(4, "Josh", twoBurgerMeal, defaultLoc,0.0);
        
        //Put the meals into orders as they are probable
        possOrders = new ArrayList<>();
        possOrders.add(order1);
        possOrders.add(order2);
        possOrders.add(order3);
        possOrders.add(order4);
        
        //Put the orders into the list of all orders for the day
        orderList = new ArrayList<>();
        
        //add the orders to the list as they are listed
        //TODO: Add timestamps or something to the orders
        //1st hour
        Random r = new Random();
        int[] shiftOrders = {15, 17, 22, 15};

        for(int i = 0; i < shiftOrders.length; i++){
            for(int j = 0; j < shiftOrders[i]; j++){
                double orderNum = r.nextDouble();
                double prevProbability = 0.0;
                for(int o = 0; o < possOrders.size(); o++){
                    if(orderNum < possOrders.get(o).meals.probability + prevProbability) {
                        Order temp = new Order(possOrders.get(o));
                        //TODO: add random location
                        temp.timeIn = i * 60 + (j * 4) % 60;
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
        ArrayList<ArrayList<Order>> packages = drone.FIFO(orderList);
        timeCalc tc1 = new timeCalc();
        ArrayList<Double> times1 = new ArrayList<>();
        //TODO: Allow calcRoute to take in an arraylist of orders
        boolean first1 = true;
        double timeSinceStart1 = 180;
        for(int i = 0; i < packages.size(); i++){
            tc1.time(packages.get(i), first1);
            double lengthOfCurDelivery = packages.get(i).get(0).timeOut;
//            System.out.println("Delivery " + i + ": ");
            for(int j = 0; j < packages.get(i).size(); j++) {
            	packages.get(i).get(j).timeOut = timeSinceStart1;
            	times1.add(packages.get(i).get(j).timeOut - packages.get(i).get(j).timeIn);
//            	System.out.println("Turn around time: " + (packages.get(i).get(j).timeOut - packages.get(i).get(j).timeIn));
            }
            timeSinceStart1 += lengthOfCurDelivery;
            first1 = false;
        }
        return times1;
    }
        
        
    //Knapsack calculation
    public ArrayList<Double> KnapSack(){
        ArrayList<ArrayList<Order>> KPpackages = drone.knapsacking(orderList);
        timeCalc tc = new timeCalc();
        ArrayList<Double> times = new ArrayList<>();
        //TODO: Allow calcRoute to take in an array list of orders
        boolean first = true;
        double timeSinceStart = 180;
        for(int i = 0; i < KPpackages.size(); i++){
            tc.time(KPpackages.get(i), first);
            double lengthOfCurDelivery = KPpackages.get(i).get(0).timeOut;
//            System.out.println("Delivery " + i + ": ");
            for(int j = 0; j < KPpackages.get(i).size(); j++) {
            	KPpackages.get(i).get(j).timeOut = timeSinceStart;
            	times.add(KPpackages.get(i).get(j).timeOut - KPpackages.get(i).get(j).timeIn);
//            	System.out.println("Turn around time: " + (packages.get(i).get(j).timeOut - packages.get(i).get(j).timeIn));
            }
            timeSinceStart += lengthOfCurDelivery;
            first = false;
        }
    return times;
    }
}

