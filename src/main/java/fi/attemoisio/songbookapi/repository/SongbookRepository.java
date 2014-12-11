package fi.attemoisio.songbookapi.repository;

import java.util.Collection;

import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryTimeoutException;

public interface SongbookRepository {

	/**
	 * Fetches all songbooks from the repository.
	 * 
	 * @return collection containing all songbooks.
	 * @throws RepositoryConnectionFailedException
	 * @throws RepositoryRequestFailedException
	 * @throws RepositoryTimeoutException
	 */
	public Collection<Songbook> getSongbooks()
			throws RepositoryConnectionFailedException,
			RepositoryRequestFailedException, RepositoryTimeoutException;

	/**
	 * Fetches a songbook with the given id from the repository.
	 * 
	 * @param id
	 * @return Songbook with the given id, null if book was not found.
	 * @throws RepositoryConnectionFailedException
	 * @throws RepositoryRequestFailedException
	 * @throws RepositoryTimeoutException
	 */
	public Songbook getSongbook(String id)
			throws RepositoryConnectionFailedException,
			RepositoryRequestFailedException, RepositoryTimeoutException;

	/**
	 * Adds a songbook to the repository.
	 * 
	 * @param book
	 * @return true, if the book was succesfully added, false if the book with
	 *         the same id has already been added to the repository.
	 */
	public boolean addSongbook(Songbook book)
			throws RepositoryConnectionFailedException,
			RepositoryRequestFailedException, RepositoryTimeoutException;

}
