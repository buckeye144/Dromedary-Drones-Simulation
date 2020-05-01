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
			if (!nextPermutation(vertices)) {  // Write this function
				break;
			}
			//vertices = calcRoute3_vertexShuffle(vertices);  // nextPermutation looks like it's supposed to shuffle the vertices, but I don't think that's happening in Java.
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

}