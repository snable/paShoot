package ru.spravka42.paShoot;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PresetParser {

	public ArrayList<Shoot> parse(InputStream in) throws IOException {
        ArrayList<Shoot> shoots = new ArrayList<Shoot>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
        	Document document = builder.parse(in);
	        NodeList picts = document.getElementsByTagName("pict");
	        for (int i = 0; i < picts.getLength(); i++) {
	            NamedNodeMap attrs = picts.item(i).getAttributes();
	            Attr yaw = (Attr)attrs.item(0);
	            Attr pitch = (Attr)attrs.item(1);
	            shoots.add(new Shoot(yaw.getValue(), pitch.getValue()));
	        }
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			in.close();
		}
		return shoots;
	}
}
