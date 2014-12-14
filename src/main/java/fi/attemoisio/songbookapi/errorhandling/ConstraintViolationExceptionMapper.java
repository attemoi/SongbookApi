package fi.attemoisio.songbookapi.errorhandling;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import fi.attemoisio.songbookapi.model.error.ValidationError;
import fi.attemoisio.songbookapi.model.error.ValidationErrorResponse;

/**
 * 
 * @author Atte
 *
 */
@Provider
public class ConstraintViolationExceptionMapper implements
		ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(final ConstraintViolationException exception) {
		
		ValidationErrorResponse em = new ValidationErrorResponse();
		em.setCode(ApiError.VALIDATION.getCode());
		em.setMessage(ApiError.VALIDATION.getDescription());
		
		List<ValidationError> errors = new ArrayList<ValidationError>();
		for (@SuppressWarnings("rawtypes")
		ConstraintViolation violation : exception.getConstraintViolations()) {

			ValidationError e = new ValidationError();

			String property = "";
			Iterator<Node> iter = violation.getPropertyPath().iterator();
			while (iter.hasNext()) {
				property = iter.next().toString();
			}
			e.setProperty(property);
			e.setMessage(violation.getMessage());
			errors.add(e);
		}
		em.setErrors(errors);

		return Response.status(Response.Status.BAD_REQUEST).entity(em)
				.type(MediaType.APPLICATION_JSON).build();
	}
}
