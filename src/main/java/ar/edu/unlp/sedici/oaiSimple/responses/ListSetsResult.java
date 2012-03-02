package ar.edu.unlp.sedici.oaiSimple.responses;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ar.edu.unlp.sedici.oaiSimple.model.ResumptionTokenDefinition;
import ar.edu.unlp.sedici.oaiSimple.model.SetDefinition;
import ar.edu.unlp.sedici.oaiSimple.requests.ListSetsRequest;
import ar.edu.unlp.sedici.xmlutils.XmlProcessingException;
import ar.edu.unlp.sedici.xmlutils.XmlResource;

public class ListSetsResult extends VerbResult {
	
	public ListSetsResult(ListSetsRequest request, XmlResource resource) {
		super(request, resource);
	}

	public List<SetDefinition> getSets() throws XmlProcessingException, IOException{
		List<SetDefinition> sets = new LinkedList<SetDefinition>();
		NodeList nodes = this.xmlResource.evalXpathToNodeList("//oai:set");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node ithNode = nodes.item(i);
			sets.add(new SetDefinition((Element) ithNode));
		}
		return sets;
	};
	
	public ResumptionTokenDefinition getResumptionToken() throws XmlProcessingException, IOException{
		Node rtNode = this.xmlResource.evalXpathToNode("//oai:resumptionToken");
		if (rtNode  == null)
			return null;
		else
			return new ResumptionTokenDefinition((Element) rtNode);
	}
	
	
}
