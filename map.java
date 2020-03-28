
public class map {
	private String name;
	private location waypoints[];
	
	public map(String name, location waypoints[]) {
		//creates a map with a provided name and list of locations
		this.name = name;
		this.waypoints = waypoints;
	}
	
	public String getName() {
		// returns name of the map
		return name;
	}
	
	public location getLocation(int i) {
		//returns a requested location
		return waypoints[i];
	}
	/*
	public location getLocation(String name) {
		//returns a requested location
		for (int i = 0; i < waypoints.length; i++) {
			if (waypoints[i].getName() == name) {
				return waypoints[i];
			}
		}
	}
	*/
}
