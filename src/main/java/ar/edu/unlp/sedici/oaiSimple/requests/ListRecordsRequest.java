package ar.edu.unlp.sedici.oaiSimple.requests;

import java.util.Date;
import java.util.Map;

import ar.edu.unlp.sedici.oaiSimple.exceptions.OaiErrorException;
import ar.edu.unlp.sedici.oaiSimple.model.GranularityType;
import ar.edu.unlp.sedici.oaiSimple.model.OaiErrorType;
import ar.edu.unlp.sedici.oaiSimple.responses.ListRecordsResult;
import ar.edu.unlp.sedici.xmlutils.XmlResource;

public class ListRecordsRequest extends VerbRequest<ListRecordsResult> {
	private Date from;
	private Date until;
	private String set;
	private String metadataPrefix;
	private String resumptionToken;
	private GranularityType granularity;
	
	public ListRecordsRequest(String resumptionToken) throws OaiErrorException {
		if (resumptionToken == null)
			this.throwOaiError(OaiErrorType.badArgument, "El campo resumptionToken no puede ser null");
		if ("".equals(resumptionToken.trim()))
				this.throwOaiError(OaiErrorType.badResumptionToken, "The required argument 'resumptionToken' is missing in the request");
		this.resumptionToken = resumptionToken;
	}
	
	public ListRecordsRequest(Date from, Date until, String set, String metadataPrefix, GranularityType granularity) throws OaiErrorException {
		if (metadataPrefix == null || "".equals(metadataPrefix.trim()))
			this.throwOaiError(OaiErrorType.badArgument, "The required argument 'metadataPrefix' is missing in the request.");
		
		this.from = from;
		this.until = until;
		this.set = set;
		this.metadataPrefix = metadataPrefix;
		this.granularity = granularity;
		
	}
	

	@Override
	public ListRecordsResult getResponseInstance(XmlResource resource) {
		return new ListRecordsResult(this, resource);
	}
	
	@Override
	protected void populateParams(Map<String, String> params) {
		if (this.resumptionToken != null){
			params.put(PARAM_resumptionToken, this.resumptionToken);
		}else{
			if (from != null)
				params.put(PARAM_from, granularity.formatDate(this.from));
			if (until != null)
				params.put(PARAM_until, granularity.formatDate(this.until));
			if (set != null)
				params.put(PARAM_set, this.set);
			
			params.put(PARAM_metadataPrefix, this.metadataPrefix);
		}
	}
	
	@Override
	public String getVerb() {
		return VERB__LIST_RECORDS;
	}
	
	
	public Date getFrom() {
		return from;
	}
	public Date getUntil() {
		return until;
	}
	public String getSet() {
		return set;
	}
	public String getMetadataPrefix() {
		return metadataPrefix;
	}
	public String getResumptionToken() {
		return resumptionToken;
	}
	
}
