// Class to hold map coordinates for the traveling salesman problem.
import java.util.ArrayList;

public class Coord {
	public int x;
	public int y;
	public ArrayList<Integer> costs;
	
	public Coord() {
		costs = new ArrayList<>();
		this.x = 0;
		this.y = 0;
	}
	
	public Coord(int x, int y) {
		costs = new ArrayList<>();
		this.x = x;
		this.y = y;
	}
	
	public void addConnection(Coord coord, int cost) {
		costs.add(cost);
	}
	
	public double calcDistance(Coord coord2) {
		double result = Math.sqrt(Math.pow((x - coord2.x), 2) + Math.pow((y - coord2.y), 2));
		return result;
	}
	
	public static double calcDistance(Coord coord1, Coord coord2) {
		double result = Math.sqrt(Math.pow((coord1.x - coord2.x), 2) + Math.pow((coord2.y - coord2.y), 2));
		return result;
	}

}
