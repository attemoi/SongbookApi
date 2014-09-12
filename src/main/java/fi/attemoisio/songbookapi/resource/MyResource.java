package fi.attemoisio.songbookapi.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponses;
import com.wordnik.swagger.annotations.ApiResponse;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
@Api(value = "/myresource", description = "Operations about myresource")
@Produces({"text/plain"})
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @ApiOperation(value = "Get Hello world message", notes = "More notes about this method")
    @ApiResponses(value = {
      @ApiResponse(code = 404, message = "Resource not found") 
    })
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Hello, Heroku!";
    }
}
