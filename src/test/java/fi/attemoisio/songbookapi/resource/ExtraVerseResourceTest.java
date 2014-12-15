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
