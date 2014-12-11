package fi.attemoisio.songbookapi.postgres;

import javax.sql.rowset.CachedRowSet;

import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryTimeoutException;

public interface SQLDriverManager {
	public CachedRowSet execQuery(String query)
			throws RepositoryConnectionFailedException,
			RepositoryRequestFailedException, RepositoryTimeoutException;
}
