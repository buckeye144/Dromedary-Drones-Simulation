import java.util.Stack;
import java.util.ArrayList;

import javax.tools.DocumentationTool.Location;

public class timeCalc {
	
	public timeCalc() {
		
	}
	
	public ArrayList<double> time(ArrayList<Order> orders, boolean firstLeave) {
		ArrayList<double> times = new ArrayList<>();
		TravelingSalesman tsp = new TravelingSalesman();
		Stack<Locations> bestTour = tsp.calcRoute(orders);
		double time = 0;
		
		if (!firstLeave) {
			time = time + 3;
		}
		
		while (!bestTour.isEmpty()) {
			Location loc1 = bestTour.pop();
			Location loc2 = bestTour.peek();
			
			int distance = Math.sqrt(Math.pow((loc2.getX() - loc1.getX()), 2) + Math.pow((loc2.getY() - loc1.getY()), 2));
			time = time + distance/1760;
			time = time + 0.5;
			for (int i = 0 ; i < orders.size(); i++) {
				if ((orders.get(i).destination.getX() == loc2.getX()) && (orders.get(i).destination.getY() == loc2.getY())) {
					orders.remove(i);
					times.add(time);
				}
			}
		}
		
		return times;
	}
}
