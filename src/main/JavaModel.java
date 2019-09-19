package main;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class JavaModel {

	URL url;
	HttpURLConnection con;
	private String baseURL;
	private Document doc;
	private ArrayList<String> coordinates;
	private ArrayList<DataInterface> views;
	private String reply;

	public JavaModel() {
		baseURL = "https://api.met.no/weatherapi/locationforecast/1.9/";
		populateLocations();
		views = new ArrayList<DataInterface>();
	}

	private void populateLocations() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}

		FileInputStream input = null;
		try {
			input = new FileInputStream(new File("places.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			doc = builder.parse(input);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Parsing xml-document with coordinates
	 */
	public ArrayList<String> getCoordinateList(String name) {
		Element root = doc.getDocumentElement();

		NodeList locality = doc.getElementsByTagName("locality");

		boolean thisNode = false;
		ArrayList<String> result = new ArrayList<String>();
		for (int temp = 0; temp < locality.getLength(); temp++) {
			Node n1 = locality.item(temp);
			if (n1.hasAttributes()) {
				NamedNodeMap attributes = n1.getAttributes();
				for (int i = 0; i < attributes.getLength(); i++) {
					Node nAttr = attributes.item(i);
					if (nAttr.getNodeValue().trim().equalsIgnoreCase(name.trim())) {
						thisNode = true;
					} else {
						thisNode = false;
					}
				}
			}

			if (!thisNode) {
				continue;
			} else {
				NodeList children = n1.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					Node n2 = children.item(i);
					if (n2.hasAttributes()) {
						NamedNodeMap attributes = n2.getAttributes();
						for (int i1 = 0; i1 < attributes.getLength(); i1++) {
							Node nAttr = attributes.item(i1);
							result.add(nAttr.getNodeValue());
						}
					}
				}
			}
		}
		return result;
	}

	public void addDatainterface(DataInterface data) {
		this.views.add(data);
	}

	/*
	 * Callback procedure
	 */
	public void notifyViews() {
		for (DataInterface d : views) {
			d.updatedData();
		}
	}

	public void dataRequest(String name) {
		ArrayList<String> coords;
		String request = null;
		try {
			coords = getCoordinateList(name);
			this.setCoordinates(coords);
			/*
			 * Update the string from the website
			 */
			request = "?lat=" + coordinates.get(1) + "&lon=" + coordinates.get(2) + "&msl=" + coordinates.get(0);
			baseURL = "https://api.met.no/weatherapi/locationforecast/1.9/";
			url = new URL(baseURL + request);
			con = (HttpURLConnection) url.openConnection();
			synchronized (con) {
				int status = con.getResponseCode();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer content = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine);
				}
				reply = content.toString();
				in.close();
				con.disconnect();
				this.notifyViews();
			}

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void setCoordinates(ArrayList<String> coords) {
		this.coordinates = coords;
	}

	/*
	 * Parsing xml into DOM model
	 */
	private Document parseXML(String xmlData) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		Document xml = null;
		try {
			InputStream input = new ByteArrayInputStream(xmlData.getBytes(Charset.forName("UTF-8")));
			xml = builder.parse(input);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xml;
	}

	public String getReply() {
		return reply;
	}

/** Recursion Test
 * 
 * 
	public String parseNode(Node n) {
		NodeList children = n.getChildNodes();
		System.out.println(children.getLength());
		System.out.println(n.getAttributes().getLength());
		for (int n1 = 0; n1 < children.getLength(); n1++) {
			System.out.println(" - ");
			return children.item(n1).getNodeName() + children.item(n1).getNodeValue() + parseNode(children.item(n1));
		}
		return "";
	}**/
}
