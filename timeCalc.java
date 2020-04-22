import java.util.ArrayList;

public class TimeCalc {
	//everything is in minutes
	public double time(ArrayList<Order> orders, boolean firstLeave) {
		TravelingSalesman tsp = new TravelingSalesman();
		ArrayList<Connection> bestTour = tsp.calcRoute(orders);
		double time = 0.0;
		
		if (!firstLeave) {
			time = time + 2.5;
		}
		
		for(int i = 0; i < bestTour.size(); i++) {
			time = time + bestTour.get(i).getDistance()/1760; //20mph -> 1760 fpm
			time = time + .5;	//Drop off for 30 seconds
			if(i + 1 != bestTour.size()){ //if it's not going back to the sac
				orders.get(i).timeOut = time; //set the timeOut for the time after the drone leaves the deliver point
			}
		}

		return time;
	}
}