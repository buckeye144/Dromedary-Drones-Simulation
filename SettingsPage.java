import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

public class SettingsPage {

	public Scene settingsPage(MainMenu mm, Map map) {
		
		FoodSettings fs = new FoodSettings();
		MealSettings ms = new MealSettings();
		ShiftSettings ss = new ShiftSettings();
		LocationSettings ls = new LocationSettings();
		TabPane settings = new TabPane();
		Scene settingsPage = new Scene(settings, 1000, 750);
		
		Tab food = new Tab("Food");
		Tab meals = new Tab("Meals");
		Tab shift = new Tab("Shifts");
		Tab locations = new Tab("Locations");
		
		food.setContent(fs.food(mm));
		locations.setContent(ls.locations(mm, map));
		meals.setContent(ms.meals(mm));
		shift.setContent(ss.shiftSettings(mm));
		
		settings.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		settings.getTabs().addAll(food,meals,shift,locations);
		
		return settingsPage;
	}
}



//TODO: Shifts page: text box to modify how many shifts, how long a shift is, change number of orders in a shift
//		Food page: modify existing ones
//		Locations page: modify locations
//		Meal page: add/remove/modify meals, change probabilities