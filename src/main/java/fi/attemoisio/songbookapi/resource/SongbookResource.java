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

import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponses;
import com.wordnik.swagger.annotations.ApiResponse;

import fi.attemoisio.songbookapi.model.Songbook;

@Path("songbooks")
@Api(value = "/songbooks", description = "Operations about songbooks")
@Produces({ "application/json" })
public class SongbookResource {

	@Context UriInfo uriInfo;

	public URI getCreatedUri(String resourceId)
	{
		return uriInfo.getRequestUri().resolve(resourceId);
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
	@ApiOperation(value = "Add a new songbook (form)")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Songbook created"),
			@ApiResponse(code = 400, message = "Invalid input")})
	@Consumes("application/x-www-form-urlencoded")
	public Response addSongbook(
			@ApiParam(value = "Songbook title", required = true) @FormParam("title") String title,
			@ApiParam(value = "Songbook description", required = true) @FormParam("description") String description,
			@ApiParam(value = "Songbook release year", required = true) @FormParam("releaseYear") int releaseYear) {

		if (title == null || title.equals("a"))
			throw new BadRequestException("Missing or invalid title");
		
		Songbook book = new Songbook();
		book.setTitle(title);
		book.setDescription(description);
		book.setReleaseYear(releaseYear);
				
		// TODO: add songbook to database and return the created object along
		// with the proper http response
		book.setId(123);
		
		String id = Integer.toString(book.getId());	
		return Response.created(getCreatedUri(id)).entity(book).build();
	}
	
	@POST
	@ApiOperation(value = "Add a new songbook (json)")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Songbook created"),
			@ApiResponse(code = 405, message = "Invalid input")})
	@Consumes("application/json")
	public Response addSongbook(
			@ApiParam(value = "Songbook to be added", required = true) Songbook book) {
				
		// TODO: add songbook to database and return the created object along
		// with the proper http response

		String id = Integer.toString(book.getId());	
		return Response.created(getCreatedUri(id)).entity(book).build();
	}
}
