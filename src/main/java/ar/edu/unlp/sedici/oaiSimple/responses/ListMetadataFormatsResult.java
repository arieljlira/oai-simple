package ar.edu.unlp.sedici.oaiSimple.responses;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ar.edu.unlp.sedici.oaiSimple.model.MetadataFormatDefinition;
import ar.edu.unlp.sedici.oaiSimple.requests.ListMetadataFormatsRequest;
import ar.edu.unlp.sedici.xmlutils.XmlProcessingException;
import ar.edu.unlp.sedici.xmlutils.XmlResource;

public class ListMetadataFormatsResult extends VerbResult {
	
	public ListMetadataFormatsResult(ListMetadataFormatsRequest request, XmlResource resource) {
		super(request, resource);
	}
	
	public List<MetadataFormatDefinition> getMetadataFormats() throws XmlProcessingException, IOException{
		List<MetadataFormatDefinition> mfDefs = new LinkedList<MetadataFormatDefinition>();
		NodeList mfNodes = this.xmlResource.evalXpathToNodeList("//oai:metadataFormat");
		for (int i = 0; i < mfNodes.getLength(); i++) {
			Node ithNode = mfNodes.item(i);
			mfDefs.add(new MetadataFormatDefinition((Element) ithNode));
		}
		return mfDefs;
	};
	
}
