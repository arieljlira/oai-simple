package ar.edu.unlp.sedici.oaiSimple.requests;

import java.util.Map;

import ar.edu.unlp.sedici.oaiSimple.exceptions.OaiErrorException;
import ar.edu.unlp.sedici.oaiSimple.model.OaiErrorType;
import ar.edu.unlp.sedici.oaiSimple.responses.ListSetsResult;
import ar.edu.unlp.sedici.xmlutils.XmlResource;

public class ListSetsRequest extends VerbRequest<ListSetsResult> {
	private String resumptionToken;
	
	public ListSetsRequest(String resumptionToken) throws OaiErrorException {
		if (resumptionToken == null)
			this.throwOaiError(OaiErrorType.badArgument, "El campo resumptionToken no puede ser null");
		if ("".equals(resumptionToken.trim()))
				this.throwOaiError(OaiErrorType.badResumptionToken, "The required argument 'resumptionToken' is missing in the request");
		this.resumptionToken = resumptionToken;
	}
	
	public ListSetsRequest() throws OaiErrorException {
	}
	
	@Override
	protected void populateParams(Map<String, String> params) {
		if (this.resumptionToken != null){
			params.put(PARAM_resumptionToken, this.resumptionToken);
		}
	}
	
	@Override
	public ListSetsResult getResponseInstance(XmlResource resource) {
		return new ListSetsResult(this, resource);
	}
	
	@Override
	public String getVerb() {
		return VERB__LIST_SETS;
	}
	
	public String getResumptionToken() {
		return resumptionToken;
	}

	
}
