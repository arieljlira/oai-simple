package ar.edu.unlp.sedici.oaiSimple.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HeaderDefinition extends OaiDefinition{
	private String identifier = null;// anyURI
	private Date datestamp = null;
	private List<String> setSpecs = new LinkedList<String>();//
	private String status = null; //@status=deleted
	
	public HeaderDefinition(Element element) {
		
		if (element.hasAttribute("status")) 
			this.status = this._getString(element.getAttributeNode("status"));
		
		NodeList childs = element.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node current = childs.item(i);
			if (current.getNodeType() != Node.ELEMENT_NODE) continue;
			
			if ("identifier".equals(current.getNodeName()))
				this.identifier = this._getString(current);
			else if ("datestamp".equals(current.getNodeName()))
				this.datestamp = this._getDate(current);
			else if ("setSpec".equals(current.getNodeName()))
				this.setSpecs.add(this._getString(current));
		}	
	}
	
	
	public List<String> getSetSpecs() {
		return setSpecs;
	}
	
	public Date getDatestamp() {
		return datestamp;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public String getStatus() {
		return status;
	}
	
}