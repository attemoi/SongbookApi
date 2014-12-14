package fi.attemoisio.songbookapi.model.error;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ValidationError")
public class ValidationErrorResponse {
	
	private String property;
	private String message;
	private int code;
	private List<ValidationError> errors;
	
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
	
	@XmlElement 
	@XmlElementWrapper(name = "errors") 
	public List<ValidationError> getErrors() {
		return errors;
	}
	public void setErrors(List<ValidationError> errors) {
		this.errors = errors;
	}
	
	@XmlElement(name = "code") 
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
}