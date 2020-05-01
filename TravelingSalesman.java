// Solver for the traveling salesman problem, ported from Pacheco's parallel textbook, pg. 300
import java.util.Stack;
import java.util.ArrayList;
import java.util.Scanner;

public class TravelingSalesman {
	private ArrayList<Connection> connections;
	private double MAX_DIST;
	
	public TravelingSalesman() {
		MAX_DIST = 35200;
	}
	
	public ArrayList<Connection> calcRoute(ArrayList<Order> orders) {
		Scanner scan = new Scanner(System.in);
		// https://www.geeksforgeeks.org/traveling-salesman-problem-tsp-implementation/

		ArrayList<Connection> results = new ArrayList<>();  // Initialized to keep Eclipse from yammering
		// Generate list of locations from orders
		Location sac = new Location("SAC", 0, 0);
		ArrayList<Location> locs = new ArrayList<>();
		locs.add(sac);
		for (Order o : orders) {
			if (!locs.contains(o.destination)) {  // This comparison might not work
				locs.add(o.destination);
			}
		}

		
		int n = locs.size();
		// Create and populate graph.  It seems a lot of implementations use something like this
		double[][] graph = new double[n][n];
		for (int x=0; x<n; x++) {
			for (int y=0; y<n; y++) {
				Location l1 = locs.get(x);
				Location l2 = locs.get(y);
				graph[x][y] = Location.calcDistance(l1, l2);
			}
		}
		
		// Oh, my kingdom for either good comments or variable names
		// Store all vertices apart from source vertex
		int sourceVertex = 0;
		ArrayList<Integer> vertices = new ArrayList<>();
		for (int i=0; i<n; i++) {
			if (i != sourceVertex) {
				vertices.add(i);
			}
		}
		
		double min_path = Double.MAX_VALUE;
		
		while (true) {
			ArrayList<Connection> path = new ArrayList<>();
			// Reset current_pathweight
			double current_pathweight = 0;
			
			// Compute current_pathweight
			int k = sourceVertex;
			for (int i = 0; i < vertices.size(); i++) {
				current_pathweight += graph[k][vertices.get(i)];
				// Add to an ArrayList<Connection>
				Connection newConn = new Connection(locs.get(k), locs.get(vertices.get(i)));
				path.add(newConn);
				
				k = vertices.get(i);
			}
			current_pathweight += graph[k][sourceVertex];
			Connection newConn = new Connection(locs.get(k), locs.get(sourceVertex));
			path.add(newConn);
			// Update min_path
			if (current_pathweight < min_path) {
				min_path = current_pathweight;
				// If this path is the best we've found yet, keep the above ArrayList
				results = path;
			}
			if (!nextPermutation(vertices)) {  // Write this function
				break;
			}
		}
		
		return results;
	}
	
	public boolean nextPermutation(ArrayList<Integer> vertices) {
		int temp;  // Used for swapping elements
		int n = vertices.size();
		int i = n - 2;
		while (i >= 0 && (vertices.get(i) >= vertices.get(i + 1))) {
			i--;
		}
		
		if (i == -1) {
			return false;
		}
		
		int j = i + 1;
		while ((j < n) && (vertices.get(j) > vertices.get(i))) {
			j++;
		}
		j--;
		
		// Swap the i, j elements
		temp = vertices.get(j);
		vertices.set(j, vertices.get(i));
		vertices.set(i, temp);
		
		int left = i + 1;
		int right = n - 1;
		
		while (left < right) {
			// Swap left and right elements
			temp = vertices.get(left);
			vertices.set(left, vertices.get(right));
			vertices.set(right, temp);
			
			left++;
			right--;
		}
		
		return true;
	}
	
	public ArrayList<Connection> calcRoute2(ArrayList<Order> orders) {
		// The older, less precise algorithm, but it works nicely on larger data sets
		MAX_DIST = 35200;
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