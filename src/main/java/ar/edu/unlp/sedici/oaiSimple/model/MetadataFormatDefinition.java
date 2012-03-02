package ar.edu.unlp.sedici.oaiSimple.model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MetadataFormatDefinition extends OaiDefinition {
	private String metadataPrefix = null; // "[A-Za-z0-9\-_\.!~\*'\(\)]+
	private String schema = null; // anyURI
	private String metadataNamespace = null; // anyURI

	public MetadataFormatDefinition(Element element) {

		NodeList childs = element.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node current = childs.item(i);
			if (current.getNodeType() != Node.ELEMENT_NODE)
				continue;

			if ("metadataPrefix".equals(current.getNodeName()))
				this.metadataPrefix = this._getString(current);
			else if ("metadataNamespace".equals(current.getNodeName()))
				this.metadataNamespace = this._getString(current);
			else if ("schema".equals(current.getNodeName()))
				this.schema = this._getString(current);
		}

	}

	public String getMetadataPrefix() {
		return metadataPrefix;
	}

	public String getSchema() {
		return schema;
	}

	public String getMetadataNamespace() {
		return metadataNamespace;
	}

	
}