package fi.attemoisio.songbookapi.errorhandling;

import javax.ws.rs.core.Response;

public enum ApiError {
	
	VALIDATION(0, "A validation error has occured.", Response.Status.BAD_REQUEST),
	
	// SONGBOOK REPOSITORY ///////////////////////////
	
	SONGBOOK_REPOSITORY_ERROR(0, "Could not establish connection to songbook repository.", Response.Status.SERVICE_UNAVAILABLE),
	SONGBOOK_REPOSITORY_TIMEOUT(0, "Songbook repository connection timed out.", Response.Status.SERVICE_UNAVAILABLE),
	
	ADD_SONGBOOK_ERROR(0, "Failed to add songbook.", Response.Status.INTERNAL_SERVER_ERROR),
	ADD_SONGBOOK_CONFLICT(0, "Songbook with the given id already exists", Response.Status.CONFLICT),
	ADD_SONGBOOK_TIMEOUT(0, "Songbook add request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	
	GET_SONGBOOK_ERROR(0, "Failed to get songbook data.", Response.Status.INTERNAL_SERVER_ERROR),
	GET_SONGBOOK_TIMEOUT(0, "Songbook get request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	GET_SONGBOOK_NOT_FOUND(0, "Songbook was not found.", Response.Status.NOT_FOUND),
	GET_SONGBOOKS_TIMEOUT(0, "Songbook get request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	GET_SONGBOOKS_ERROR(0, "Failed to get songbook list.", Response.Status.INTERNAL_SERVER_ERROR),
	GET_SONGBOOKS_NO_CONTENT(0, "No songbooks found.", Response.Status.NO_CONTENT),
	
	DELETE_SONGBOOK_ERROR(0, "Failed to delete songbook.", Response.Status.INTERNAL_SERVER_ERROR),
	DELETE_SONGBOOK_TIMEOUT(0, "Songbook delete request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	DELETE_SONGBOOK_NOT_FOUND(0, "Could not delete songbook (not found)", Response.Status.NOT_FOUND),
	
	// SONG REPOSITORY /////////////////////////
	
	SONG_REPOSITORY_ERROR(0, "Could not establish connection to song repository.", Response.Status.INTERNAL_SERVER_ERROR),
	SONG_REPOSITORY_TIMEOUT(0, "Song repository connection timed out.", Response.Status.SERVICE_UNAVAILABLE),
	
	ADD_SONG_ERROR(0, "Failed to add song.", Response.Status.INTERNAL_SERVER_ERROR),
	ADD_SONG_TIMEOUT(0, "Song add request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	ADD_SONG_CONFLICT(0, "Song with the given id already exists", Response.Status.CONFLICT),
	
	DELETE_SONG_ERROR(0, "Failed to delete song.", Response.Status.INTERNAL_SERVER_ERROR),
	DELETE_SONG_TIMEOUT(0, "Song delete request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	DELETE_SONG_NOT_FOUND(0, "Could not delete song (not found)", Response.Status.NOT_FOUND),
	
	GET_SONG_ERROR(0, "Failed to get song data.", Response.Status.INTERNAL_SERVER_ERROR),
	GET_SONG_TIMEOUT(0, "Song get request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	GET_SONG_NOT_FOUND(0, "Song was not found.", Response.Status.NOT_FOUND),
	
	GET_SONGS_ERROR(0, "Failed to get song list.", Response.Status.INTERNAL_SERVER_ERROR),
	GET_SONGS_TIMEOUT(0, "Song get request timed out.", Response.Status.SERVICE_UNAVAILABLE),  
	GET_SONGS_NO_CONTENT(0, "No songs found.", Response.Status.NO_CONTENT),
		
	// VERSE REPOSITORY ////////////////////
	
	VERSE_REPOSITORY_ERROR(0, "Could not establish connection to verse repository.", Response.Status.INTERNAL_SERVER_ERROR),
	VERSE_REPOSITORY_TIMEOUT(0, "Verse repository connection timed out.", Response.Status.SERVICE_UNAVAILABLE),
	
	ADD_VERSE_ERROR(0, "Failed to add verse.", Response.Status.INTERNAL_SERVER_ERROR),
	ADD_VERSE_TIMEOUT(0, "Verse add request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	
	DELETE_VERSE_ERROR(0, "Failed to delete verse.", Response.Status.INTERNAL_SERVER_ERROR),
	DELETE_VERSE_TIMEOUT(0, "Verse delete request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	DELETE_VERSE_NOT_FOUND(0, "Could not delete verse (not found)", Response.Status.NOT_FOUND),
	
	GET_VERSE_ERROR(0, "Failed to get verse data.", Response.Status.INTERNAL_SERVER_ERROR),
	GET_VERSE_TIMEOUT(0, "Verse get request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	GET_VERSE_NOT_FOUND(0, "Verse was not found.", Response.Status.NOT_FOUND), 
	
	GET_VERSES_ERROR(0, "Failed to get verse list.", Response.Status.INTERNAL_SERVER_ERROR),
	GET_VERSES_TIMEOUT(0, "Verse get request timed out.", Response.Status.SERVICE_UNAVAILABLE),  
	GET_VERSES_NO_CONTENT(0, "No verses found.", Response.Status.NO_CONTENT);
	
	private final int code;
	private final Response.Status httpStatus;
	private final String description;

	private ApiError(int code, String description, Response.Status httpStatus) {
		this.code = code;
		this.description = description;
		this.httpStatus = httpStatus;
	}

	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}

	public Response.Status getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String toString() {
		return code + ": " + description;
	}
}
