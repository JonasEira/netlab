package main;

import java.net.HttpURLConnection;
import java.net.URL;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class JavaModel implements DataInterface {

	URL url;
	HttpURLConnection con;
	private String baseURL;
	private Document doc;

	public JavaModel() {
		baseURL = "https://api.met.no/weatherapi/locationforecast/1.9/";
		populateLocations();
	}

	private void populateLocations() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuilder xmlStringBuilder = new StringBuilder();
		xmlStringBuilder.append("<?xml version=\"1.0\"?> <places> </places>");
		FileInputStream input = null;
		try {
			input = new FileInputStream(new File("places.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			doc = builder.parse(input);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getRequestString(String name) {
		Element root = doc.getDocumentElement();
		System.out.println(root.);
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList locality = doc.getElementsByTagName("locality");
		System.out.println("----------------------------");

		for (int temp = 0; temp < locality.getLength(); temp++) {
			Node location = locality.item(temp);
			NamedNodeMap data = locality.getAttributes();
			Node localAttr = data.getNamedItem("name");
			if(localAttr.getNodeValue() == name) {
				System.out.println("Town name: " + name);
			}
		}
		return "";
	}

	@Override
	public void datarequest(String name) throws IOException {

		url = new URL(baseURL + name);
		con = (HttpURLConnection) url.openConnection();
		getRequestString(name);
		synchronized (con) {
			// Do some updates

			// notify view

		}
	}

}
