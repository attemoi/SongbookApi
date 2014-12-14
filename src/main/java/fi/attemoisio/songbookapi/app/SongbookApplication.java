package fi.attemoisio.songbookapi.app;

/*
 * ###################################################################=
 * Laulukirja API
 * %%
 * Copyright (C) 2014 Atte Moisio
 * %%
 * DO WHAT YOU WANT TO PUBLIC LICENSE
 * 
 *  Copyright (C) 2014 Atte Moisio
 * 
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 * 
 *  DO WHAT YOU WANT TO PUBLIC LICENSE
 *  TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 * 
 *  0. You just DO WHAT YOU WANT TO.
 * ###################################################################-
 */

import java.util.logging.Logger;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import fi.attemoisio.songbookapi.errorhandling.ApiExceptionMapper;
import fi.attemoisio.songbookapi.errorhandling.ConstraintViolationExceptionMapper;

public class SongbookApplication extends ResourceConfig {
	public SongbookApplication() {

		register(new SongbookApplicationBinder());
		register(new ApiExceptionMapper());
		register(new ConstraintViolationExceptionMapper());

		packages("fi.attemoisio.songbookapi",
				"com.wordnik.swagger.jersey.listing");

		// Enables sending validation errors in response entity to the client.
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

		// Enable LoggingFilter & output entity.
		registerInstances(new LoggingFilter(
				Logger.getLogger(SongbookApplication.class.getName()), true));
	}
}
