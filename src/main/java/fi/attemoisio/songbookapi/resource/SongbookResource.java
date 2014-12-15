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
import java.util.Collection;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import fi.attemoisio.songbookapi.errorhandling.ApiError;
import fi.attemoisio.songbookapi.exceptions.ApiException;
import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.model.SongbookPost;
import fi.attemoisio.songbookapi.repository.ExtraVerseRepository;
import fi.attemoisio.songbookapi.repository.SongRepository;
import fi.attemoisio.songbookapi.repository.SongbookRepository;
import fi.attemoisio.songbookapi.repository.SongbookRepository.PutResult;
import fi.attemoisio.songbookapi.validation.Slug;

@Path("songbooks")
@Api(value = "songbooks", description = "Operations about songbooks")
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
public class SongbookResource {

	@Context
	UriInfo uriInfo;

	@Inject
	SongbookRepository songbookRepository;
	@Inject
	SongRepository songRepository;
	@Inject
	ExtraVerseRepository verseRepository;

	private URI getCreatedUri(String resourceId) {
		return uriInfo.getRequestUri().resolve("songbooks").resolve(resourceId);
	}

	@GET
	@ApiOperation(value = "List all songbooks", notes = "Returns a list of all available songbooks.", response = Songbook.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Request completed succesfully"),
			@ApiResponse(code = 204, message = "No songbooks found"),
			@ApiResponse(code = 408, message = "Request timeout"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable") })
	public Response getSongbooks() {

		Collection<Songbook> books;

		books = songbookRepository.getSongbooks();

		if (books.isEmpty())
			throw new ApiException(ApiError.GET_SONGBOOKS_NO_CONTENT);

		GenericEntity<Collection<Songbook>> entity = new GenericEntity<Collection<Songbook>>(
				books) {
		};
		return Response.ok(entity).build();
	}

	@POST
	@ApiOperation(value = "Add a new songbook (json)")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Songbook added succesfully"),
			@ApiResponse(code = 400, message = "Invalid input"),
			@ApiResponse(code = 409, message = "Songbook with given id already exists"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable") })
	@Consumes("application/json")
	public Response postSongbook(
			@ApiParam(value = "Songbook to be added", required = true) @Valid Songbook book) {

		Songbook createdBook = songbookRepository.postSongbook(book);

		return Response.created(getCreatedUri(createdBook.getId()))
				.entity(createdBook).build();

	}
	
	@PUT
	@ApiOperation(value = "Update existing or create a new songbook")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Songbook data updated succesfully"),
			@ApiResponse(code = 201, message = "Songbook added succesfully"),
			@ApiResponse(code = 400, message = "Invalid input"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable") })
	@Consumes("application/json")
	public Response putSongbook(
			@ApiParam(value = "Songbook to be added", required = true) @Valid Songbook book) {

		PutResult r = songbookRepository.putSongbook(book);
		
		if (r.getUpdatedRows() > 0) {

			return Response.ok("Songbook data updated succesfully.")
					.type(MediaType.TEXT_PLAIN).build();

		} else {

			Songbook newSongbook = new Songbook();
			newSongbook.setId(r.getInsertedId());
			newSongbook.setTitle(book.getTitle());
			newSongbook.setOtherNotes(book.getOtherNotes());
			newSongbook.setDescription(book.getDescription());
			newSongbook.setReleaseYear(book.getReleaseYear());

			return Response.created(getCreatedUri(r.getInsertedId())).entity(newSongbook)
					.build();
		}

	}

	@DELETE
	@Path("/{book_id}")
	@ApiOperation(value = "Delete a songbook")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Songbook deleted succesfully"),
			@ApiResponse(code = 400, message = "Invalid input"),
			@ApiResponse(code = 404, message = "Songbook not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable") })
	public Response deleteSongbook(
			@ApiParam(value = "Id of songbook to delete", required = true) @PathParam("book_id") String bookId) {

		if (songbookRepository.deleteSongbook(bookId)) {
			return Response.ok("Songbook deleted succesfully")
					.type(MediaType.TEXT_PLAIN).build();
		} else {
			throw new ApiException(ApiError.DELETE_SONGBOOK_NOT_FOUND);
		}

	}

	@GET
	@ApiOperation(value = "Get songbook data")
	@Path("/{book_id}")
	public Response getSongbook(@Slug @PathParam("book_id") String bookId) {

		Songbook book = songbookRepository.getSongbook(bookId);

		if (book == null)
			throw new ApiException(ApiError.GET_SONGBOOK_NOT_FOUND);

		return Response.ok(book).build();

	}

	@Path("/{book_id}/songs")
	public SongResource locateToSongResource(
			@Slug @PathParam("book_id") String bookId) {

		Songbook book = songbookRepository.getSongbook(bookId);

		if (book == null)
			throw new ApiException(ApiError.GET_SONGBOOK_NOT_FOUND);

		return new SongResource(uriInfo, songRepository, verseRepository,
				book.getId());

	}
}
