import java.util.Stack;
import java.util.ArrayList;

public class timeCalc {
	
	public ArrayList<Double> time (ArrayList<Order> orders, boolean firstLeave) {
		ArrayList<Double> times = new ArrayList<>();
		TravelingSalesman tsp = new TravelingSalesman();
		ArrayList<Connection> bestTour = tsp.calcRoute(orders);
		double time = 0.0;
		
		if (!firstLeave) {
			time = time + 3;
		}
		
		for(int i = 0; i < bestTour.size(); i++) {
			time = time + bestTour.get(i).getDistance()/1760.0;
			time = time + 0.5;	//Down time
		}
		System.out.println(bestTour.size());
		for (int i = 0 ; i < orders.size(); i++) {
			if ((orders.get(i).destination.getX() ==
					bestTour.get(i).getLoc1().getX()) && 
					(orders.get(i).destination.getY() == bestTour.get(i).getLoc1().getY())) {
				orders.remove(i);
				times.add(time);
			}
		}
		
		
		
		
//		for (int i = 0; i < bestTour.size(); i++) {
//			System.out.println(bestTour.get(i).getName());
//		}
		
//		while (!bestTour.isEmpty()) {
//			Location loc1 = bestTour.pop();
//			Location loc2 = bestTour.peek();
//			
//			double distance =  Math.sqrt(Math.pow((loc2.getX() - loc1.getX()), 2) + Math.pow((loc2.getY() - loc1.getY()), 2));
//			time = time + distance/1760.0;
//			time = time + 0.5;
//			for (int i = 0 ; i < orders.size(); i++) {
//				if ((orders.get(i).destination.getX() == loc2.getX()) && (orders.get(i).destination.getY() == loc2.getY())) {
//					orders.remove(i);
//					times.add(time);
//				}
//			}
//		}
//		System.out.println(times);
		return times;
	}
}