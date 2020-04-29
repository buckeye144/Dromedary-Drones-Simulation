import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.VBox;

public class SettingsPage {
	
	FoodSettings fs;
	MealSettings ms;
	ShiftSettings ss;
	LocationSettings ls;
	VBox fsBox, msBox, ssBox, lsBox;
	MainMenu mm;
	Map map;
	
	public SettingsPage(MainMenu mm, Map map) {
		fs = new FoodSettings();
		ms = new MealSettings();
		ss = new ShiftSettings();
		ls = new LocationSettings();
		fsBox = fs.food(mm);
		lsBox = ls.locations(mm, map);
		msBox = ms.meals(mm);
		ssBox = ss.shiftSettings(mm);
	}

	public Scene settingsPage() {
		
		TabPane settings = new TabPane();
		Scene settingsPage = new Scene(settings, 1000, 750);
		
		Tab food = new Tab("Food");
		Tab meals = new Tab("Meals");
		Tab shift = new Tab("Shifts");
		Tab locations = new Tab("Locations");
		
		food.setContent(fsBox);
		locations.setContent(lsBox);
		meals.setContent(msBox);
		shift.setContent(ssBox);
		
		settings.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		settings.getTabs().addAll(food,meals,shift,locations);
		
		return settingsPage;
	}
}



//TODO: Shifts page: text box to modify how many shifts, how long a shift is, change number of orders in a shift
//		Food page: modify existing ones
//		Locations page: modify locations
//		Meal page: add/remove/modify meals, change probabilities