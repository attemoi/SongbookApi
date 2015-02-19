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

import fi.attemoisio.songbookapi.model.Song;
import fi.attemoisio.songbookapi.model.SongPost;
import fi.attemoisio.songbookapi.resource.app.SongbookTestApplicationBinder;

public class SongResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig().register(
				new SongbookTestApplicationBinder()).packages(
				"fi.attemoisio.songbookapi");
	}

	@Test
	public void testGetSongs() {

		final Response response = target("songbooks/book0/songs").request()
				.get();
		assertEquals(200, response.getStatus());

		Collection<Song> songs = response
				.readEntity(new GenericType<Collection<Song>>() {
				});
		assertEquals(5, songs.size());

	}

	@Test
	public void testGetSong() {

		final Response response = target("songbooks/book0/songs/song0")
				.request().get();
		assertEquals(200, response.getStatus());

		Song song = response.readEntity(Song.class);
		assertTrue(song != null);

		final Response response2 = target(
				"songbooks/book0/songs/non-existent-songs").request().get();
		assertEquals(404, response2.getStatus());

	}

	@Test
	public void testPostSong() {

		SongPost song = new SongPost();
		song.setName("song name lorem ipsum");
		song.setExtra("asdf");
		song.setLyrics("asdf");
		song.setPageNumber(234);
		song.setSongNumber(123);
		song.setOtherNotes("asdf");

		Entity<SongPost> songEntity = Entity.entity(song,
				MediaType.APPLICATION_JSON);
		
		final Response response = target("songbooks/book0/songs").request()
				.post(songEntity);

		assertEquals(201, response.getStatus());
		
	}

	@Test
	public void testDeleteSongbook() {

		final Response response = target("songbooks/book0/songs/song0")
				.request().delete();
		assertEquals(200, response.getStatus());

		final Response response2 = target(
				"songbooks/book0/songs/non-existent-id").request().delete();
		assertEquals(404, response2.getStatus());

	}
	
	@Test
	public void testPutSongbook() {

		Song song = new Song();
		song.setId("song0");
		song.setName("new name");
		song.setExtra("asdf");
		song.setLyrics("asdf");
		song.setPageNumber(234);
		song.setSongNumber(123);
		song.setOtherNotes("asdf");
		
		Entity<Song> existingSongEntity = Entity.entity(song, MediaType.APPLICATION_JSON);
		
		final Response response = target("songbooks/book0/songs/")
				.request().put(existingSongEntity);
		assertEquals(200, response.getStatus());
		
		song.setId("non-existent-id");
		Entity<Song> newSongEntity = Entity.entity(song, MediaType.APPLICATION_JSON);;
		final Response response2 = target("songbooks/book0/songs/")
				.request().put(newSongEntity);
		assertEquals(201, response2.getStatus());

	}

}
