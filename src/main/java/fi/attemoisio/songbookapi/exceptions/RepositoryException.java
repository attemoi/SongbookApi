package fi.attemoisio.songbookapi.exceptions;

import javax.ws.rs.WebApplicationException;

import fi.attemoisio.songbookapi.errorhandling.ApiError;

public class RepositoryException extends WebApplicationException {

	private static final long serialVersionUID = 9115891644416593875L;

	private ApiError error;

	public ApiError getErrorCode() {
		return error;
	}

	public void setErrorCode(ApiError error) {
		this.error = error;
	}
	
	public RepositoryException() {}

	public RepositoryException(ApiError error, Throwable cause) {
		super(error.getDescription(), cause);
		this.error = error;
		
	}

}
