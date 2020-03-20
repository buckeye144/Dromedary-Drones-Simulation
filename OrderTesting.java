import java.util.ArrayList;
import java.util.Random;

public class OrderTesting{
    public static void main(String[] args){
        //Make the default meals
        FoodItem hamburger = new FoodItem("hamburger", 6);
        FoodItem fries = new FoodItem("fires", 4);
        FoodItem drink = new FoodItem("drink", 14);
        ArrayList<FoodItem> meal0 = new ArrayList<>();
        meal0.add(hamburger);
        meal0.add(fries);
        meal0.add(drink);
        Meal typical = new Meal("typical", meal0);
        ArrayList<FoodItem> meal1 = new ArrayList<>();
        meal1.add(hamburger);
        meal1.add(hamburger);
        meal1.add(fries);
        meal1.add(drink);
        Meal twoBurgerMeal = new Meal("twoBurgerMeal", meal1);
        ArrayList<FoodItem> meal2 = new ArrayList<>();
        meal2.add(hamburger);
        meal2.add(fries);
        Meal burgerFries = new Meal("burgerFries", meal2);
        ArrayList<FoodItem> meal3 = new ArrayList<>();
        meal3.add(hamburger);
        meal3.add(hamburger);
        meal3.add(fries);
        Meal twoBurgers = new Meal("twoBurgers", meal3);

        //standard location
        Location defaultLoc = new Location(0,0);
        //Put the meals into orders as they are probable
        Order order1 = new Order(1, "Jonathan", typical, defaultLoc, 0.55);
        Order order2 = new Order(2, "Nathan", twoBurgers, defaultLoc, 0.10);
        Order order3 = new Order(3, "Daniel", burgerFries, defaultLoc, 0.20);
        Order order4 = new Order(4, "Josh", twoBurgerMeal, defaultLoc, 0.15);

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
                    if(orderNum < possOrders.get(o).probability + prevProbability){
                        orderList.add(possOrders.get(o));
                        break;
                    }
                    prevProbability += possOrders.get(o).probability;
                }
            }
        }
        
        //Print out order reciept
        for(int i = 0; i < orderList.size(); i++){
            System.out.println("Order Name: " + orderList.get(i).name + "\tMeal: " + orderList.get(i).meals.name);
        }
        //TODO: impliment FIFO to get the orders out on the drone

        //TODO: Impliment knapsacking to get the orders out on the drone

        //TODO: Use rough numbers for travel to simulate the difference in time eacho of them have.
    }
}