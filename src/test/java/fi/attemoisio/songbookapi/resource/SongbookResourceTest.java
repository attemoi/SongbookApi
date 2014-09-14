package fi.attemoisio.songbookapi.resource;

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

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import fi.attemoisio.songbookapi.resource.SongbookResource;

public class SongbookResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(SongbookResource.class);
    }

    @Test
    public void testGetSongbooks() {
        final String responseMsg = target().path("songbooks").request().get(String.class);
        assertEquals("", responseMsg);
        
        final Response response = target().path("songbooks").request().head();
        assertEquals(204, response.getStatus());
    }

}
