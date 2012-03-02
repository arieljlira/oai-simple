package ar.edu.unlp.sedici.oaiSimple.model;

import junit.framework.Assert;


public enum DeletedRecord {

	NO, TRANSIENT, PERSISTENT;
	
	public static DeletedRecord fromString(String name) {
		Assert.assertNotNull(name);
		return DeletedRecord.valueOf(name.toUpperCase());
	}

}
