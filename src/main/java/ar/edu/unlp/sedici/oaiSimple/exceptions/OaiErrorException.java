package ar.edu.unlp.sedici.oaiSimple.exceptions;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unlp.sedici.oaiSimple.model.ErrorDefinition;

public class OaiErrorException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private List<ErrorDefinition> errors; 
	private boolean thrownByServer; 
	
	public OaiErrorException(ErrorDefinition error, boolean wasThrownByServer) {
		this.errors = new ArrayList<ErrorDefinition>();
		this.errors.add(error);
		this.thrownByServer = wasThrownByServer; 
	}
	
	public OaiErrorException(List<ErrorDefinition> errors, boolean wasThrownByServer) {
		this.errors = errors;
		this.thrownByServer = wasThrownByServer; 
	}

	public List<ErrorDefinition> getErrors() {
		return this.errors;
	}

	public boolean isThrownByServer() {
		return thrownByServer;
	}
	
	public boolean isThrownByClient() {
		return !thrownByServer;
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for(ErrorDefinition error : this.errors) {
			buf.append("ErrorCode: ").append(error.getCode())
				.append(" - ").append(this.thrownByServer?"Server":"Client")
				.append(" said: ").append(error.getMessage())
				.append("\n");
		}
		return buf.toString();
	}

	public ErrorDefinition getFirstError() {
		return this.errors.get(0);
	}
	
}
