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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.process.internal.RequestScoped;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import fi.attemoisio.songbookapi.errorhandling.ApiError;
import fi.attemoisio.songbookapi.exceptions.ApiException;
import fi.attemoisio.songbookapi.model.Song;
import fi.attemoisio.songbookapi.model.SongPost;
import fi.attemoisio.songbookapi.model.error.ErrorResponse;
import fi.attemoisio.songbookapi.model.error.ValidationErrorResponse;
import fi.attemoisio.songbookapi.repository.ExtraVerseRepository;
import fi.attemoisio.songbookapi.repository.SongRepository;
import fi.attemoisio.songbookapi.repository.SongRepository.PutResult;

//@Path("/songs")
@RequestScoped
@Api(value = "songs", description = "Operations about songs")
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
public class SongResource {

	UriInfo uriInfo;
	SongRepository songRepository;
	ExtraVerseRepository verseRepository;
	String bookId;

	public SongResource(UriInfo uriInfo, SongRepository songRepository,
			ExtraVerseRepository verseRepository, String bookId) {
		this.uriInfo = uriInfo;
		this.songRepository = songRepository;
		this.verseRepository = verseRepository;
		this.bookId = bookId;
	}

	private URI getCreatedUri(String resourceId) {
		return uriInfo.getRequestUri().resolve("songbooks").resolve(resourceId);
	}

	@GET
	@ApiOperation(value = "List all songs", notes = "Returns a list of all songs in a songbook.", response = Song.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Request completed succesfully", response = Song.class),
			@ApiResponse(code = 204, message = "No songs found", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class),
			@ApiResponse(code = 503, message = "Service unavailable", response = ErrorResponse.class)})
	public Response getSongs() {

		Collection<Song> songs;

		songs = songRepository.getSongs(bookId);

		if (songs.isEmpty())
			throw new ApiException(ApiError.GET_SONGS_NO_CONTENT);

		GenericEntity<Collection<Song>> entity = new GenericEntity<Collection<Song>>(
				songs) {
		};
		return Response.ok(entity).build();
	}

	@POST
	@ApiOperation(value = "Add a new song. Id will be generated automatically.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Song added succesfully", response = Song.class),
			@ApiResponse(code = 400, message = "Invalid input", response = ValidationErrorResponse.class),
			@ApiResponse(code = 409, message = "Song with given id already exists", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class),
			@ApiResponse(code = 503, message = "Service unavailable", response = ErrorResponse.class) })
	@Consumes("application/json")
	public Response postSong(
			@ApiParam(value = "Song to be added", required = true) @Valid SongPost song) {

		Song newSong = songRepository.postSong(bookId, song);

		return Response.created(getCreatedUri(bookId)).entity(newSong).build();

	}

	@PUT
	@ApiOperation(value = "Update existing or add a new song.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Song updated succesfully"),
			@ApiResponse(code = 201, message = "New song created succesfully", response = Song.class),
			@ApiResponse(code = 400, message = "Invalid input", response = ValidationErrorResponse.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class),
			@ApiResponse(code = 503, message = "Service unavailable", response = ErrorResponse.class) })
	@Consumes("application/json")
	public Response putSong(
			@ApiParam(value = "Song to be added", required = true) @Valid Song song) {

		PutResult r = songRepository.putSong(bookId, song);

		if (r.getUpdatedRows() > 0) {

			return Response.ok("Song data updated succesfully.")
					.type(MediaType.TEXT_PLAIN).build();

		} else {

			Song newSong = new Song();
			newSong.setId(r.getInsertedId());
			newSong.setExtra(song.getExtra());
			newSong.setLyrics(song.getLyrics());
			newSong.setName(song.getName());
			newSong.setOtherNotes(song.getOtherNotes());
			newSong.setPageNumber(song.getPageNumber());
			newSong.setSongNumber(song.getSongNumber());

			return Response.created(getCreatedUri(r.getInsertedId())).entity(newSong)
					.build();
		}

	}

	@DELETE
	@Path("/{song_id}")
	@ApiOperation(value = "Delete a song and its' extra verses.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Song deleted succesfully"),
			@ApiResponse(code = 400, message = "Invalid input", response = ValidationErrorResponse.class),
			@ApiResponse(code = 404, message = "Song not found", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class),
			@ApiResponse(code = 503, message = "Service unavailable", response = ErrorResponse.class) })
	public Response deleteSong(
			@ApiParam(value = "Id of song to delete", required = true) @PathParam("song_id") String songId) {

		if (songRepository.deleteSong(bookId, songId)) {
			return Response.ok("Song deleted succesfully")
					.type(MediaType.TEXT_PLAIN).build();
		} else {
			throw new ApiException(ApiError.DELETE_SONG_NOT_FOUND);
		}

	}

	@GET
	@ApiOperation(value = "Get song data")
	@Path("/{song_id}")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Song data fetched succesfully", response = Song.class),
			@ApiResponse(code = 404, message = "Song not found", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class),
			@ApiResponse(code = 503, message = "Service unavailable", response = ErrorResponse.class) })
	public Response getSong(@PathParam("song_id") String songId) {

		Song song = songRepository.getSong(bookId, songId);

		if (song == null)
			throw new ApiException(ApiError.GET_SONG_NOT_FOUND);

		return Response.ok(song).build();

	}

	@Path("/{song_id}/extra-verses")
	public ExtraVerseResource locateToExtraVerseResource(
			@PathParam("song_id") String songId) {

		Song song = songRepository.getSong(bookId, songId);

		if (song == null)
			throw new ApiException(ApiError.GET_SONG_NOT_FOUND);

		return new ExtraVerseResource(uriInfo, verseRepository, bookId, songId);

	}

}
