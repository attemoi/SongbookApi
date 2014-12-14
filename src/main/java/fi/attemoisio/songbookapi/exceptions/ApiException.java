package fi.attemoisio.songbookapi.exceptions;

import fi.attemoisio.songbookapi.errorhandling.ApiError;

public class ApiException extends RuntimeException{

	private static final long serialVersionUID = -4145100011024195143L;
	
	private ApiError error;

	public ApiError getApiError() {
		return error;
	}
	
	public ApiException() {}
	
	public ApiException(ApiError error) {
		super(error.getDescription());
		this.error = error;
	}
	
	public ApiException(ApiError error, Throwable cause) {
		super(error.getDescription(), cause);
		this.error = error;
	}

}
