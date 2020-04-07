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
	public makeOrders() {
		//Make the default meals
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
	     
	}
    public ArrayList<Double> simulation(){
    	
        //standard location
        Location defaultLoc = new Location("null", 0, 0);
        
        Order order1 = new Order(1, "Jonathan", typical, defaultLoc);
        Order order2 = new Order(2, "Nathan", twoBurgers, defaultLoc);
        Order order3 = new Order(3, "Daniel", burgerFries, defaultLoc);
        Order order4 = new Order(4, "Josh", twoBurgerMeal, defaultLoc);
        
        //Put the meals into orders as they are probable
        ArrayList<Order> possOrders = new ArrayList<>();
        possOrders.add(order1);
        possOrders.add(order2);
        possOrders.add(order3);
        possOrders.add(order4);
        
        //Put the orders into the list of all orders for the day
        ArrayList<Order> orderList = new ArrayList<>();
        
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
                    if(orderNum < 
                    		possOrders.get(o).meals.probability 
                    		+ prevProbability){  
                        Order temp = possOrders.get(o);
                        //TODO: add random location
                        possOrders.get(o).destination = new Location("test", 1, 2);
                        orderList.add(possOrders.get(o));
                        break;
                    }
                    prevProbability += possOrders.get(o).meals.probability;
                }
            }
        }
        
        //Print out order reciept
//        for(int i = 0; i < orderList.size(); i++){
//            System.out.println(i + "\tOrder Name: " + orderList.get(i).name + "\tMeal: " + orderList.get(i).meals.name);
//        }

        Drone drone = new Drone();
        //Drone deliver groupings with FIFO
        ArrayList<ArrayList<Order>> packages = drone.FIFO(orderList);

//        //Print the deliveries
//        for(int i = 0; i < packages.size(); i++){
//            //System.out.println("Delivery (FIFO) " + i + ":");
//            int deliveryWeight = 0;
//            for(int j = 0; j < packages.get(i).size(); j++){
//                //System.out.println("\tOrder Name: " + packages.get(i).get(j).name + "\tMeal: " + packages.get(i).get(j).meals.name);
//                deliveryWeight += drone.OrderCapacity(packages.get(i).get(j));
//            }
//            //System.out.println("\tTotal Weight: " + deliveryWeight/16.0);
//        }

        packages = drone.knapsacking(orderList);

//        //Print the deliveries
//        for(int i = 0; i < packages.size(); i++){
//            //System.out.println("Delivery (Knapsacking) " + i + ":");
//            int deliveryWeight = 0;
//            for(int j = 0; j < packages.get(i).size(); j++){
//                //System.out.println("\tOrder Name: " + packages.get(i).get(j).name + "\tMeal: " + packages.get(i).get(j).meals.name);
//                deliveryWeight += drone.OrderCapacity(packages.get(i).get(j));
//            }
//            //System.out.println("\tTotal Weight: " + deliveryWeight/16.0);
//        }
        
        //TSP
        packages = drone.knapsacking(orderList);
        timeCalc tc = new timeCalc();
        double[] dist = new double[packages.size()];
        ArrayList<Double> times = new ArrayList<>();
        //TODO: Allow calcRoute to take in an arraylist of orders
        boolean first = true;
        for(int i = 0; i < packages.size(); i++){
            ArrayList<Double> temp = tc.time(packages.get(i), first);
            first = false;
            for (int j = 0; j < temp.size(); j++) {
            	times.add(temp.get(j));
            }
        }
        return times;
    }
}