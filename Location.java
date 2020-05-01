public class Location {
	private String name;
	private int x;
	private int y;
	
	public Location(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public Location(Location l) {
		this.name = l.name;
		this.x = l.x;
		this.y = l.y;
	}
	
	public String getName() {
		return name;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
	    return this.getName();
	}
	
	public static double calcDistance(Location l1, Location l2) {
		int x = Math.abs(l1.getX() - l2.getX());
        int y = Math.abs(l1.getY() - l2.getY());
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
}