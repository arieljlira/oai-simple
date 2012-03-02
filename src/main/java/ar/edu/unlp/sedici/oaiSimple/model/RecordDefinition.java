package ar.edu.unlp.sedici.oaiSimple.model;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RecordDefinition {
	private HeaderDefinition header = null;
	private Element metadata = null; 
	private List<Element> abouts = new LinkedList<Element>();
	
	public RecordDefinition(Element element) {
		NodeList childs = element.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node current = childs.item(i);
			if (current.getNodeType() != Node.ELEMENT_NODE) continue;
			
			if ("header".equals(current.getNodeName()))
				this.header = new HeaderDefinition((Element) current);
			else if ("metadata".equals(current.getNodeName()))
				this.metadata = (Element) current;
			else if ("about".equals(current.getNodeName()))
				this.abouts.add((Element) current);
		}
	}

	public HeaderDefinition getHeader() {
		return header;
	}

	public Element getMetadata() {
		return metadata;
	}

	public List<Element> getAbouts() {
		return abouts;
	}
	
	
}