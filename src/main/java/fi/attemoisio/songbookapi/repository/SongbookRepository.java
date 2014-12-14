package fi.attemoisio.songbookapi.repository;

import java.util.Collection;

import fi.attemoisio.songbookapi.model.Songbook;

public interface SongbookRepository {

	/**
	 * Fetches all songbooks from the repository.
	 * 
	 * @return collection containing all songbooks.
	 */
	public Collection<Songbook> getSongbooks();

	/**
	 * Fetches a songbook with the given id from the repository.
	 * 
	 * @param id
	 * @return Songbook with the given id, null if book was not found.
	 */
	public Songbook getSongbook(String id);

	/**
	 * Adds a songbook to the repository.
	 * 
	 * @param book
	 * @return True, if the book was successfully added. False, if the book with
	 *         the given id already exists.
	 */
	public boolean addSongbook(Songbook book);

	/**
	 * Deletes a songbook from the repository
	 * 
	 * @param id
	 * @return True, if the book was successfully deleted. False, if the book
	 *         with the given id was not found.
	 */
	public boolean deleteSongbook(String id);

}
