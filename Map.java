// Basic map class for the traveling salesman problem.
import java.util.ArrayList;

public class Map {
	public ArrayList<Coord> coords;
	public int size;
	
	public Map() {
		coords = new ArrayList<>();
		size = 0;
	}
	
	public void addCoord(Coord coord) {
		coords.add(coord);
		size++;
	}
	
}
