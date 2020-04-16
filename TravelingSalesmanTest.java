import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class TravelingSalesmanTest {

	@Test
	void simulatedAnnealingTest() {
		// Since I don't have a main() method to use and I don't particularly want to mess up whatever people are working on, maybe JUnit can help.
		TravelingSalesman calculator = new TravelingSalesman();  // Create our TSP calculator
		// Create orders (copied from OrderTesting.java)
		FoodItem hamburger = new FoodItem("hamburger", 6);
        FoodItem fries = new FoodItem("fries", 4);
        FoodItem drink = new FoodItem("drink", 14);
        ObservableList<FoodItem> meal0 = FXCollections.observableArrayList();
        meal0.add(hamburger);
        meal0.add(fries);
        meal0.add(drink);
        Meal typical = new Meal("typical", meal0, .55);
		Order order1 = new Order(1, "Jonathan", typical, new Location("dummy1", 10, 10), 0);
        Order order2 = new Order(2, "Nathan", typical, new Location("dummy2", 20, 20), 0);
        Order order3 = new Order(3, "Daniel", typical, new Location("dummy3", -5, 0), 0);
        Order order4 = new Order(4, "Josh", typical, new Location("dummy4", 20, 15), 0);
        
        // Create ArrayList of orders
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
        
        // Run through calculator
        ArrayList<Connection> results = calculator.calcRoute2(orders);
        // Alright, we have our results, now how do we view them?
        for (Connection c : results) {
        	Location l1 = c.getLoc1();
        	Location l2 = c.getLoc2();
        	System.out.printf("%s(%d,%d) to %s(%d,%d): dist %f\n", l1.getName(), l1.getX(), l1.getY(), l2.getName(), l2.getX(), l2.getY(), c.getDistance());
        }
		
		fail("Not yet implemented");  // I can't think of a good success condition for this, I just want to see the results
	}

}
