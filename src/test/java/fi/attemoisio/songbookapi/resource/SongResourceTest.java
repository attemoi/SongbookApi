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
import org.junit.Test;

import fi.attemoisio.songbookapi.model.Song;
import fi.attemoisio.songbookapi.resource.app.SongbookTestApplicationBinder;

public class SongResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(SongbookResource.class, SongResource.class).register(new SongbookTestApplicationBinder());
    }

    @Test
    public void testGetSongs() {
    	
    	final Response response = target("songbooks/book0/songs").request().get(); 	
        assertEquals(200, response.getStatus());
        
        Collection<Song> songs = response.readEntity(new GenericType<Collection<Song>>(){});
        assertEquals(5, songs.size());
        
    }
    
    @Test
    public void testGetSong() {
    	
    	final Response response = target("songbooks/book0/songs/song0").request().get(); 	
        assertEquals(200, response.getStatus());
        
        Song song = response.readEntity(Song.class);
        assertTrue(song != null);
        
        final Response response2 = target("songbooks/book0/songs/non-existent-songs").request().get(); 	
        assertEquals(404, response2.getStatus());
        
    }
    
    @Test
    public void testAddSongbook() {
    	
    	Song song = new Song();
    	song.setId("song123");
    	song.setName("song name lorem ipsum");
    	song.setExtra("asdf");
    	song.setLyrics("asdf");
    	song.setPageNum(234);
    	song.setSongNumber(123);
    	song.setOtherNotes("asdf");
    	
    	Entity<Song> songEntity = Entity.entity(song, MediaType.APPLICATION_JSON);
    	final Response response = target("songbooks/book0/songs").request().post(songEntity); 	
    	
    	assertEquals(201, response.getStatus());
    	
    	song.setId("song0");
    	songEntity = Entity.entity(song, MediaType.APPLICATION_JSON);
    	final Response response2 = target("songbooks/book0/songs").request().post(songEntity); 	
    	assertEquals(409, response2.getStatus());
    	
    }
    
    @Test
    public void testDeleteSongbook() {
    	
    	final Response response = target("songbooks/book0/songs/song0").request().delete(); 
        assertEquals(200, response.getStatus());
        
        final Response response2 = target("songbooks/book0/songs/non-existent-id").request().delete(); 	
        assertEquals(404, response2.getStatus());

    }

}