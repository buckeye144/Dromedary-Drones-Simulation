// Solver for the traveling salesman problem, ported from Pacheco's parallel textbook, pg. 300
import java.util.Stack;
import java.util.ArrayList;

public class TravelingSalesman {

	public static void main(String[] args) {
		Map map = new Map();
		Stack<Integer> stack = new Stack<>();
		ArrayList<Coord> coordsToHit = new ArrayList<>();
		Coord startingCoord;
		Coord nullCoord = new Coord(-1,-1);
		ArrayList<Integer> currentTour = new ArrayList<>();
		
		
		// Add to coordsToHit here
		ArrayList<Location> locationsInOrders = new ArrayList<>();
		// Populate locationsInOrders here
		
		int n = locationsInOrders.size();
		int nullIndex = -1;
		
		Stack<Location> locationStack = new Stack<>();
		
		for (int i = (n-1); i >= 1; i--) {
			locationStack.push(locationsInOrders.get(i));
		}
		while (!stack.isEmpty()) {
			int c = stack.pop();
			if (c == nullIndex) {
				// Remove_last_city(curr_tour);
			} else {
				currentTour.add(c);
				if (currentTour.size() == n) {
					// if (Best_tour(curr_tour)) ...
				} else {
					stack.push(nullIndex);
					for (int nbr = n-1; nbr >= 1; nbr--) {
						// if (Feasible(curr_tour, nbr))...
					}
				}
			}
		}

	}
	
	// For each order in the simulation
	// 	Each order has a Location attached to it
	//	Calculate the distance between each location it needs to go to
	//	Use those distances to determine quickest route

}
