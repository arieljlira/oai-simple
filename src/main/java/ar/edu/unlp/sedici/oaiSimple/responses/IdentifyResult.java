package ar.edu.unlp.sedici.oaiSimple.responses;

import java.io.IOException;

import junit.framework.Assert;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import ar.edu.unlp.sedici.oaiSimple.model.IdentifyDefinition;
import ar.edu.unlp.sedici.oaiSimple.requests.IdentifyRequest;
import ar.edu.unlp.sedici.xmlutils.XmlProcessingException;
import ar.edu.unlp.sedici.xmlutils.XmlResource;

public class IdentifyResult extends VerbResult {
	
    public IdentifyResult(IdentifyRequest request ,XmlResource resource) {
		super(request, resource);
	}
    
    public IdentifyDefinition getDefinition() throws XmlProcessingException, IOException{
    	Node ifyNode = this.xmlResource.evalXpathToNode("//oai:Identify");
    	Assert.assertNotNull(ifyNode);
    	return new IdentifyDefinition((Element) ifyNode);
    }
}
