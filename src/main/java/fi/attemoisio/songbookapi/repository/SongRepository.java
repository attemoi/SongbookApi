package fi.attemoisio.songbookapi.repository;

import java.util.Collection;

import fi.attemoisio.songbookapi.model.Song;

public interface SongRepository {

	/**
	 * Fetches all songs from a book.
	 * 
	 * @return collection containing all songbooks.
	 */
	public Collection<Song> getSongs(String bookId);

	/**
	 * Fetches a single song.
	 * 
	 * @param bookId
	 * @param songId
	 * @return Song with the given id, null if song was not found.
	 */
	public Song getSong(String bookId, String songId);

	/**
	 * Adds a song.
	 * 
	 * @param bookId
	 * @param song
	 * @return True, if the song was successfully added. False, if a song with
	 *         the given id already exists.
	 */
	public boolean addSong(String bookId, Song song);

	/**
	 * Deletes a song from the repository
	 * 
	 * @param id
	 * @return True, if the song was successfully deleted. False, if the song
	 *         with the given id was not found.
	 */
	public boolean deleteSong(String bookId, String id);

}
