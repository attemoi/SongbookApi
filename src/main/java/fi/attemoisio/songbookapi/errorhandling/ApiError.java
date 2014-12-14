package fi.attemoisio.songbookapi.errorhandling;

import javax.ws.rs.core.Response;

public enum ApiError {
	
	  VALIDATION(1000, "A validation error has occured.", Response.Status.BAD_REQUEST),
	  
	  SONGBOOK_REPOSITORY_CONNECTION_FAIL(1100, "Could not establish connection to songbook repository.", Response.Status.SERVICE_UNAVAILABLE),
	  SONGBOOK_REPOSITORY_CONNECTION_TIMEOUT(1112, "Songbook repository connection timed out.", Response.Status.SERVICE_UNAVAILABLE),
	  SONGBOOK_REPOSITORY_REQUEST_FAIL(1111, "Failed to make request to songbook repository.", Response.Status.INTERNAL_SERVER_ERROR),
	  SONGBOOK_REPOSITORY_REQUEST_TIMEOUT(1113, "Songbook repository request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	  
	  SONG_REPOSITORY_CONNECTION_FAIL(1120, "Could not establish connection to song repository.", Response.Status.INTERNAL_SERVER_ERROR),
	  SONG_REPOSITORY_CONNECTION_TIMEOUT(1121, "Song repository connection timed out.", Response.Status.SERVICE_UNAVAILABLE),
	  SONG_REPOSITORY_REQUEST_TIMEOUT(1122, "Song repository request timed out.", Response.Status.SERVICE_UNAVAILABLE),
	  SONG_REPOSITORY_REQUEST_FAIL(1123, "Failed to make request to song repository.", Response.Status.INTERNAL_SERVER_ERROR),
	  
	  VERSE_REPOSITORY_CONNECTION_FAIL(1130, "Failed to establish connection to verse repository.", Response.Status.INTERNAL_SERVER_ERROR),
	  VERSE_REPOSITORY_REQUEST_FAIL(1131, "Failed to make request to verse repository.", Response.Status.INTERNAL_SERVER_ERROR),
	  VERSE_REPOSITORY_CONNECTION_TIMEOUT(1132, "Verse repository connection timed out.", Response.Status.SERVICE_UNAVAILABLE),
	  VERSE_REPOSITORY_REQUEST_TIMEOUT(1133, "Verse repository request timed out.", Response.Status.SERVICE_UNAVAILABLE);

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
