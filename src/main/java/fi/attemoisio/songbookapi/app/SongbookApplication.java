package fi.attemoisio.songbookapi.app;

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

import java.util.logging.Logger;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class SongbookApplication extends ResourceConfig {
	public SongbookApplication() {

		register(new SongbookApplicationBinder());

		packages("fi.attemoisio.songbookapi",
				"com.wordnik.swagger.jersey.listing");

		// Enables sending validation errors in response entity to the client.
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

		// Enable LoggingFilter & output entity.
		registerInstances(new LoggingFilter(
				Logger.getLogger(SongbookApplication.class.getName()), true));
	}
}
