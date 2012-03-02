package ar.edu.unlp.sedici.oaiSimple.model;


public enum OaiErrorType {

	unknown, cannotDisseminateFormat, idDoesNotExist, badArgument, badVerb, 
	noMetadataFormats, badResumptionToken, noSetHierarchy, noRecordsMatch;
	// noRecordsMatch no se considera como error. Esta aca solo para poder discriminarlo
	
	public static OaiErrorType fromString(String name) {
		//nos e si hay que manejar algun caso en minuscula o sth like this
		return OaiErrorType.valueOf(name);
	}

	/**
     * Retorna true si el name es un OaiErrorType valido 
	 */
	public static boolean isAllowed(String name) {
		//Hace el intento de creacion del Enum. Si salta una IllegalArgumentException, asumimos que no es un valor valido para este enum
		try {
			OaiErrorType.fromString(name);
			return true;
		} catch(IllegalArgumentException e) {
			return false;
		}
	}
	
	public boolean matches(String name){
		try {
			return this.equals(OaiErrorType.fromString(name));
		} catch(IllegalArgumentException e) {
			return false;
		}
	}
	
}
