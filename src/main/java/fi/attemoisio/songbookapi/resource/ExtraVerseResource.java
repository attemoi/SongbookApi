package fi.attemoisio.songbookapi.resource;

/*
 * ###################################################################=
 * Songbook API
 * %%
 * Copyright (C) 2014 Atte Moisio
 * %%
 * The MIT License (MIT)
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 * ###################################################################-
 */

import java.net.URI;
import java.util.Collection;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.process.internal.RequestScoped;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import com.wordnik.swagger.jaxrs.PATCH;

import fi.attemoisio.songbookapi.errorhandling.ApiError;
import fi.attemoisio.songbookapi.exceptions.ApiException;
import fi.attemoisio.songbookapi.model.ExtraVerse;
import fi.attemoisio.songbookapi.model.ExtraVersePost;
import fi.attemoisio.songbookapi.repository.ExtraVerseRepository;

@RequestScoped
@Api(value = "extraverses", description = "Operations about extra verses")
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
public class ExtraVerseResource {

	UriInfo uriInfo;
	ExtraVerseRepository verseRepository;
	String bookId;
	String songId;

	public ExtraVerseResource(UriInfo uriInfo,
			ExtraVerseRepository verseRepository, String bookId, String songId) {
		this.uriInfo = uriInfo;
		this.verseRepository = verseRepository;
		this.bookId = bookId;
		this.songId = songId;
	}

	private URI getCreatedUri(String resourceId) {
		return uriInfo.getRequestUri().resolve("songbooks").resolve(resourceId);
	}

	@GET
	@ApiOperation(value = "List all extra verses for a song", notes = "Returns a list of all extra verses for a song.", response = ExtraVerse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Request completed succesfully"),
			@ApiResponse(code = 204, message = "No verses found"),
			@ApiResponse(code = 408, message = "Request timeout"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable") })
	public Response getExtraVerses() throws NoContentException {

		Collection<ExtraVerse> extraVerses;

		extraVerses = verseRepository.getExtraVerses(bookId, songId);

		if (extraVerses.isEmpty())
			throw new ApiException(ApiError.GET_VERSES_NO_CONTENT);

		GenericEntity<Collection<ExtraVerse>> entity = new GenericEntity<Collection<ExtraVerse>>(
				extraVerses) {
		};
		return Response.ok(entity).build();
	}

	@PATCH
	@ApiOperation(value = "Update verse data")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Verse data updated successfully"),
			@ApiResponse(code = 201, message = "New verse created succesfully"),
			@ApiResponse(code = 400, message = "Invalid input"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable") })
	@Consumes("application/json")
	public Response updateExtraVerse(
			@ApiParam(value = "Verse to be added", required = true) @Valid ExtraVerse verse) {

		verseRepository.patchExtraVerse(bookId, songId, verse);

		return Response.ok("Verse data updated succesfully.").type(MediaType.TEXT_PLAIN).build();

	}
	
	@POST
	@ApiOperation(value = "Add a new verse.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Verse created succesfully"),
			@ApiResponse(code = 400, message = "Invalid input"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable") })
	@Consumes("application/json")
	public Response addExtraVerse(
			@ApiParam(value = "Verse to be added", required = true) @Valid ExtraVersePost verse) {

		ExtraVerse newVerse = verseRepository.postExtraVerse(bookId, songId, verse);

		return Response.created(getCreatedUri(bookId)).entity(newVerse).build();

	}

	@DELETE
	@Path("/{verse_id}")
	@ApiOperation(value = "Delete an extra verse from a song")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Verse deleted succesfully"),
			@ApiResponse(code = 400, message = "Invalid input"),
			@ApiResponse(code = 404, message = "Verse not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable") })
	public Response deleteExtraVerse(
			@ApiParam(value = "Id of verse to delete", required = true) @PathParam("verse_id") String verseId) {

		// Return not found instead of bar request
		Integer parsedId;
		try {
			parsedId = Integer.parseUnsignedInt(verseId);
		} catch (NumberFormatException e) {
			throw new ApiException(ApiError.DELETE_VERSE_NOT_FOUND);
		}
		
		if (verseRepository.deleteExtraVerse(bookId, songId, parsedId)) {
			return Response.ok("Verse deleted succesfully")
					.type(MediaType.TEXT_PLAIN).build();
		} else {
			throw new ApiException(ApiError.DELETE_VERSE_NOT_FOUND);
		}

	}

	@GET
	@ApiOperation(value = "Get data for extra verse")
	@Path("/{verse_id}")
	public Response getExtraVerse(
			@ApiParam(value = "Verse id", required = true) @PathParam("verse_id") String verseId) {

		// Return not found instead of bar request
		Integer parsedId;
		try {
			parsedId = Integer.parseUnsignedInt(verseId);
		} catch (NumberFormatException e) {
			throw new ApiException(ApiError.GET_VERSE_NOT_FOUND);
		}
				
		ExtraVerse verse = verseRepository.getExtraVerse(bookId, songId,
				parsedId);

		if (verse == null)
			throw new ApiException(ApiError.GET_VERSE_NOT_FOUND);

		return Response.ok(verse).build();

	}

}
