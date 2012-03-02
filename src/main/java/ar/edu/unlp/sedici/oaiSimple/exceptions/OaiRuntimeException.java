package ar.edu.unlp.sedici.oaiSimple.exceptions;

public class OaiRuntimeException extends Exception {

	private static final long serialVersionUID = 1L;

	public enum ExceptionCause { 
		malformed_xml,io_error, 
		connection_timeout, server_not_found, connection_error
	} ;
	
	private ExceptionCause exceptionCause;
	
	public OaiRuntimeException(ExceptionCause exceptionCause, String message, Throwable cause) {
		super(message, cause);
		this.exceptionCause = exceptionCause;
	}
	
	public ExceptionCause getExceptionCause() {
		return exceptionCause;
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("ErrorCode: ").append(this.exceptionCause)
		.append(" - ").append(this.getMessage());
		return buf.toString();
	}

}
