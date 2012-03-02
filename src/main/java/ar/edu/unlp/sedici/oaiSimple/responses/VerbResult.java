package ar.edu.unlp.sedici.oaiSimple.responses;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ar.edu.unlp.sedici.oaiSimple.exceptions.OaiErrorException;
import ar.edu.unlp.sedici.oaiSimple.model.ErrorDefinition;
import ar.edu.unlp.sedici.oaiSimple.model.OaiErrorType;
import ar.edu.unlp.sedici.oaiSimple.requests.VerbRequest;
import ar.edu.unlp.sedici.xmlutils.XmlProcessingException;
import ar.edu.unlp.sedici.xmlutils.XmlResource;

public abstract class VerbResult {
	protected XmlResource xmlResource;
	
	protected VerbRequest<?> request;
	
	public static final String SCHEMA_LOCATION_V2_0 = "http://www.openarchives.org/OAI/2.0/ http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd";

	private static Map<String, String> oaiNamespaces;
	static {
		oaiNamespaces= new HashMap<String, String>();
		oaiNamespaces.put("oai", "http://www.openarchives.org/OAI/2.0/");
		//se podria agregar en algun momenntos los NS de OAI-PMH v1.1 solo si es necesario
	}

	public VerbResult(VerbRequest<?> request, XmlResource resource) {
		this.xmlResource = resource;
		this.xmlResource.addNamespaceBindings(oaiNamespaces);
		this.request = request;
	}

	public Date getResponseDate() throws XmlProcessingException, IOException{
		Calendar cal = this.xmlResource.evalXpathToCalendar("/*/oai:responseDate");
		return cal.getTime();
	}
	
	protected List<ErrorDefinition> getErrors() throws XmlProcessingException, IOException {
		List<ErrorDefinition> errors = new LinkedList<ErrorDefinition>();
		NodeList nodes = this.xmlResource.evalXpathToNodeList("//oai:error");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element errorElement = (Element) nodes.item(i);

			// Verifica que no sea un error noRecordsMatch
			OaiErrorType errorType = null;
			if (errorElement.hasAttribute("code")) {
				String errorCode = errorElement.getAttributeNode("code").getValue();
				if(!errorCode.isEmpty() && OaiErrorType.isAllowed(errorCode)) {
					errorType = OaiErrorType.fromString(errorCode);
					if(errorType.equals(OaiErrorType.noRecordsMatch))
						continue;
				}
			}
			// Obtiene el mensaje de error
			String errorMessage = errorElement.getTextContent();
			errors.add(new ErrorDefinition(errorType, errorMessage));
		}
		return errors;
	}
	
	public void throwErrors() throws OaiErrorException, XmlProcessingException, IOException {
		List<ErrorDefinition> errors = this.getErrors();
		if(errors.size() > 0)
			throw new OaiErrorException(this.getErrors(), true);
	}
	
	public VerbRequest<?> getRequest() {
		return request;
	}
}