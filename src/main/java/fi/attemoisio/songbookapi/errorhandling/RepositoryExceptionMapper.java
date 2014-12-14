package fi.attemoisio.songbookapi.errorhandling;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import fi.attemoisio.songbookapi.exceptions.RepositoryException;
import fi.attemoisio.songbookapi.model.error.ErrorResponse;

public class RepositoryExceptionMapper implements ExceptionMapper<RepositoryException>{
	
	@Override
	public Response toResponse(final RepositoryException exception) {
		
		ErrorResponse e = new ErrorResponse();
		e.setCode(exception.getErrorCode().getCode());
		e.setMessage(exception.getErrorCode().getDescription());
		e.setHttpStatus(exception.getErrorCode().getHttpStatus().getStatusCode());
		
		if (exception.getCause() != null)
			e.setDescription(exception.getCause().getMessage());
	
		return Response.status(exception.getErrorCode().getHttpStatus()).entity(e)
				.type(MediaType.APPLICATION_JSON).build();
	}
}
