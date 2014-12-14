package fi.attemoisio.songbookapi.model.error;

import javax.xml.bind.annotation.XmlElement;

public class ValidationError {
	
	private String property;
	private String message;
	
	@XmlElement(name = "property")
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	
	@XmlElement(name = "message")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
