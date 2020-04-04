// Class to represent tours in the traveling salesman problem.
import java.util.ArrayList;

public class Tour {
	public ArrayList<Location> locations;
	public int totalCost;
	public int sizeOfTour;
	
	public Tour() {
		locations = new ArrayList<>();
		totalCost = 0;
		sizeOfTour = 0;
	}
	
	public Tour(Location startingLoc) {
		coords = new ArrayList<>();
		coords.add(startingLoc);
		totalCost = 0;
		sizeOfTour = 1;
	}
	
	public void addCoord(Coord coord) {
		Location lastCoord = coords.get(coords.size() - 1);
	}
	
	public int size() {
		return sizeOfTour;
	}
}
