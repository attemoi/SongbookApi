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

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import fi.attemoisio.songbookapi.repository.SongbookRepository;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryTimeoutException;
import fi.attemoisio.songbookapi.resource.SongbookResource;

public class SongbookResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(SongbookResource.class);
    }

    @Test
    public void testGetSongbooks() throws URISyntaxException, RepositoryConnectionFailedException, RepositoryRequestFailedException, RepositoryTimeoutException {
    	
    	SongbookResource resource = new SongbookResource();
    	
    	// Test normal ok response
    	resource.repository = new MockSongbookRepository();
        assertEquals(200, resource.getSongbooks().getStatus());
        
        // Test request timeout
        SongbookRepository r2 = mock(SongbookRepository.class);
        when(r2.getSongbooks()).thenThrow(new RepositoryTimeoutException("Request timeout"));
        resource.repository = r2;
        assertEquals(408, resource.getSongbooks().getStatus());
        
        // Test server error
        SongbookRepository r1 = mock(SongbookRepository.class);
        when(r1.getSongbooks()).thenThrow(new RepositoryRequestFailedException("Server error"));
        resource.repository = r1;
        assertEquals(500, resource.getSongbooks().getStatus());

        // Test service unavailable
        SongbookRepository r3 = mock(SongbookRepository.class);
        when(r3.getSongbooks()).thenThrow(new RepositoryConnectionFailedException("Service unavailable"));
        resource.repository = r3;
        assertEquals(503, resource.getSongbooks().getStatus());
        
    }

}
