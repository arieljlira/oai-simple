package ar.edu.unlp.sedici.oaiSimple.responses;

import java.io.IOException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import ar.edu.unlp.sedici.oaiSimple.model.RecordDefinition;
import ar.edu.unlp.sedici.oaiSimple.requests.GetRecordRequest;
import ar.edu.unlp.sedici.xmlutils.XmlProcessingException;
import ar.edu.unlp.sedici.xmlutils.XmlResource;

public class GetRecordResult extends VerbResult {

	public GetRecordResult(GetRecordRequest request, XmlResource resource) {
		super(request, resource);
	}

	public RecordDefinition getRecord() throws XmlProcessingException, IOException{
		Node recordNode = this.xmlResource.evalXpathToNode("//oai:record");
		return new RecordDefinition((Element) recordNode);
	};
	
}
