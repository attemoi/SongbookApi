package fi.attemoisio.songbookapi.repository;

import java.util.Collection;

import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionTimedOutException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestTimedOutException;

public interface SongbookRepository {

	/**
	 * Fetches all songbooks from the repository.
	 * 
	 * @return collection containing all songbooks.
	 * @throws RepositoryConnectionFailedException
	 * @throws RepositoryRequestFailedException
	 * @throws RepositoryRequestTimedOutException
	 * @throws RepositoryConnectionTimedOutException
	 */
	public Collection<Songbook> getSongbooks()
			throws RepositoryConnectionFailedException,
			RepositoryRequestFailedException,
			RepositoryRequestTimedOutException,
			RepositoryConnectionTimedOutException;

	/**
	 * Fetches a songbook with the given id from the repository.
	 * 
	 * @param id
	 * @return Songbook with the given id, null if book was not found.
	 * @throws RepositoryConnectionFailedException
	 * @throws RepositoryRequestFailedException
	 * @throws RepositoryRequestTimedOutException
	 */
	public Songbook getSongbook(String id)
			throws RepositoryConnectionFailedException,
			RepositoryRequestFailedException,
			RepositoryRequestTimedOutException;

	/**
	 * Adds a songbook to the repository.
	 * 
	 * @param book
	 * @return True, if the book was successfully added. False, if the book with
	 *         the given id already exists.
	 * @throws RepositoryConnectionTimedOutException
	 */
	public boolean addSongbook(Songbook book)
			throws RepositoryConnectionFailedException,
			RepositoryRequestFailedException,
			RepositoryRequestTimedOutException,
			RepositoryConnectionTimedOutException;

	/**
	 * Deletes a songbook from the repository
	 * 
	 * @param id
	 * @return True, if the book was successfully deleted. False, if the book
	 *         with the given id was not found.
	 * @throws RepositoryConnectionFailedException
	 * @throws RepositoryRequestFailedException
	 * @throws RepositoryRequestTimedOutException
	 * @throws RepositoryConnectionTimedOutException
	 */
	public boolean deleteSongbook(String id)
			throws RepositoryConnectionFailedException,
			RepositoryRequestFailedException,
			RepositoryRequestTimedOutException,
			RepositoryConnectionTimedOutException;

}
