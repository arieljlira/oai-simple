package ar.edu.unlp.sedici.oaiSimple.requests;

import java.util.Map;

import ar.edu.unlp.sedici.oaiSimple.responses.ListMetadataFormatsResult;
import ar.edu.unlp.sedici.xmlutils.XmlResource;

public class ListMetadataFormatsRequest extends VerbRequest<ListMetadataFormatsResult> {

	public ListMetadataFormatsRequest() {
	}
	
	@Override
	protected void populateParams(Map<String, String> params) {
	}
	
	@Override
	public String getVerb() {
		return VERB__LIST_METADATA_FORMATS;
	}

	@Override
	public ListMetadataFormatsResult getResponseInstance(XmlResource resource) {
		return new ListMetadataFormatsResult(this, resource);
	}
	
}
