package fi.attemoisio.songbookapi.exceptionhandling;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The default constraintViolation is a bit too verbose, so this is used to
 * display only the necessary request validation information.
 * 
 * @author Atte
 *
 */
@Provider
public class ConstraintViolationExceptionMapper implements
		ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(final ConstraintViolationException exception) {
		List<CustomConstraintViolation> errors = new ArrayList<CustomConstraintViolation>();

		for (@SuppressWarnings("rawtypes")
		ConstraintViolation violation : exception.getConstraintViolations()) {

			CustomConstraintViolation e = new CustomConstraintViolation();
			e.setInvalidValue(violation.getInvalidValue().toString());
			String property = "";
			Iterator<Node> iter = violation.getPropertyPath().iterator();
			while (iter.hasNext()) {
				property = iter.next().toString();
			}
			e.setProperty(property);
			e.setMessage(violation.getMessage());
			errors.add(e);
		}

		GenericEntity<List<CustomConstraintViolation>> entity = new GenericEntity<List<CustomConstraintViolation>>(
				errors) {
		};

		return Response.status(Response.Status.BAD_REQUEST).entity(entity)
				.type(MediaType.APPLICATION_JSON).build();
	}
}
