package fi.attemoisio.songbookapi.app;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class SongbookApplication extends ResourceConfig{
	public SongbookApplication() {
		
		// VALIDATION

		// Enables sending validation errors in response entity to the client.
	    property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
	    
	    // Disables @ValidateOnExecution check.
	    //property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
	    
		packages("fi.attemoisio.songbookapi.resource;fi.attemoisio.songbookapi.model");
	}
}
