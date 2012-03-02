package ar.edu.unlp.sedici.oaiSimple.model;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import ar.edu.unlp.sedici.xmlutils.XmlUtils;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;


abstract class OaiDefinition {

	protected OaiDefinition() {
	}

	// ver si es necesario hacer validacion asi, o conviene usar el xsd directamente 
	//public abstract void validate();
	
	
	//Estos metodos _get*(X) podrian ir al XmlUtils en algun momento, son todos util
	protected String _getString(Attr attr){
		return attr.getValue();
	}
	
	protected String _getString(Element element){
    	StringBuffer buf = new StringBuffer();
    	NodeList childs = element.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node current = childs.item(i);
			if (current.getNodeType() == Node.TEXT_NODE)
				buf.append(((Text)current).getData());
		}
		return buf.toString();
	}
	
	protected String _getString(Node element){
		if (element instanceof Element)
			return this._getString((Element)element);
		else if (element instanceof Attr)
			return this._getString((Attr)element);
		else
			return element.getNodeValue();
	}
	
	protected Date _getDate(Node current) {
		String dateStr = this._getString(current);
		Assert.assertNotNull(dateStr);
		return XmlUtils.convertDatetimeToDate(dateStr);
	}
	
	protected Long _getLong(Node current) {
		return Long.parseLong(this._getString(current));
	}

	
}
