package ar.edu.unlp.sedici.oaiSimple.model;

import java.io.StringWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ar.edu.unlp.sedici.xmlutils.XmlUtils;

public class IdentifyDefinition extends OaiDefinition{

	private String repositoryName = null;
	private String baseURL = null;
	private String protocolVersion = null;//final 2.0
	private List<String> adminEmails = new LinkedList<String>();
	private Date earliestDatestamp = null;
	private DeletedRecord deletedRecord = null;
	private GranularityType granularity = GranularityType.YYYYMMDD;
	private List<String> compressions = new LinkedList<String>();
    private List<String> descriptions = new LinkedList<String>();
    private List<String> friends = new LinkedList<String>();
    
	public IdentifyDefinition(Element element) {
	
		NodeList childs = element.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node current = childs.item(i);
			if (current.getNodeType() != Node.ELEMENT_NODE) continue;
			
			if ("repositoryName".equals(current.getNodeName()))
				this.repositoryName = this._getString(current);
			else if ("baseURL".equals(current.getNodeName()))
				this.baseURL = this._getString(current);
			else if ("protocolVersion".equals(current.getNodeName()))
				this.protocolVersion = this._getString(current);
			else if ("adminEmail".equals(current.getNodeName()))
				this.adminEmails.add(this._getString(current));
			else if ("earliestDatestamp".equals(current.getNodeName()))
				this.earliestDatestamp = this._getDate(current);
			else if ("deletedRecord".equals(current.getNodeName()))
				this.deletedRecord = DeletedRecord.fromString(this._getString(current));
			else if ("granularity".equals(current.getNodeName()))
				this.granularity= GranularityType.fromString(this._getString(current));
			else if ("compression".equals(current.getNodeName()))
				this.compressions.add(this._getString(current));
			else if ("description".equals(current.getNodeName())) {
				// Definir y aplicar extractores del elemento <description> de OAI
				// Separa los OaiFriends
				NodeList friendsNodes = ((Element) current).getElementsByTagName("friends");
				if (friendsNodes.getLength() != 0) {
					NodeList friendsURLNodes = ((Element)friendsNodes.item(0)).getElementsByTagName("baseURL");
					for(int index = 0 ; index < friendsURLNodes.getLength() ; index++ ) {
						this.friends.add(this._getString(friendsURLNodes.item(index)));
					}
				} else {
					// Description
					try {
						StringWriter writer = new StringWriter();
						XmlUtils.writeTo(XmlUtils.getSource(current), XmlUtils.getResult(writer));
						this.descriptions.add(writer.toString());
					} catch (TransformerException e) {
						//Ignoro la exception
					}
				}
			} 
		}
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public String getProtocolVersion() {
		return protocolVersion;
	}

	public List<String> getAdminEmails() {
		return adminEmails;
	}

	public Date getEarliestDatestamp() {
		return earliestDatestamp;
	}

	public DeletedRecord getDeletedRecord() {
		return deletedRecord;
	}

	public GranularityType getGranularity() {
		return granularity;
	}

	public List<String> getCompressions() {
		return compressions;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}
	
	public List<String> getFriends() {
		return friends;
	}
	
}
