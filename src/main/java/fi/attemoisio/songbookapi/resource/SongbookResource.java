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

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.api.ConflictException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.repository.SongbookRepository;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryTimeoutException;

@Path("songbooks")
@Api(value = "songbooks", description = "Operations about songbooks")
@Produces({ MediaType.APPLICATION_JSON })
@ManagedBean
public class SongbookResource {

	@Context UriInfo uriInfo;
	@Inject SongbookRepository repository;
	
	private URI getCreatedUri(String resourceId)
	{
		return uriInfo.getRequestUri().resolve("songbooks").resolve(resourceId);
	}
	
	@GET
	@ApiOperation(
			value = "List all songbooks", 
			notes = "Returns a list of all available songbooks.", 
			response = Songbook.class, 
			responseContainer = "List")
	@ApiResponses(value = { 
			@ApiResponse(code = 204, message = "Resource not found"),
			@ApiResponse(code = 408, message = "Request timeout"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable")})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSongbooks(){

		Collection<Songbook> books;
		try {
			books = repository.getSongbooks();
		} catch (RepositoryConnectionFailedException e) {
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Service unavailable").build();
		} catch (RepositoryRequestFailedException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
		} catch (RepositoryTimeoutException e) {
			return Response.status(Response.Status.REQUEST_TIMEOUT).entity("Request timed out").build();
		}

		if (books == null || books.isEmpty())
			return Response.status(Response.Status.NO_CONTENT).entity("No songbooks found").build();

	    GenericEntity<Collection<Songbook>> entity = new GenericEntity<Collection<Songbook>>(books) {};
		return Response.ok(entity).build();
	}
	
	@POST
	@ApiOperation(value = "Add a new songbook (json)")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Songbook created succesfully"),	
			@ApiResponse(code = 400, message = "Invalid input"),
			@ApiResponse(code = 409, message = "Songbook with given id already exists"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable")})
			
	@Consumes("application/json")
	public Response addSongbook(
			@ApiParam(value = "Songbook to be added", required = true) @Valid Songbook book) {
				
		// TODO: add songbook to database and return the created object along
		// with the proper http response

		if (false) //TODO: if a songbook with the id already exists)
			throw new ConflictException("Songbook with given id already exists");
		
		return Response.created(getCreatedUri(book.getId())).entity(book).build();
	}
}
