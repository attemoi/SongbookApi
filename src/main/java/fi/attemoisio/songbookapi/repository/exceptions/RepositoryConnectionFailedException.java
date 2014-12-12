package fi.attemoisio.songbookapi.repository.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RepositoryConnectionFailedException extends
		WebApplicationException {

	private static final long serialVersionUID = -9007118831981894024L;

	public RepositoryConnectionFailedException(String message) {
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(message).type(MediaType.TEXT_PLAIN).build());
	}

}
