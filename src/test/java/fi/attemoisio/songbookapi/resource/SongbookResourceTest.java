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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
import org.junit.Test;

import fi.attemoisio.songbookapi.model.Songbook;

public class SongbookResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(SongbookResource.class).register(new SongbookTestApplicationBinder());
    }

    @Test
    public void testGetSongbooks() {
    	
    	final Response response = target("songbooks").request().get(); 	
        assertEquals(response.getStatus(), 200);
        Collection<Songbook> books = response.readEntity(new GenericType<Collection<Songbook>>(){});
        assertTrue(!books.isEmpty());
        
    }
    
    @Test
    public void testAddSongbook() {
    	
    	Songbook book = new Songbook();
    	book.setId("book123");
    	book.setTitle("book title lorem ipsum");
    	book.setOtherNotes("other notes book lorem ipsum");
    	book.setReleaseYear(1999);
    	book.setDescription("description lorem ipsum");
    	
    	Entity<Songbook> bookEntity = Entity.entity(book, MediaType.APPLICATION_JSON);
    	final Response response = target("songbooks/").request().post(bookEntity); 	
    	
    	assertEquals(201, response.getStatus());
    	
    	book.setId("book0");
    	bookEntity = Entity.entity(book, MediaType.APPLICATION_JSON);
    	final Response response2 = target("songbooks/").request().post(bookEntity); 	
    	assertEquals(409, response2.getStatus());
    	
    }
    
    @Test
    public void testDeleteSongbook() {
    	
    	final Response response = target("songbooks/book0").request().delete(); 
        assertEquals(200, response.getStatus());
        
        final Response response2 = target("songbooks/non-existent-id").request().delete(); 	
        assertEquals(404, response2.getStatus());

    }

}
