
public class Connection {
	private Location loc1;
	private Location loc2;
	private double distance;
	
	//Creator for conenctions
	public Connection (Location locInput1, Location locInput2) {
		loc1 = locInput1;
		loc2 = locInput2;
		distance = Math.sqrt(Math.pow((loc2.getX() - loc1.getX()), 2) + Math.pow((loc2.getY() - loc1.getY()), 2));
	}
	
	//Getter for the first location
	public Location getLoc1() {
		return loc1;
	}
	
	//Getter for the second location
	public Location getLoc2() {
		return loc2;
	}
	
	//Getter for the distance between the two locations
	public double getDistance() {
		return distance;
	}
	
	//Setter for the first location. Also calculates the new distance.
	public void setLoc1(Location newLoc) {
		loc1 = newLoc;
		distance = Math.sqrt(Math.pow((loc2.getX() - loc1.getX()), 2) + Math.pow((loc2.getY() - loc1.getY()), 2));
	}
	
	//Setter for the second location. Also calculates the new distance.
	public void setLoc2(Location newLoc) {
		loc2 = newLoc;
		distance = Math.sqrt(Math.pow((loc2.getX() - loc1.getX()), 2) + Math.pow((loc2.getY() - loc1.getY()), 2));
	}
}