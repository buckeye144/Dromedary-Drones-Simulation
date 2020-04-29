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
		
		//Print orders list
//		System.out.println("Orders size: " + orders.size());
//		System.out.println("Orders: ");
//		for (int i = 0; i < orders.size(); i++) {
//			System.out.println(orders.get(i).destination.getName());
//		}
		
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
		
		//Print unique locations
//		System.out.println("\nLocations size: " + locations.size());
//		System.out.println("Locations:");
//		for (int i = 0; i < locations.size(); i++) {
//			System.out.println(locations.get(i).getName());
//		}//end for
//		System.out.println("\n");
//		
		//Print location stacks
//		System.out.println("\nLocation stack size: " + locationStack.size());
//		for (int i = 0; i < locationStack.size(); i++) {
//			System.out.println(locationStack.get(i).getName());
//		}
//		System.out.println("\n\n");
		
//		while (!locationStack.isEmpty()) {
//			System.out.println("=============================");
//			System.out.println("Location Stack Size: " + locationStack.size());
//			System.out.println("Current Tour Size: " + currentTour.size());
//			System.out.println("\nUpdated Location Stack:");
//			for (int i = 0; i < locationStack.size(); i++) {
//				System.out.println(locationStack.get(i).getName());
//			}
//			System.out.println("\nUpdated Current Tour:");
//			for (int i = 0; i < currentTour.size(); i++) {
//				System.out.println(currentTour.get(i).getName());
//			}
//			
//			System.out.println("\n");
//			Location currentLoc = new Location(locationStack.pop());
//			System.out.println("Popped from stack: " + currentLoc.getName());
//			System.out.println("Flag: 1    " + currentLoc.getName());
//			if ((currentLoc.getX() == sac.getX()) && (currentLoc.getY() == sac.getY())) {
//				System.out.println("Flag: 2");
//				currentTour.pop();
//			}
//			else {
//				System.out.println("Flag: 3");
//				currentTour.push(currentLoc);
//				System.out.println("Added "+ currentLoc.getName() + " to stack");
//				if (currentTour.size() == n) {
//					System.out.println("Flag: 4");
//					System.out.println(DIST);
//					if (DIST < BEST_DIST) {
//						System.out.println("Flag: 5");
//						BEST_DIST = DIST;
//						bestTour = (Stack<Location>) currentTour.clone();
//					}
//					currentTour.pop();
//					System.out.println("Popped from stack");
//				}
//				else {
//					locationStack.push(sac);
//					for (int i = n - 1; i >= 1 ; i--) {
//						if (feasible(currentTour, locations.get(i))) {
//							locationStack.remove(0);
//							locationStack.push(locations.get(i));
//						}
//					}
//				}
//			}
//		}
//		System.out.println("\n\nBest tours:");
//		for (int i = 0; i < bestTour.size(); i++) {
//			System.out.println(bestTour.get(i).getName());
//		}
//		System.out.println("\n\n");
//		
//		return bestTour;
		
		
		//Print out current connections
//		System.out.println("Current Connections:");
		for(int i = 0; i < connections.size(); i++) {
//			System.out.print("1: " + connections.get(i).getLoc1().getName() + " -> " +
//					"2: " + connections.get(i).getLoc2().getName());
//			System.out.println(":   " + connections.get(i).getDistance());
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
//		System.out.println("Added: " + temp.getLoc2().getName() + " -> " +
//				temp.getLoc1().getName());
		

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
//			System.out.println("\n\nLast shortest: " + shortest);
//			System.out.println("\n\nLast Position: " + lastPos);
//			System.out.println("Current Position: " + nextPos);
//			System.out.println("Connection update:");
			for(int i = 0; i < connections.size(); i++) {
//				System.out.print("1: " + connections.get(i).getLoc1().getName() + " -> " +
//						"2: " + connections.get(i).getLoc2().getName());
//				System.out.println(":   " + connections.get(i).getDistance());
			}//end for
			
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
//			System.out.println("Added: " + temp.getLoc2().getName() + " -> " +
//					temp.getLoc1().getName());
		}//end while
		if(!feasible(currentTour.get(currentTour.size() - 1).getDistance())) {
			Connection fail = new Connection(sac, sac);
			currentTour.add(fail);
		} else {
			Connection last = new Connection(sac,sac);
			currentTour.add(last);
		}
		
