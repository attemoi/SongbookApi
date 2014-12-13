package fi.attemoisio.songbookapi.repository;

import java.util.Collection;

import fi.attemoisio.songbookapi.model.ExtraVersePost;
import fi.attemoisio.songbookapi.model.ExtraVerse;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionTimedOutException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestTimedOutException;

public interface ExtraVerseRepository {

	/**
	 * Fetches all extra verses for a song.
	 * 
	 * @return collection containing all extra verses for a song.
	 * @throws RepositoryConnectionFailedException
	 * @throws RepositoryRequestFailedException
	 * @throws RepositoryRequestTimedOutException
	 * @throws RepositoryConnectionTimedOutException
	 */
	public Collection<ExtraVerse> getExtraVerses(String bookId, String songId);

	/**
	 * Fetches an extra verse for a song.
	 * 
	 * @param bookId
	 * @param songId
	 * @param extraVerseid
	 * @return Extra verse with the given id, null if verse was not found.
	 * @throws RepositoryConnectionFailedException
	 * @throws RepositoryRequestFailedException
	 * @throws RepositoryRequestTimedOutException
	 * @throws RepositoryConnectionTimedOutException
	 */
	public ExtraVerse getExtraVerse(String bookId, String songId,
			Integer extraVerseId);

	/**
	 * Adds an extra verse to a song.
	 * 
	 * @param bookId
	 * @param songId
	 * @param verse
	 * @return True, if the verse was successfully added. False, if a verse with
	 *         the given id already exists.
	 * @throws RepositoryConnectionFailedException
	 * @throws RepositoryRequestFailedException
	 * @throws RepositoryRequestTimedOutException
	 * @throws RepositoryConnectionTimedOutException
	 */
	public ExtraVerse addExtraVerse(String bookId, String songId, ExtraVersePost verse);

	/**
	 * Deletes an extra verse from the repository
	 * 
	 * @param bookId
	 * @param songId
	 * @param verseId
	 * 
	 * @return True, if the verse was successfully deleted. False, if the verse
	 *         with the given id was not found.
	 * @throws RepositoryConnectionFailedException
	 * @throws RepositoryRequestFailedException
	 * @throws RepositoryRequestTimedOutException
	 * @throws RepositoryConnectionTimedOutException
	 */
	public boolean deleteExtraVerse(String bookId, String songId, Integer verseId);

}
