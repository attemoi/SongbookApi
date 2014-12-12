package fi.attemoisio.songbookapi.repository.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RepositoryRequestFailedException extends WebApplicationException{

	private static final long serialVersionUID = -1607307743605403733L;

	public RepositoryRequestFailedException() {}

    public RepositoryRequestFailedException(String message)
    {
    	super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(message).type(MediaType.TEXT_PLAIN).build());
    }
	
}
