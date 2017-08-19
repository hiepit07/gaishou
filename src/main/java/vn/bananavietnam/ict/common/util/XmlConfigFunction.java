package vn.bananavietnam.ict.common.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vn.bananavietnam.ict.common.component.XmlConfig;

/**
 * Define all functions for read file xml
 * 
 * @author PhongBui
 *
 */
public class XmlConfigFunction {
	/** global object {XmlConfig} */
	private static XmlConfig xmlConfig;
	private static String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePathTmp) {
		filePath = filePathTmp;
	}

	/**
	 * Initialize XmlConfig object
	 */
	public void initXmlConfig() {
		try {
			File xmlFile = new File(getFilePath());
			// Check the time the file was last modified
			if (xmlConfig != null && xmlFile.lastModified() == xmlConfig.getLastModified()) {
				return;
			}
			xmlConfig = new XmlConfig();
			xmlConfig.setLastModified(xmlFile.lastModified());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			// Return new NodeList object containing all the matched xmlConfig tag.
			NodeList nodes = doc.getElementsByTagName("xmlConfig");
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				Element element = (Element) node;
				NodeList mode_name = element.getElementsByTagName("app_name");
				for (int j = 0; j < mode_name.getLength(); j++) {
					xmlConfig.mode.put(getValue("app_name", element, j), getValue("screen_id", element, j));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getValue(String tag, Element element, int index) {		
		NodeList nodes = element.getElementsByTagName(tag).item(index).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	public String[] getScreenList(String app_name) {
		initXmlConfig();
		return xmlConfig.getScreenList(app_name);
	}
}