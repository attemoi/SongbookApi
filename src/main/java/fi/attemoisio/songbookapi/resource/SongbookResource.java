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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.core.Response;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponses;
import com.wordnik.swagger.annotations.ApiResponse;

import fi.attemoisio.songbookapi.model.Songbook;

@Path("songbooks")
@Api(value = "/songbooks", description = "Operations about songbooks")
@Produces({ "application/json" })
public class SongbookResource {

	@GET
	@ApiOperation(
			value = "List all songbooks", 
			notes = "Returns a list of all available songbooks.", 
			response = Songbook.class, 
			responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Resource not found") })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSongbooks(){

		// TODO: Get books from Postgres
		
		List<Songbook> books = new ArrayList<>();	
		
		if (books.isEmpty())
			return Response.status(Response.Status.NO_CONTENT).entity("No songbooks found").build();

		return Response.ok(books).build();
	}
}
