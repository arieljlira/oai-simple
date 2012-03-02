package ar.edu.unlp.sedici.oaiSimple.requests;

import java.util.Map;

import ar.edu.unlp.sedici.oaiSimple.responses.IdentifyResult;
import ar.edu.unlp.sedici.xmlutils.XmlResource;

public class IdentifyRequest extends VerbRequest<IdentifyResult> {

	@Override
	public String getVerb() {
		return VERB__IDENTIFY;
	}

	@Override
	protected void populateParams(Map<String, String> params) {
	}

	@Override
	public IdentifyResult getResponseInstance(XmlResource resource) {
		return new IdentifyResult(this, resource);
	}
	
}
