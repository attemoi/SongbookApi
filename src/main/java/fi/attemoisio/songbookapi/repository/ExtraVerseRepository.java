package fi.attemoisio.songbookapi.repository;

import java.util.Collection;

import fi.attemoisio.songbookapi.model.ExtraVerse;
import fi.attemoisio.songbookapi.model.ExtraVersePost;

public interface ExtraVerseRepository {

	/**
	 * Fetches all extra verses for a song.
	 * 
	 * @return collection containing all extra verses for a song.
	 */
	public Collection<ExtraVerse> getExtraVerses(String bookId, String songId);

	/**
	 * Gets data for a verse.
	 * 
	 * @param bookId
	 * @param songId
	 * @param extraVerseid
	 * @return Extra verse with the given id, null if verse was not found.
	 */
	public ExtraVerse getExtraVerse(String bookId, String songId,
			Integer extraVerseId);

	/**
	 * Adds a new verse.
	 * 
	 * @param bookId
	 * @param songId
	 * @param verse
	 * @return True, if the verse was successfully added. False, if a verse with
	 *         the given id already exists.
	 */
	public ExtraVerse postExtraVerse(String bookId, String songId, ExtraVersePost verse);
	
	/**
	 * Updates verse data.
	 * 
	 * @param bookId
	 * @param songId
	 * @param verse
	 * @return 
	 */
	public void patchExtraVerse(String bookId, String songId, ExtraVerse verse);

	/**
	 * Deletes an extra verse from the repository
	 * 
	 * @param bookId
	 * @param songId
	 * @param verseId
	 * 
	 * @return True, if the verse was successfully deleted. False, if the verse
	 *         with the given id was not found.
	 */
	public boolean deleteExtraVerse(String bookId, String songId, Integer verseId);

}
