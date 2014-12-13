package fi.attemoisio.songbookapi.exceptionhandling;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestExceptionMapper implements ExceptionMapper<Throwable>
{

    @Override
    public Response toResponse(Throwable exception)
    {
    	// TODO: logging
        exception.printStackTrace();

        return Response.status(getStatusCode(exception))
                .entity(getEntity(exception)).type(MediaType.TEXT_PLAIN)
                .build();
    }

    /*
     * Get appropriate HTTP status code for an exception.
     */
    private int getStatusCode(Throwable exception)
    {
        if (exception instanceof WebApplicationException)
        {
            return ((WebApplicationException)exception).getResponse().getStatus();
        }

        return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
    }

    /*
     * Get response body for an exception.
     */
    private String getEntity(Throwable exception)
    {
        // return stack trace for debugging (probably don't want this in prod...)
        StringWriter errorMsg = new StringWriter();
        exception.printStackTrace(new PrintWriter(errorMsg));
        return errorMsg.toString();            
    }
}