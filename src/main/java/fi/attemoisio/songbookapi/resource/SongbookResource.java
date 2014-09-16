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
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.api.ConflictException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponses;
import com.wordnik.swagger.annotations.ApiResponse;

import fi.attemoisio.songbookapi.model.Songbook;

@Path("songbooks")
@Api(value = "/songbooks", description = "Operations about songbooks")
@Produces({ MediaType.APPLICATION_JSON })
public class SongbookResource {

	@Context UriInfo uriInfo;
	
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
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Resource not found") })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSongbooks(){

		// TODO: Get books from database
		
		List<Songbook> books = new ArrayList<>();	
		
		if (books.isEmpty())
			return Response.status(Response.Status.NO_CONTENT).entity("No songbooks found").build();

		return Response.ok(books).build();
	}
	
	@POST
	@ApiOperation(value = "Add a new songbook (json)")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Songbook created succesfully"),	
			@ApiResponse(code = 400, message = "Invalid input"),
			@ApiResponse(code = 409, message = "Songbook with given id already exists")})
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
