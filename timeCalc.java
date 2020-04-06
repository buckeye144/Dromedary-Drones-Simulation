import java.util.Stack;
import java.util.ArrayList;

import javax.tools.DocumentationTool.Location;

public class timeCalc {
	
	public timeCalc() {
		
	}
	
	public double time(double dist, ArrayList<Order> orders) {
		double time = dist/1760;
		double n = 0;
		
		Stack<Location> orderLoc = new Stack<Location>();
		for (int i = 0; i < orders.size(); i++) {
			if (!orderLoc.contains(orders.get(i).destination)) {
				n = n + 1;
			}
		}
		
		
		time = time + (n * .5) + 3;
		
		return time;
	}
}
