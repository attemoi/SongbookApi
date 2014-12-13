package fi.attemoisio.songbookapi.repository;

import java.util.Collection;

import fi.attemoisio.songbookapi.model.Song;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionTimedOutException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestTimedOutException;

public interface SongRepository {

	/**
	 * Fetches all songs from a book.
	 * 
	 * @return collection containing all songbooks.
	 * @throws RepositoryConnectionFailedException
	 * @throws RepositoryRequestFailedException
	 * @throws RepositoryRequestTimedOutException
	 * @throws RepositoryConnectionTimedOutException
	 */
	public Collection<Song> getSongs(String bookId);

	/**
	 * Fetches a single song.
	 * 
	 * @param bookId
	 * @param songId
	 * @return Song with the given id, null if song was not found.
	 * @throws RepositoryConnectionFailedException
	 * @throws RepositoryRequestFailedException
	 * @throws RepositoryRequestTimedOutException
	 * @throws RepositoryConnectionTimedOutException
	 */
	public Song getSong(String bookId, String songId);

	/**
	 * Adds a song.
	 * 
	 * @param bookId
	 * @param song
	 * @return True, if the song was successfully added. False, if a song with
	 *         the given id already exists.
	 * @throws RepositoryConnectionFailedException
	 * @throws RepositoryRequestFailedException
	 * @throws RepositoryRequestTimedOutException
	 * @throws RepositoryConnectionTimedOutException
	 */
	public boolean addSong(String bookId, Song song);

	/**
	 * Deletes a song from the repository
	 * 
	 * @param id
	 * @return True, if the song was successfully deleted. False, if the song
	 *         with the given id was not found.
	 * @throws RepositoryConnectionFailedException
	 * @throws RepositoryRequestFailedException
	 * @throws RepositoryRequestTimedOutException
	 * @throws RepositoryConnectionTimedOutException
	 */
	public boolean deleteSong(String bookId, String id);

}
