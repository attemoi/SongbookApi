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
import fi.attemoisio.songbookapi.model.error.ErrorResponse;
import fi.attemoisio.songbookapi.model.error.ValidationErrorResponse;
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
	@ApiOperation(
			value = "List all songbooks", 
			notes = "Returns a list of all available songbooks.", 
			response = Songbook.class, 
			responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Request completed succesfully", response = Songbook.class),
			@ApiResponse(code = 204, message = "No songbooks found"),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class),
			@ApiResponse(code = 503, message = "Service unavailable", response = ErrorResponse.class) })
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
	@ApiOperation(
			value = "Add a new songbook")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Songbook added succesfully", response = Songbook.class),
			@ApiResponse(code = 400, message = "Invalid input", response = ValidationErrorResponse.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class),
			@ApiResponse(code = 503, message = "Service unavailable", response = ErrorResponse.class) })
	@Consumes("application/json")
	public Response postSongbook(
			@ApiParam(value = "Songbook to be added", required = true)  @Valid SongbookPost book) {

		Songbook createdBook = songbookRepository.postSongbook(book);

		return Response.created(getCreatedUri(createdBook.getId()))
				.entity(createdBook).build();

	}
	
	@PUT
	@ApiOperation(
			value = "Update existing or create a new songbook",
			response = Songbook.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Songbook data updated succesfully"),
			@ApiResponse(code = 201, message = "Songbook added succesfully", response = Songbook.class),
			@ApiResponse(code = 400, message = "Invalid input", response = ValidationErrorResponse.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class),
			@ApiResponse(code = 503, message = "Service unavailable", response = ErrorResponse.class )}) 
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
	@ApiOperation(value = "Delete a songbook and all related songs and verses.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Songbook deleted succesfully"),
			@ApiResponse(code = 400, message = "Invalid input", response = ValidationErrorResponse.class),
			@ApiResponse(code = 404, message = "Songbook not found", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class),
			@ApiResponse(code = 503, message = "Service unavailable", response = ErrorResponse.class) })
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
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Songbook data fetched succesfully", response = Songbook.class),
			@ApiResponse(code = 404, message = "Songbook not found", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class),
			@ApiResponse(code = 503, message = "Service unavailable", response = ErrorResponse.class) })
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