//		System.out.println("\n\nCurrent Tour: " + currentTour.size());
//		for(int i = 0; i < currentTour.size(); i++) {
//			System.out.println(currentTour.get(i).getLoc1().getName());
//		}//end for
		return currentTour;
	}//end calcRoute method
	
	public boolean feasible(double distance) {
		
		MAX_DIST -= distance;
		if (MAX_DIST < 0) {
			return false;
		}//end if
		return true;
	}//end feasible method
	
	
	public ArrayList<Connection> calcRoute2(ArrayList<Order> orders) {
		// Do something, Taipu
		// Simulated Annealing method, ported from https://www.baeldung.com/java-simulated-annealing-for-traveling-salesman
		
		Location sac = new Location("SAC", 0, 0);
		double temperature = 1000.0;  // Set this
		double coolingRate = 1;  // Set this
		int numberOfIterations = 1000;  // I don't know what a good value for this is
		
		// First things first, get a list of Locations from orders.
		Tour tour = new Tour();
		ArrayList<Location> locs = new ArrayList<>();
		locs.add(sac);
		tour.addLocation(sac);
		for (Order o : orders) {
			if (!locs.contains(o.destination)) {  // This comparison might not work
				locs.add(o.destination);
				tour.addLocation(o.destination);
			}
		}
		locs.add(sac);
		tour.addLocation(sac);
		
		// simulateAnnealing() from the webpage
		double bestDistance = tour.getDistance();
		Tour currentSolution = tour;
		
		for (int i=0; i < numberOfIterations; i++) {
			if (temperature > 0.1) {
				// Do something, Taipu
				currentSolution.swapLocs();
				double currentDistance = currentSolution.getDistance();
				if (currentDistance < bestDistance) {
					bestDistance = currentDistance;
				} else if (Math.exp((bestDistance - currentDistance) / temperature) < Math.random()) {
					currentSolution.revert();
				}
			} else {
				continue;
			}
			temperature *= coolingRate;
		}
		// currentSolution is, well, our solution to the problem.  What do we do with it?
		// The current code is set to take an ArrayList of Connections.  We need to create that.
		ArrayList<Connection> results = new ArrayList<>();
		for (int i=0; i < currentSolution.sizeOfTour-1; i++) {
			Connection newConn = new Connection(currentSolution.locations.get(i), currentSolution.locations.get(i+1));
			results.add(newConn);
		}
		return results;
	}
	
	public ArrayList<Connection> calcRoute3(ArrayList<Order> orders) {
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
				/*if (x == y) {
					graph[x][y] = Double.MAX_VALUE;
				} else {
					graph[x][y] = l1.calcDistance(l2);
				}*/
				graph[x][y] = l1.calcDistance(l2);
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
				//System.out.printf("%d, %d\n", k, vertices.get(i));
				// Add to an ArrayList<Connection>
				Connection newConn = new Connection(locs.get(k), locs.get(vertices.get(i)));
				path.add(newConn);
				
				k = vertices.get(i);
			}
			current_pathweight += graph[k][sourceVertex];
			Connection newConn = new Connection(locs.get(k), locs.get(sourceVertex));
			path.add(newConn);
			//System.out.printf("current_pathweight: %f, min_path: %f\n", current_pathweight, min_path);
			// Update min_path
			if (current_pathweight < min_path) {
				min_path = current_pathweight;
				// If this path is the best we've found yet, keep the above ArrayList
				results = path;
			}
			if (!calcRoute3_nextPermutation(vertices)) {  // Write this function
				break;
			}
			vertices = calcRoute3_vertexShuffle(vertices);  // nextPermutation looks like it's supposed to shuffle the vertices, but I don't think that's happening in Java.
		}
		
		return results;
	}
	
	public boolean calcRoute3_nextPermutation(ArrayList<Integer> vertices) {
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
	
	public ArrayList<Integer> calcRoute3_vertexShuffle(ArrayList<Integer> vertices) {  // Like nextPermutation, but returns you an ArrayList with elements shuffled
		int temp;  // Used for swapping elements
		int n = vertices.size();
		int i = n - 2;
		while (i >= 0 && (vertices.get(i) >= vertices.get(i + 1))) {
			i--;
		}
		
		if (i == -1) {
			return vertices;
		}
		
		int j = i + 1;
		while (j < n && (vertices.get(j) > vertices.get(i))) {
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
		
		return vertices;
	}
	
}