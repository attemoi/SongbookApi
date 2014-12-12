package fi.attemoisio.songbookapi.repository.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RepositoryConnectionTimedOutException extends WebApplicationException {

	private static final long serialVersionUID = 8184313370873051466L;

	public RepositoryConnectionTimedOutException() {}

    public RepositoryConnectionTimedOutException(String message)
    {
    	super(Response.status(Response.Status.SERVICE_UNAVAILABLE)
				.entity(message).type(MediaType.TEXT_PLAIN).build());
    }

}
