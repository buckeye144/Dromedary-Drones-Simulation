// Solver for the traveling salesman problem, ported from Pacheco's parallel textbook, pg. 300
import java.util.Stack;

import com.sun.xml.internal.bind.v2.runtime.Location;

import java.sql.Connection;
import java.util.ArrayList;

public class TravelingSalesman {
	private ArrayList<Connection> connections;
	private double DIST = 0;
	private double BEST_DIST;
	
	public double calcRoute(Map map) {
		Stack<Integer> stack = new Stack<>();
		ArrayList<Coord> coordsToHit = new ArrayList<>();
		Coord startingCoord;
		Coord nullCoord = new Coord(-1,-1);
		ArrayList<Integer> currentTour = new ArrayList<>();
		Location sac = New Location(0, 0);
		Stack<Location> currentTour = new Stack<>();
		Stack<Location> bestTour = new Stack<>();
		DIST = 0;
		BEST_DIST = 35200;
		
		// Add to coordsToHit here
		ArrayList<Location> locationsInOrders = new ArrayList<>();
		// Populate locationsInOrders here
		
		int n = locationsInOrders.size();
		int nullIndex = -1;
		
		Stack<Location> locationStack = new Stack<>();
		
		for (int i = (n-1); i >= 1; i--) {
			if (!locationStack.contains(locationsInOrders.get(i)) {
					locationStack.push(locationsInOrders.get(i));
			}
		}
		
		n = locationStack.size();
		ArrayList<Location> locations = new ArrayList();
		for (int i = 0; i < locationStack.size(); i++) {
			locations.add(locationStack.pop());
		}
		
		connections = newArrayList();
		for (int i = 0; i < locations.length(); i++) {
			locationStack.push(locations.get(i));
			for (int j = 0; j < locations.length(); j++) {
				if (!(i==j)) {
					Connection c = new Connection(locations.get(i), locations.get(j));
					connections.add(c);
				}
				Connection sacCon = new Connection(locations.get(i), sacCon);
				connections.add(sacCon);
			}
		}
		
		while (!locationStack.isEmpty()) {
			currentLoc = locationStack.pop();
			if ((currentLocation.getX() == sac.getX()) && (currentLocation.getY() == sac.getY())) {
				currentTour.pop();
			}
			
			else {
				currentTour.push(currentLoc);
				if (currentTour.size() == n) {
					if (DIST < BEST_DIST) {
						BEST_DIST = DIST;
						bestTour = currentTour;
					}
					currentTour.pop();
				}
				
				else {
					locationStack.push(sac);
					for (int i = 0; i < location.length(); i ++) {
						if (feasible(currentTour, location.get(i))) {
							locationStack.push(location.get(i));
						}
					}
				}
			}
			return BEST_DIST;
		}
	}
	
	public boolean feasible(Stack<Location> currentTour, Location loc) {
		//Make sure drone can return
		if (currentTour.contains(loc)) {
			return false;
		}
		else {
			int conDist = 0;
			for (int i = 0; i < connections.length(); i++) {
				if ((currentTour.peek() == connection.get(i).getLoc1()) && (loc == connection.get(i).getLoc2())) {
					conDist = connection.get(i).getDistance();
				}
			}
			int tempDist = DIST + conDist;
			if (tempDist >= BEST_DIST) {
				return false;
			}
			else {
				DIST = DIST + conDist;
				return true;
			}
		}
	}
	
	// For each order in the simulation
	// 	Each order has a Location attached to it
	//	Calculate the distance between each location it needs to go to
	//	Use those distances to determine quickest route

}
