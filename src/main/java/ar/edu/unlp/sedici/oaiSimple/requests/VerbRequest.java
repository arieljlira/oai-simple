package ar.edu.unlp.sedici.oaiSimple.requests;

import java.util.HashMap;
import java.util.Map;

import ar.edu.unlp.sedici.oaiSimple.exceptions.OaiErrorException;
import ar.edu.unlp.sedici.oaiSimple.model.ErrorDefinition;
import ar.edu.unlp.sedici.oaiSimple.model.OaiErrorType;
import ar.edu.unlp.sedici.oaiSimple.responses.VerbResult;
import ar.edu.unlp.sedici.xmlutils.XmlResource;

public abstract class VerbRequest <T extends VerbResult> {
	public static final String PARAM_verb = "verb";
	protected static final String PARAM_identifier = "identifier";
	protected static final String PARAM_metadataPrefix = "metadataPrefix";
	protected static final String PARAM_from = "from";
	protected static final String PARAM_until = "until";
	protected static final String PARAM_set = "set";
	protected static final String PARAM_resumptionToken = "resumptionToken";
	
	protected static final String VERB__IDENTIFY = "Identify";
	protected static final String VERB__LIST_RECORDS = "ListRecords";
	protected static final String VERB__LIST_SETS = "ListSets";
	protected static final String VERB__LIST_IDENTIFIERS = "ListIdentifiers";
	protected static final String VERB__GET_RECORD = "GetRecord";
	protected static final String VERB__LIST_METADATA_FORMATS = "ListMetadataFormats";
	
	public final Map<String, String> getRequestParams(){
		Map<String, String> params = new HashMap<String, String>();
		this.populateParams(params);
		return params;
			
	}
	
	protected final void throwOaiError(OaiErrorType errorType, String errorMessage) throws OaiErrorException{
		throw new OaiErrorException(new ErrorDefinition(errorType, errorMessage), false);
	}

	public abstract String getVerb();
	protected abstract void populateParams(Map<String, String> params); 
	
	public abstract T getResponseInstance(XmlResource resource); 
	
}
