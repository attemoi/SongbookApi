package fi.attemoisio.songbookapi.resource;

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

import com.sun.jersey.api.ConflictException;
import com.sun.jersey.api.NotFoundException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import fi.attemoisio.songbookapi.model.Song;
import fi.attemoisio.songbookapi.repository.ExtraVerseRepository;
import fi.attemoisio.songbookapi.repository.SongRepository;
import fi.attemoisio.songbookapi.validation.Slug;

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
			@ApiResponse(code = 200, message = "Request completed succesfully"),
			@ApiResponse(code = 204, message = "No songs found"),
			@ApiResponse(code = 408, message = "Request timeout"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable") })
	public Response getSongs() throws NoContentException {

		Collection<Song> songs;

		songs = songRepository.getSongs(bookId);

		if (songs.isEmpty())
			throw new NoContentException("No songs found");

		GenericEntity<Collection<Song>> entity = new GenericEntity<Collection<Song>>(
				songs) {
		};
		return Response.ok(entity).build();
	}

	@POST
	@ApiOperation(value = "Add a new song")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Song added succesfully"),
			@ApiResponse(code = 400, message = "Invalid input"),
			@ApiResponse(code = 409, message = "Song with given id already exists"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable") })
	@Consumes("application/json")
	public Response addSong(
			@ApiParam(value = "Song to be added", required = true) @Valid Song song) {

		if (songRepository.addSong(bookId, song)) {
			return Response.created(getCreatedUri(bookId)).entity(song).build();
		} else {
			throw new ConflictException("Song with given id already exists");
		}

	}

	@DELETE
	@Path("/{song_id}")
	@ApiOperation(value = "Delete a song")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Song deleted succesfully"),
			@ApiResponse(code = 400, message = "Invalid input"),
			@ApiResponse(code = 404, message = "Song not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable") })
	public Response deleteSong(
			@ApiParam(value = "Id of song to delete", required = true) @Slug @PathParam("song_id") String songId) {

		if (songRepository.deleteSong(bookId, songId)) {
			return Response.ok("Song deleted succesfully")
					.type(MediaType.TEXT_PLAIN).build();
		} else {
			throw new NotFoundException("Could not delete song (id not found).");
		}

	}

	@GET
	@ApiOperation(value = "Get song data")
	@Path("/{song_id}")
	public Response getSong(@Slug @PathParam("song_id") String songId) {

		Song song = songRepository.getSong(bookId, songId);

		if (song == null)
			throw new NotFoundException("Song was not found.");

		return Response.ok(song).build();

	}

	@Path("/{song_id}/extra-verses")
	public ExtraVerseResource locateToExtraVerseResource(
			@Slug @PathParam("song_id") String songId) {

		Song song = songRepository.getSong(bookId, songId);

		if (song == null)
			throw new NotFoundException("Song was not found.");

		return new ExtraVerseResource(uriInfo, verseRepository, bookId,
				song.getId());

	}

}
