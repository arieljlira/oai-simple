package ar.edu.unlp.sedici.oaiSimple.responses;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ar.edu.unlp.sedici.oaiSimple.model.RecordDefinition;
import ar.edu.unlp.sedici.oaiSimple.model.ResumptionTokenDefinition;
import ar.edu.unlp.sedici.oaiSimple.requests.VerbRequest;
import ar.edu.unlp.sedici.xmlutils.XmlProcessingException;
import ar.edu.unlp.sedici.xmlutils.XmlResource;

public class ListRecordsResult extends VerbResult {
	
	public ListRecordsResult(VerbRequest<ListRecordsResult> request, XmlResource resource) {
		super(request, resource);
	}

	public List<RecordDefinition> getRecords() throws XmlProcessingException, IOException{
		List<RecordDefinition> records = new LinkedList<RecordDefinition>();
		NodeList recordNodes = this.xmlResource.evalXpathToNodeList("//oai:record");
		for (int i = 0; i < recordNodes.getLength(); i++) {
			Node ithRecordNode = recordNodes.item(i);
			records.add(new RecordDefinition((Element) ithRecordNode));
		}
		return records;
	};
	
	public int getRecordCount() throws XmlProcessingException, IOException{
		return this.xmlResource.evalXpathToNumber("count(//oai:record)").intValue();
	}
	
	public ResumptionTokenDefinition getResumptionToken() throws XmlProcessingException, IOException{
		Node rtNode = this.xmlResource.evalXpathToNode("//oai:resumptionToken");
		if (rtNode  == null)
			return null;
		else if(rtNode.getTextContent().isEmpty())
			return null;
		else
			return new ResumptionTokenDefinition((Element) rtNode);
	}

	public XmlResource getXmlResource() {
		return this.xmlResource;
	}
	
}