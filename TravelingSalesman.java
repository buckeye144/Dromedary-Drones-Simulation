// Solver for the traveling salesman problem, ported from Pacheco's parallel textbook, pg. 300
import java.util.Stack;
import java.util.ArrayList;

public class TravelingSalesman {
	private ArrayList<Connection> connections;
	private double MAX_DIST;
	
	public TravelingSalesman() {
		MAX_DIST = 35200;
	}
	
	public ArrayList<Connection> calcRoute(ArrayList<Order> orders) {
		MAX_DIST = 35200;
		//ArrayList<Integer> currentTour = new ArrayList<>();
		Location sac = new Location("SAC", 0, 0);
		Stack<Location> locationStack = new Stack<>();
		ArrayList<Location> locations = new ArrayList<>();
		ArrayList<Location> locationsInOrders = new ArrayList<>();
		ArrayList<Connection> currentTour = new ArrayList<>();
		
		for (int i = 0; i < orders.size(); i++) {
			locationsInOrders.add(orders.get(i).destination);
		}//end for
		
		int n = locationsInOrders.size();
		
	
		
		//Remove multiple orders to the same location
		for (int i = (n-1); i >= 0; i--) {
			if (!locationStack.contains(locationsInOrders.get(i))) {
				locationStack.push(locationsInOrders.get(i));
			}//end if
		}//end for
		
		n = locationStack.size();
		for (int i = 0; i < n; i++) {
			locations.add(locationStack.pop());
		}//end for
		
		
		//Connect all possible locations to each other
		connections = new ArrayList<>();
		for (int i = 0; i < locations.size(); i++) {
			locationStack.push(locations.get(i));
			for (int j = 0; j < locations.size(); j++) {
				if (!(i==j)) {
					Connection c = new Connection(locations.get(i), locations.get(j));
					connections.add(c);
				}//end if
			}//end for
			Connection sacCon = new Connection(locations.get(i), sac);
				connections.add(sacCon);
		}//end for
		
		Connection temp = new Connection(sac, sac);		//temporary connection
		
		double shortest = connections.get(0).getDistance();
		
		//For loop finds the shortest distance from the SAC to next location
		for(int i = 0; i < connections.size(); i++) {
			if(Double.compare(connections.get(i).getDistance(), shortest) < 0 && 						
					connections.get(i).getLoc2().getName().matches("SAC")) {
				if(!feasible(connections.get(i).getDistance())) {
					Connection fail = new Connection(sac, sac);
					currentTour.add(fail);
				} else {
					shortest = connections.get(i).getDistance();
					temp = connections.get(i);
				}//end if
			}//end if
		}//end for
		
		currentTour.add(temp);
		

		//Find all other destinations
		while(currentTour.size() != locations.size()) {
			String lastPos = currentTour.get(currentTour.size() - 1).getLoc2().getName();
			if(connections.size() != 0) {
				shortest = connections.get(0).getDistance();
			} else {
				break;
			}
			//Loop through and remove already visited locations
			int index = 0;
			while(index < 10) {
				for(int i = 0; i < connections.size(); i++) {
					if(connections.get(i).getLoc1().getName().matches(lastPos) ||
							connections.get(i).getLoc2().getName().matches(lastPos)) {
						connections.remove(connections.get(i));
					}//end if
				}//end for
				index++;
			}
			String nextPos = currentTour.get(currentTour.size() - 1).getLoc1().getName();

			//Find the rest of the locations
			for(int i = 0; i < connections.size(); i++) {
				if(Double.compare(connections.get(i).getDistance(), shortest) <= 0 &&
						connections.get(i).getLoc2().getName().matches(nextPos) &&
						!connections.get(i).getLoc1().getName().matches(lastPos) &&
						!connections.get(i).getLoc2().getName().matches("SAC")) {
					if(!feasible(connections.get(i).getDistance())) {
						Connection fail = new Connection(sac, sac);
						currentTour.add(fail);
						System.out.println("failed");
					} else {
						shortest = connections.get(i).getDistance();
						temp = connections.get(i);
					}//end if
				}
				if(connections.size() == 1) {
					shortest = connections.get(i).getDistance();
					temp = connections.get(i);
				}
			}//end for
			currentTour.add(temp);

		}//end while
		if(!feasible(currentTour.get(currentTour.size() - 1).getDistance())) {
			Connection fail = new Connection(sac, sac);
			currentTour.add(fail);
		} else {
			Connection last = new Connection(sac,sac);
			currentTour.add(last);
		}
		

		return currentTour;
	}//end calcRoute method
	
	public boolean feasible(double distance) {
		
		MAX_DIST -= distance;
		if (MAX_DIST < 0) {
			return false;
		}//end if
		return true;
	}//end feasible method
	

}