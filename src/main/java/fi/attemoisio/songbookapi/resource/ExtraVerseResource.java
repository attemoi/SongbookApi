package fi.attemoisio.songbookapi.resource;

import java.net.URI;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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

import com.sun.jersey.api.NotFoundException;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import fi.attemoisio.songbookapi.model.ExtraVerse;
import fi.attemoisio.songbookapi.model.ExtraVerseId;
import fi.attemoisio.songbookapi.repository.ExtraVerseRepository;


@RequestScoped
//@Api(value = "extraverses", description = "Operations about extra verses")
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
public class ExtraVerseResource {

	UriInfo uriInfo;
	ExtraVerseRepository verseRepository;
	String bookId;
	String songId;
	
	public ExtraVerseResource(UriInfo uriInfo, ExtraVerseRepository verseRepository, String bookId, String songId) {
		this.uriInfo = uriInfo;
		this.verseRepository = verseRepository;
		this.bookId = bookId;
		this.songId = songId;
	}
	
	private URI getCreatedUri(String resourceId) {
		return uriInfo.getRequestUri().resolve("songbooks").resolve(resourceId);
	}

	@GET
	@ApiOperation(value = "List all extra verses for a song", notes = "Returns a list of all extra verses for a song.", response = ExtraVerseId.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Request completed succesfully"),
			@ApiResponse(code = 204, message = "No verses found"),
			@ApiResponse(code = 408, message = "Request timeout"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable") })
	public Response getExtraVerses() throws NoContentException {

		Collection<ExtraVerseId> extraVerses;

		extraVerses = verseRepository.getExtraVerses(bookId, songId);

		if (extraVerses.isEmpty())
			throw new NoContentException("No extra verses found");

		GenericEntity<Collection<ExtraVerseId>> entity = new GenericEntity<Collection<ExtraVerseId>>(
				extraVerses) {
		};
		return Response.ok(entity).build();
	}

	@POST
	@ApiOperation(value = "Add a new verse for a song")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Verse added succesfully"),
			@ApiResponse(code = 400, message = "Invalid input"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service unavailable") })
	@Consumes("application/json")
	public Response addExtraVerse(
			@ApiParam(value = "Verse to be added", required = true) @Valid ExtraVerse verse) {

		ExtraVerseId addedVerse = verseRepository.addExtraVerse(bookId, songId, verse);
		
		return Response.created(getCreatedUri(addedVerse.getId().toString())).entity(addedVerse)
				.build();

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
	public Response deleteSong(
			@ApiParam(value = "Id of verse to delete", required = true) @Min(0) @PathParam("verse_id") Integer verseId) {

		if (verseRepository.deleteExtraVerse(bookId, songId, verseId)) {
			return Response.ok("Verse deleted succesfully")
					.type(MediaType.TEXT_PLAIN).build();
		} else {
			throw new NotFoundException(
					"Could not delete verse (id not found).");
		}

	}

	@GET
	@ApiOperation(value = "Get data for extra verse")
	@Path("/{verse_id}")
	public Response getSong(@Min(0) @PathParam("verse_id") Integer verseId) {

		ExtraVerseId verse = verseRepository.getExtraVerse(bookId, songId, verseId);

		if (verse == null)
			throw new NotFoundException("Verse was not found.");

		return Response.ok(verse).build();

	}

}
