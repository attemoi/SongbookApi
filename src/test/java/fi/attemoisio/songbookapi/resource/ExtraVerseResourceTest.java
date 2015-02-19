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
import org.junit.Ignore;
import org.junit.Test;

import fi.attemoisio.songbookapi.model.ExtraVerse;
import fi.attemoisio.songbookapi.model.ExtraVersePost;
import fi.attemoisio.songbookapi.resource.app.SongbookTestApplicationBinder;

public class ExtraVerseResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig()
				.register(new SongbookTestApplicationBinder()).packages("fi.attemoisio.songbookapi");
	}

	@Test
	public void testGetExtraVerses() {

		final Response response = target(
				"songbooks/book0/songs/song0/extra-verses").request().get();
		assertEquals(200, response.getStatus());

		Collection<ExtraVerse> verses = response
				.readEntity(new GenericType<Collection<ExtraVerse>>() {
				});
		assertEquals(5, verses.size());

	}

	@Test
	public void testGetExtraVerse() {

		final Response response = target(
				"songbooks/book0/songs/song0/extra-verses/0").request().get();
		assertEquals(200, response.getStatus());

		ExtraVerse verse = response.readEntity(ExtraVerse.class);
		assertTrue(verse != null);

		final Response response2 = target(
				"songbooks/book0/songs/song0/extra-verses/9998").request()
				.get();
		assertEquals(404, response2.getStatus());

	}

	@Test
	public void testAddExtraVerse() {

		ExtraVersePost verse = new ExtraVersePost();
		verse.setLyrics("asdf");

		Entity<ExtraVersePost> verseEntity = Entity.entity(verse,
				MediaType.APPLICATION_JSON);
		final Response response = target(
				"songbooks/book0/songs/song0/extra-verses").request().post(
				verseEntity);

		assertEquals(201, response.getStatus());

	}
	
	@Ignore // because PATCH not supported
	@Test
	public void testUpdateExtraVerse() {

		ExtraVerse verse = new ExtraVerse();
		verse.setLyrics("asdf");
		verse.setId(1);

		Entity<ExtraVerse> verseEntity = Entity.entity(verse,
				MediaType.APPLICATION_JSON);
		final Response response = target(
				"songbooks/book0/songs/song0/extra-verses").request().method("PATCH", verseEntity);

		assertEquals(200, response.getStatus());
		
		verse.setId(991929); // non-existent id
		Entity<ExtraVerse> newVerseEntity = Entity.entity(verse,
				MediaType.APPLICATION_JSON);
		final Response response2 = target(
				"songbooks/book0/songs/song0/extra-verses").request().method("PATCH", newVerseEntity);
		assertEquals(404, response2.getStatus());

	}

	@Test
	public void testDeleteExtraVerse() {

		final Response response = target(
				"songbooks/book0/songs/song0/extra-verses/0").request()
				.delete();
		assertEquals(200, response.getStatus());

		final Response response2 = target(
				"songbooks/book0/songs/song0/extra-verses/9998").request()
				.delete();
		assertEquals(404, response2.getStatus());

	}

}
