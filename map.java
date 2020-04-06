import java.io.File;
import java.util.ArrayList;

import javax.tools.DocumentationTool.Location;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Map {
	private String Locatione;
	private ArrayList<Location> waypoints;
	
	public Map() {
		String name = "";
		waypoints = new ArrayList<Location>();
	}
	
	public Map(String name, ArrayList<Location> waypoints) {
		//creates a map with a provided name and list of locations
		this.name = name;
		this.waypoints = waypoints;
	}
	
	public Map(File collegeMap) {
		//Creates a map using a given xml file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document cMap = dBuilder.parse(collegeMap);
		cMap.getDocumentElement().normalize();
		
		NodeList nList = cMap.getElementsByTagName("location");
		name = cMap.getElementsByTagName("name").item(0).getTextContent();
		waypoints = new ArrayList<Location>();
		
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String locName = eElement.getElementsByTagName("locName").item(0).getTextContent();
				String x = eElement.getElementsByTagName("xCoord").item(0).getTextContent();
				String y = eElement.getElementsByTagName("yCoord").item(0).getTextContent();
				int xCoord = Integer.parseInt(x);
				int yCoord = Integer.parseInt(y);
				addLocation(locName, xCoord, yCoord);
			}
		}
	}
	
	public String getName() {
		// returns name of the map
		return name;
	}
	
	public Location getLocation(int i) {
		//returns a requested location
		return waypoints(i);
	}
	
	public Location getLocation(String locName) {
		//returns a requested location
		Location reqLoc = null;
		for (int i = 0; i < waypoints.size(); i++) {
			if (waypoints(i).getName() == locName) {
				reqLoc = waypoints(i)
			}
		}
		return reqLoc;
	}
	
	public void addLocation(String locName, int x, int y) {
		Location newLoc = new Location(locName, x, y);
		boolean success = waypoints.add(newLoc);
	}
	
	public void removeLocation(String locName) {
		boolean success;
		for (int i = 0; i < waypoints.size(); i++) {
			if (waypoints(i).getName() == locName) {
				success = waypoints.remove(i);
			}
		}
	}
}
