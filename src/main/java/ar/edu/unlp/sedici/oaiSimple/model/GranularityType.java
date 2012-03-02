package ar.edu.unlp.sedici.oaiSimple.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import junit.framework.Assert;

public enum GranularityType {

    YYYYMMDD, YYYYMMDDTHHMMSSZ;
    
    public static GranularityType fromString(String name) {
		Assert.assertNotNull(name);
		name = name.replaceAll("-", "").replaceAll(":", "");
		return GranularityType.valueOf(name.toUpperCase());
	}

    public String formatDate(Date date){
		if (date == null)
			return null;
		
		SimpleDateFormat oaiFormatter = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		try {
			//workaround: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4965317
			oaiFormatter.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		} catch(Exception e) {
		}
		
		String oai_date = oaiFormatter.format(date);
		if (GranularityType.YYYYMMDDTHHMMSSZ.equals(this))
			return oai_date.substring(0,10) + "T00:00:01Z";
		else
			return oai_date.substring(0,10);
	}
	
}
