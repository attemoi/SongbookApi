package fi.attemoisio.songbookapi.errorhandling;

import javax.ws.rs.core.Response;

public enum ApiError {
	
	VALIDATION(1000, "A validation error has occured.", Response.Status.BAD_REQUEST),
	
	// SONGBOOK REPOSITORY ///////////////////////////
	
	SONGBOOK_REPOSITORY_ERROR(2000, "Could not establish connection to songbook repository.", Response.Status.SERVICE_UNAVAILABLE),
	SONGBOOK_REPOSITORY_TIMEOUT(2001, "Songbook repository connection timed out.", Response.Status.SERVICE_UNAVAILABLE),
	
	ADD_SONGBOOK_ERROR(2100, "Failed to add songbook.", Response.Status.INTERNAL_SERVER_ERROR),
	ADD_SONGBOOK_CONFLICT(2101, "Songbook with the given id already exists", Response.Status.CONFLICT),
	ADD_SONGBOOK_TIMEOUT(2102, "Songbook add request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	
	UPDATE_SONGBOOK_ERROR(2200, "Failed to update songbook.", Response.Status.INTERNAL_SERVER_ERROR),
	UPDATE_SONGBOOK_TIMEOUT(2201, "Songbook update timed out.", Response.Status.SERVICE_UNAVAILABLE),

	GET_SONGBOOK_ERROR(2300, "Failed to get songbook data.", Response.Status.INTERNAL_SERVER_ERROR),
	GET_SONGBOOK_TIMEOUT(2301, "Songbook get request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	GET_SONGBOOK_NOT_FOUND(2302, "Songbook was not found.", Response.Status.NOT_FOUND),
	
	GET_SONGBOOKS_TIMEOUT(2401, "Songbook get request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	GET_SONGBOOKS_ERROR(2402, "Failed to get songbook list.", Response.Status.INTERNAL_SERVER_ERROR),
	GET_SONGBOOKS_NO_CONTENT(2403, "No songbooks found.", Response.Status.NO_CONTENT),
	
	DELETE_SONGBOOK_ERROR(2501, "Failed to delete songbook.", Response.Status.INTERNAL_SERVER_ERROR),
	DELETE_SONGBOOK_TIMEOUT(2502, "Songbook delete request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	DELETE_SONGBOOK_NOT_FOUND(2503, "Could not delete songbook (not found)", Response.Status.NOT_FOUND),
	
	// SONG REPOSITORY /////////////////////////
	
	SONG_REPOSITORY_ERROR(3000, "Could not establish connection to song repository.", Response.Status.INTERNAL_SERVER_ERROR),
	SONG_REPOSITORY_TIMEOUT(3001, "Song repository connection timed out.", Response.Status.SERVICE_UNAVAILABLE),
	
	ADD_SONG_ERROR(3100, "Failed to add song.", Response.Status.INTERNAL_SERVER_ERROR),
	ADD_SONG_TIMEOUT(3101, "Song add request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	ADD_SONG_CONFLICT(3102, "Song with the given id already exists", Response.Status.CONFLICT),

	UPDATE_SONG_ERROR(3200, "Failed to update song.", Response.Status.INTERNAL_SERVER_ERROR),
	UPDATE_SONG_TIMEOUT(3201, "Song update timed out.", Response.Status.SERVICE_UNAVAILABLE),	
	
	DELETE_SONG_ERROR(3300, "Failed to delete song.", Response.Status.INTERNAL_SERVER_ERROR),
	DELETE_SONG_TIMEOUT(3301, "Song delete request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	DELETE_SONG_NOT_FOUND(3302, "Could not delete song (not found)", Response.Status.NOT_FOUND),
	
	GET_SONG_ERROR(3400, "Failed to get song data.", Response.Status.INTERNAL_SERVER_ERROR),
	GET_SONG_TIMEOUT(3401, "Song get request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	GET_SONG_NOT_FOUND(3402, "Song was not found.", Response.Status.NOT_FOUND),
	
	GET_SONGS_ERROR(3500, "Failed to get song list.", Response.Status.INTERNAL_SERVER_ERROR),
	GET_SONGS_TIMEOUT(3501, "Song get request timed out.", Response.Status.SERVICE_UNAVAILABLE),  
	GET_SONGS_NO_CONTENT(3502, "No songs found.", Response.Status.NO_CONTENT),
		
	// VERSE REPOSITORY ////////////////////
	
	VERSE_REPOSITORY_ERROR(4000, "Could not establish connection to verse repository.", Response.Status.INTERNAL_SERVER_ERROR),
	VERSE_REPOSITORY_TIMEOUT(4001, "Verse repository connection timed out.", Response.Status.SERVICE_UNAVAILABLE),
	
	ADD_VERSE_ERROR(4100, "Failed to add verse.", Response.Status.INTERNAL_SERVER_ERROR),
	ADD_VERSE_TIMEOUT(4101, "Verse add request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	
	UPDATE_VERSE_ERROR(4200, "Failed to update verse data.", Response.Status.INTERNAL_SERVER_ERROR),
	UPDATE_VERSE_TIMEOUT(4201, "Verse update timed out.", Response.Status.SERVICE_UNAVAILABLE),

	DELETE_VERSE_ERROR(4300, "Failed to delete verse.", Response.Status.INTERNAL_SERVER_ERROR),
	DELETE_VERSE_TIMEOUT(4301, "Verse delete request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	DELETE_VERSE_NOT_FOUND(4302, "Could not delete verse (not found)", Response.Status.NOT_FOUND),
	
	GET_VERSE_ERROR(4400, "Failed to get verse data.", Response.Status.INTERNAL_SERVER_ERROR),
	GET_VERSE_TIMEOUT(4401, "Verse get request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	GET_VERSE_NOT_FOUND(4402, "Verse was not found.", Response.Status.NOT_FOUND), 
	
	GET_VERSES_ERROR(4500, "Failed to get verse list.", Response.Status.INTERNAL_SERVER_ERROR),
	GET_VERSES_TIMEOUT(4501, "Verse get request timed out.", Response.Status.SERVICE_UNAVAILABLE),  
	GET_VERSES_NO_CONTENT(4502, "No verses found.", Response.Status.NO_CONTENT);
	
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
