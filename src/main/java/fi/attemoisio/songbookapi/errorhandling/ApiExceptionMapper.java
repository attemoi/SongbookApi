package fi.attemoisio.songbookapi.errorhandling;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ParamException;

import fi.attemoisio.songbookapi.exceptions.ApiException;
import fi.attemoisio.songbookapi.model.error.ErrorResponse;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<Throwable> {

	// TODO: log exceptions

	@Override
	public Response toResponse(Throwable exception) {

		ErrorResponse r = new ErrorResponse();

		if (exception instanceof ApiException) {

			ApiException apiException = (ApiException) exception;
			r.setMessage(exception.getMessage());
			r.setCode(apiException.getApiError().getCode());
			r.setHttpStatus(apiException.getApiError().getHttpStatus()
					.getStatusCode());

			if (exception.getCause() != null)
				r.setDescription(exception.getCause().getMessage());

		} else if (exception instanceof ParamException) {
			
			// this shouldn't happen, but prepare a basic message just in case
			r.setCode(30);
			r.setHttpStatus(Response.Status.BAD_REQUEST.getStatusCode());
			r.setMessage("Path parameter validation failed");
			
	    } else if (exception instanceof WebApplicationException) {
			
			WebApplicationException wae = (WebApplicationException) exception;
			r.setCode(20);
			r.setHttpStatus(wae.getResponse().getStatus());
			r.setMessage(wae.getMessage());

		} else {

			r.setCode(10);
			if (exception.getMessage() != null)
				r.setMessage(exception.getMessage());
			else
				r.setMessage("Unknown server error.");

		}
		
		return Response.status(r.getHttpStatus()).entity(r)
				.type(MediaType.APPLICATION_JSON).build();

	}

}