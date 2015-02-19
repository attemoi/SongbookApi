/* 
 * The MIT License
 *
 * Copyright 2015 Atte Moisio.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
