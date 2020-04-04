import java.util.ArrayList;

public class Location{
    int xCoordinate;
    int yCoordinate;
    public ArrayList<Connection> connections;
    
    public Location(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }
    
    public int getX() {
    	return xCoordinate;
    }
    public int getY() {
    	return yCoordinate;
    }
}