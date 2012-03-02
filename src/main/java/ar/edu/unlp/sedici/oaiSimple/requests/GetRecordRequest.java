package ar.edu.unlp.sedici.oaiSimple.requests;

import java.util.Map;

import ar.edu.unlp.sedici.oaiSimple.exceptions.OaiErrorException;
import ar.edu.unlp.sedici.oaiSimple.model.OaiErrorType;
import ar.edu.unlp.sedici.oaiSimple.responses.GetRecordResult;
import ar.edu.unlp.sedici.xmlutils.XmlResource;

public class GetRecordRequest extends VerbRequest<GetRecordResult> {
	private String record;
	private String metadataPrefix;
	
	public GetRecordRequest(String record,String metadataPrefix) throws OaiErrorException {
		if (record== null)
			this.throwOaiError(OaiErrorType.badArgument, "El campo record no puede ser null");
		if ("".equals(record.trim()))
				this.throwOaiError(OaiErrorType.badResumptionToken, "The required argument 'record' is missing in the request");
		this.record = record;
		
		if (metadataPrefix== null)
			this.throwOaiError(OaiErrorType.badArgument, "El campo metadataPrefix no puede ser null");
		
		this.metadataPrefix =metadataPrefix; 
		
	}
	
	@Override
	protected void populateParams(Map<String, String> params) {
		params.put(PARAM_identifier, this.record);
		params.put(PARAM_metadataPrefix, this.metadataPrefix);
	}
	
	@Override
	public GetRecordResult getResponseInstance(XmlResource resource) {
		return new GetRecordResult(this, resource);
	}
	
	@Override
	public String getVerb() {
		return VERB__GET_RECORD;
	}
	
	public String getRecord() {
		return record;
	}
	
}
