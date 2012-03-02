package ar.edu.unlp.sedici.oaiSimple.model;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SetDefinition extends OaiDefinition{
	private String setSpec = null; // <pattern
					// value="([A-Za-z0-9\-_\.!~\*'\(\)])+(:[A-Za-z0-9\-_\.!~\*'\(\)]+)*"/>
	private String setName = null;
	private List<String> setDescriptions = new LinkedList<String>(); 
	//o puede ser un xml 
	
	public SetDefinition(Element element) {
		
		NodeList childs = element.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node current = childs.item(i);
			if (current.getNodeType() != Node.ELEMENT_NODE) continue;
			
			if ("setSpec".equals(current.getNodeName()))
				this.setSpec = this._getString(current);
			else if ("setName".equals(current.getNodeName()))
				this.setName = this._getString(current);
			else if ("setDescription".equals(current.getNodeName()))
				this.setDescriptions.add(this._getString(current));
		}	
	}

	public String getSetSpec() {
		return setSpec;
	}

	public String getSetName() {
		return setName;
	}

	public List<String> getSetDescriptions() {
		return setDescriptions;
	}
	
	
	
}