package fi.attemoisio.songbookapi.exceptionhandling;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EntityViolationError")
public class CustomConstraintViolation {
	
	private String invalidValue;
	private String property;
	private String message;
	
	@XmlElement(name = "invalidValue")
	public String getInvalidValue() {
		return invalidValue;
	}
	public void setInvalidValue(String invalidValue) {
		this.invalidValue = invalidValue;
	}
	
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