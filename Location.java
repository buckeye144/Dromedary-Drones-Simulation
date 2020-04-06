
public class Location {
	private String name;
	private int x;
	private int y;
	
	public Location(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
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
}
