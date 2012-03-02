package ar.edu.unlp.sedici.oaiSimple.model;

import java.util.Date;

import org.w3c.dom.Element;

public class ResumptionTokenDefinition extends OaiDefinition{
	
	private String value = null; //required
	private Long cursor = null;//optional, cursor >= 0
	private Date expirationDate = null; //optional
	private Long completeListSize = null;//optional, completeListSize > 0 
 
    public ResumptionTokenDefinition(Element element) {
    	
    	if (element.hasAttribute("cursor")) 
			this.cursor = this._getLong(element.getAttributeNode("cursor"));
		
    	if (element.hasAttribute("expirationDate")) 
			this.expirationDate = this._getDate(element.getAttributeNode("expirationDate"));
		
    	if (element.hasAttribute("completeListSize")) 
			this.completeListSize = this._getLong(element.getAttributeNode("completeListSize"));
		
		this.value = this._getString(element);
	}

	public String getValue() {
		return value;
	}

	public Long getCursor() {
		return cursor;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public Long getCompleteListSize() {
		return completeListSize;
	}
    
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("Resumption Token: ").append(this.value);
		if (expirationDate!= null)
			buf.append("(Expires: ").append(this.expirationDate).append(")");
		if (completeListSize != null)
			buf.append(", has records ").append(cursor).append(" of ").append(completeListSize);
		return buf.toString();
	}
    
}

