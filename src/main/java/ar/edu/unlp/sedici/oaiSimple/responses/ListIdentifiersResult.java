package ar.edu.unlp.sedici.oaiSimple.responses;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ar.edu.unlp.sedici.oaiSimple.model.HeaderDefinition;
import ar.edu.unlp.sedici.oaiSimple.model.ResumptionTokenDefinition;
import ar.edu.unlp.sedici.oaiSimple.requests.ListIdentifiersRequest;
import ar.edu.unlp.sedici.xmlutils.XmlProcessingException;
import ar.edu.unlp.sedici.xmlutils.XmlResource;

public class ListIdentifiersResult extends VerbResult {

	public ListIdentifiersResult(ListIdentifiersRequest request, XmlResource resource) {
		super(request, resource);
	}
	
	
	public List<HeaderDefinition> getHeaders() throws XmlProcessingException, IOException{
		List<HeaderDefinition> headers = new LinkedList<HeaderDefinition>();
		NodeList headerNodes = this.xmlResource.evalXpathToNodeList("//oai:header");
		for (int i = 0; i < headerNodes.getLength(); i++) {
			Node ithHeaderNode = headerNodes.item(i);
			headers.add(new HeaderDefinition((Element) ithHeaderNode));
		}
		return headers;
	};
	
	public ResumptionTokenDefinition getResumptionToken() throws XmlProcessingException, IOException{
		Node rtNode = this.xmlResource.evalXpathToNode("//oai:resumptionToken");
		if (rtNode  == null)
			return null;
		else
			return new ResumptionTokenDefinition((Element) rtNode);
	}
	
	
}
