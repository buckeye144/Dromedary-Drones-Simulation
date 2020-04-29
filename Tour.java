// Class to represent tours in the traveling salesman problem.
import java.util.ArrayList;
import java.util.Random;

public class Tour {
	public ArrayList<Location> locations;
	public int sizeOfTour;
	public Tour previousTour;
	public Random rng;
	
	public Tour() {
		rng = new Random();  // Maybe we should move this someplace else
		locations = new ArrayList<>();
		sizeOfTour = 0;
		previousTour = null;
	}
	
	public Tour copy() {
		Tour newTour = new Tour();
		for (Location l : locations) {
			newTour.addLocation(l);
		}
		return newTour;
	}
	
	/*public Tour(Location startingLoc) {
		locations = new ArrayList<>();
		locations.add(startingLoc);
		totalCost = 0;
		sizeOfTour = 1;
	}*/
	
	public void addLocation(Location loc) {
		locations.add(loc);
		sizeOfTour += 1;
	}
	
	public int getSize() {
		return sizeOfTour;
	}
	
	public int getDistance() {
		int distance = 0;
		for (int i=0; i < sizeOfTour-1; i++) {
			Location starting = locations.get(i);
			Location destination;
			if (i+1 < sizeOfTour) {
				destination = locations.get(i+1);
			} else {  // We shouldn't need this, but just in case
				destination = locations.get(0);
			}
			distance += starting.calcDistance(destination);
		}
		return distance;
	}
	
	public void swapLocs() {
		// Assume the start and end locations are fixed (e.g. the SAC)
		int a = (Math.abs(rng.nextInt()) % (sizeOfTour - 2)) + 1;  // For some reason, this expression can generate negative numbers
		int b = (Math.abs(rng.nextInt()) % (sizeOfTour - 2)) + 1;
		previousTour = copy();
		Location x = locations.get(a);
		Location y = locations.get(b);
		locations.set(a, y);
		locations.set(b, x);
	}
	
	public void revert() {
		if (previousTour != null) {
			locations = new ArrayList<>();
			sizeOfTour = 0;
			for (Location l : previousTour.locations) {
				addLocation(l);
			}
		}
	}
}