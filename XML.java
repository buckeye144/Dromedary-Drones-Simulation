import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XML {
	
	//Method removes an item from whichever xml file it comes from
	public void remove(String removed, String tagname, String name, String filename)  {
		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filename);
			NodeList nodes = doc.getElementsByTagName(tagname);
			for (int i = 0; i < nodes.getLength(); i++) {
				Element location = (Element)nodes.item(i);
				Element locationName = (Element)location.getElementsByTagName(name).item(0);
				String lName = locationName.getTextContent();
				if(lName.matches(removed)) {
					location.getParentNode().removeChild(location);
				}
			}
			
			DOMSource ds = new DOMSource(doc);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			StreamResult sr = new StreamResult(new File(filename));
			t.transform(ds, sr);
	
		} catch (TransformerException | 
				ParserConfigurationException | 
				SAXException | 
				IOException tfe) {
			tfe.printStackTrace();
		}
	}
	
	//Adds a food item to the food.xml
	public void addFood(FoodItem thing) {
		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse("food.xml");
			
			Element last = doc.getDocumentElement();
			Element newFood = doc.createElement("food");
			Element newFoodName = doc.createElement("foodName");
			newFoodName.appendChild(doc.createTextNode(thing.name));
			newFood.appendChild(newFoodName);
			Element newWeight = doc.createElement("weight");
			newWeight.appendChild(doc.createTextNode(Integer.toString(thing.weight)));
			newFood.appendChild(newWeight);
			
			last.appendChild(newFood);
			
			DOMSource ds = new DOMSource(doc);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			StreamResult sr = new StreamResult(new File("food.xml"));
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			t.transform(ds, sr);
		
		} catch (TransformerException | 
				ParserConfigurationException | 
				SAXException | 
				IOException tfe) {
			tfe.printStackTrace();
		}
	}
	
	//Adds a location item to the location.xml file
	public void addLocation(Location thing) {
		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse("locations.xml");
			
			Element last = doc.getDocumentElement();
			Element newLocation = doc.createElement("location");
			Element newLocationName = doc.createElement("locName");
			newLocationName.appendChild(doc.createTextNode(thing.getName()));
			newLocation.appendChild(newLocationName);
			Element newX = doc.createElement("xCoord");
			newX.appendChild(doc.createTextNode(Integer.toString(thing.getX())));
			newLocation.appendChild(newX);
			Element newY = doc.createElement("yCoord");
			newY.appendChild(doc.createTextNode(Integer.toString(thing.getY())));
			newLocation.appendChild(newY);
			
			last.appendChild(newLocation);
			
			DOMSource ds = new DOMSource(doc);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			StreamResult sr = new StreamResult(new File("locations.xml"));
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			t.transform(ds, sr);
		
		} catch (TransformerException | 
				ParserConfigurationException | 
				SAXException | 
				IOException tfe) {
			tfe.printStackTrace();
		}
	}
	
	public void updateFood(String foodName, String newName, String newWeight) {
		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse("food.xml");
			
			NodeList nodes = doc.getElementsByTagName("food");
			for (int i = 0; i < nodes.getLength(); i++) {
				Node p = nodes.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE) {
					Element food = (Element) p;
					NodeList list = food.getChildNodes();
					for (int j = 0; j < list.getLength(); j++) {
						Node n = list.item(j);
						if (n.getNodeType() == Node.ELEMENT_NODE) {
							Element attribute = (Element) n;
							if(attribute.getTextContent().matches(foodName)) {
								list.item(1).setTextContent(newName);
								list.item(3).setTextContent(newWeight);
							}
						}
					}
				}
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			DOMSource ds = new DOMSource(doc);
			StreamResult sr = new StreamResult(new File("food.xml"));
			t.transform(ds, sr);
			
		} catch (TransformerException | 
				ParserConfigurationException | 
				SAXException | 
				IOException tfe) {
			tfe.printStackTrace();
		}
	}
	
	//Edits a location in the location.xml file
	public void updateLocation(String locName, String newName, String newX, String newY) {
		
		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse("locations.xml");
			
			NodeList nodes = doc.getElementsByTagName("location");
			for (int i = 0; i < nodes.getLength(); i++) {
				Node p = nodes.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE) {
					Element aLocation = (Element) p;
					NodeList list = aLocation.getChildNodes();
					for (int j = 0; j < list.getLength(); j++) {
						Node n = list.item(j);
						if (n.getNodeType() == Node.ELEMENT_NODE) {
							Element attribute = (Element) n;
							if(attribute.getTextContent().matches(locName)) {
								list.item(1).setTextContent(newName);
								list.item(3).setTextContent(newX);
								list.item(5).setTextContent(newY);
							}
						}
					}
				}
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			DOMSource ds = new DOMSource(doc);
			StreamResult sr = new StreamResult(new File("locations.xml"));
			t.transform(ds, sr);
			
		} catch (TransformerException | 
				ParserConfigurationException | 
				SAXException | 
				IOException tfe) {
			tfe.printStackTrace();
		}
	}
	
}
