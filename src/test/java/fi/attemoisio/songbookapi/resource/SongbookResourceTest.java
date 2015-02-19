/* 
 * The MIT License
 *
 * Copyright 2015 Atte Moisio.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fi.attemoisio.songbookapi.resource;

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

import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.model.SongbookPost;
import fi.attemoisio.songbookapi.resource.app.SongbookTestApplicationBinder;


public class SongbookResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
		return new ResourceConfig().register(
				new SongbookTestApplicationBinder()).packages(
				"fi.attemoisio.songbookapi");
    }

    @Test
    public void testGetSongbooks() {
    	
    	final Response response = target("songbooks").request().get(); 	
        assertEquals(response.getStatus(), 200);
        Collection<Songbook> books = response.readEntity(new GenericType<Collection<Songbook>>(){});
        assertTrue(!books.isEmpty());
        
    }
    
    @Test
    public void testGetSongbook() {
    	
    	final Response response = target("songbooks/book0").request().get(); 	
        assertEquals(200, response.getStatus());
        
        Songbook book = response.readEntity(Songbook.class);
        assertTrue(book != null);
        
        final Response response2 = target("songbooks/non-existent-book").request().get(); 	
        assertEquals(404, response2.getStatus());
        
    }
    
    @Test
    public void testDeleteSongbook() {
    	
    	final Response response = target("songbooks/book0").request().delete(); 
        assertEquals(200, response.getStatus());
        
        final Response response2 = target("songbooks/non-existent-id").request().delete(); 	
        assertEquals(404, response2.getStatus());

    }
    
    @Test
	public void testPutSongbook() {

		Songbook book = new Songbook();
		book.setId("book0");
		book.setTitle("new name");
		book.setOtherNotes("asdf");
		book.setDescription("asdf");
		book.setReleaseYear(1999);
		
		Entity<Songbook> existingSongbookEntity = Entity.entity(book, MediaType.APPLICATION_JSON);
		
		final Response response = target("songbooks/")
				.request().put(existingSongbookEntity);
		assertEquals(200, response.getStatus());
		
		book.setId("non-existent-id");
		Entity<Songbook> newSongbookEntity = Entity.entity(book, MediaType.APPLICATION_JSON);;
		final Response response2 = target("songbooks/")
				.request().put(newSongbookEntity);
		assertEquals(201, response2.getStatus());

	}
    
	@Test
	public void testPostSongbook() {

		SongbookPost book = new SongbookPost();
		book.setOtherNotes("asdf");
		book.setTitle("asdf");
		book.setOtherNotes("asdf");
		book.setReleaseYear(1999);
		book.setDescription("asdf");

		Entity<SongbookPost> bookEntity = Entity.entity(book,
				MediaType.APPLICATION_JSON);
		
		final Response response = target("songbooks/").request()
				.post(bookEntity);

		assertEquals(201, response.getStatus());
		
	}


}
