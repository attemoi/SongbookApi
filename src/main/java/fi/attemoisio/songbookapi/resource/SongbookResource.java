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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.api.ConflictException;
import com.sun.jersey.api.NotFoundException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.repository.SongbookRepository;
import fi.attemoisio.songbookapi.validation.constraints.Slug;

@Path("songbooks")
@Api(value = "songbooks", description = "Operations about songbooks")
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
public class SongbookResource {

	@Context
	UriInfo uriInfo;

	SongbookRepository repository;

	@Inject
	public SongbookResource(SongbookRepository repository) {
		this.repository = repository;
	}

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
	public Response getSongbooks() throws NoContentException  {

		Collection<Songbook> books;

		books = repository.getSongbooks();

		if (books.isEmpty())
			throw new NoContentException("No songbooks found");

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
	public Response addSongbook(
			@ApiParam(value = "Songbook to be added", required = true) @Valid Songbook book) {

		if (repository.addSongbook(book)) {
			return Response.created(getCreatedUri(book.getId())).entity(book)
					.build();
		} else {
			throw new ConflictException("Songbook with given id already exists");
		}

	}

	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "Delete a songbook (json)")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Songbook deleted succesfully"),
			@ApiResponse(code = 400, message = "Invalid input"),
			@ApiResponse(code = 404, message = "Songbook not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable") })
	public Response deleteSongbook(
			@ApiParam(value = "Id of songbook to delete", required = true) @Slug @PathParam("id") String id) {

		if (repository.deleteSongbook(id)) {
			return Response.ok("Songbook deleted succesfully")
					.type(MediaType.TEXT_PLAIN).build();
		} else {
			throw new NotFoundException(
					"Could not delete songbook (id not found).");
		}

	}
}
