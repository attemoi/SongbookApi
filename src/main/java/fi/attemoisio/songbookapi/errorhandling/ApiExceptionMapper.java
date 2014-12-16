package fi.attemoisio.songbookapi.errorhandling;

/*
 * ###################################################################=
 * Songbook API
 * %%
 * Copyright (C) 2014 Atte Moisio
 * %%
 * The MIT License (MIT)
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 * ###################################################################-
 */

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
			r.setHttpStatus(500);
			if (exception.getCause() != null)
				r.setDescription(exception.getCause().getMessage());
			if (exception.getMessage() != null)
				r.setMessage(exception.getMessage());
			else
				r.setMessage("Unknown server error.");

		}
		
		return Response.status(r.getHttpStatus()).entity(r)
				.type(MediaType.APPLICATION_JSON).build();

	}

}
