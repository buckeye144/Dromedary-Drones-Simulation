import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
	
	NodeList items;
	Document doc;
	
	public XML () {
		try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.parse("locations.xml");
		items = doc.getElementsByTagName("location");
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}

	public void remove()  {
		
	}
	
	public void add(Object item) {
		
	}
	
	public void edit(String locName, String attr, String newAttr) {
		
		try {
			boolean test = false;
			outerloop:
			for (int i = 0; i < items.getLength(); i++) {
				Node p = items.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE) {
					Element food = (Element) p;
					NodeList list = food.getChildNodes();
					for (int j = 0; j < list.getLength(); j++) {
						Node n = list.item(j);
						if (n.getNodeType() == Node.ELEMENT_NODE) {
							Element attribute = (Element) n;
							if(attribute.getTextContent().matches(locName) || test) {
								test = true;
								if(attr.equals(n.getNodeName())) {
									attribute.setTextContent(newAttr);
									test = false;
									break outerloop;
								}
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
			
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
