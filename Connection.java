
public class Connection {
	private Location loc1;
	private Location loc2;
	private double distance;
	
	public Connection (Location locInput1, Location locInput2) {
		loc1 = locInput1;
		loc2 = locInput2;
		distance = Math.sqrt(Math.pow((loc2.getX() - loc1.getX()), 2) + Math.pow((loc2.getY() - loc1.getY()), 2));
	}
	
	public Location getLoc1() {
		return loc1;
	}
	
	public Location getLoc2() {
		return loc2;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setLoc1(Location newLoc) {
		loc1 = newLoc;
		distance = Math.sqrt(Math.pow((loc2.getX() - loc1.getX()), 2) + Math.pow((loc2.getY() - loc1.getY()), 2));
	}
	
	public void setLoc2(Location newLoc) {
		loc2 = newLoc;
		distance = Math.sqrt(Math.pow((loc2.getX() - loc1.getX()), 2) + Math.pow((loc2.getY() - loc1.getY()), 2));
	}
}
