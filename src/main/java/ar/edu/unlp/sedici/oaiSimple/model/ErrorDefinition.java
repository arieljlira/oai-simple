package ar.edu.unlp.sedici.oaiSimple.model;



public class ErrorDefinition extends OaiDefinition{
	private OaiErrorType code = null;
	private String message = null;
	
	public ErrorDefinition(OaiErrorType errorType, String errorMessage) {
		super();
		if(errorType == null)
			this.code = OaiErrorType.unknown;
		else
			this.code = errorType;
		
		this.message = errorMessage;
	}
	
	public OaiErrorType getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
}