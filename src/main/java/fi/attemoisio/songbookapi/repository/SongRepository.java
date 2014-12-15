package fi.attemoisio.songbookapi.repository;

import java.util.Collection;

import fi.attemoisio.songbookapi.model.Song;
import fi.attemoisio.songbookapi.model.SongPost;

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
	 * Adds a song. Id is generated automatically using song name.
	 * 
	 * @param bookId
	 * @param song
	 * @return Created song object.
	 */
	public Song postSong(String bookId, SongPost song);
	
	/**
	 * Adds or updates a song. (Upsert)
	 * 
	 * @param bookId
	 * @param song
	 * @return 1, if row was added. 2, if row was inserted. 0 for failure.
	 */
	public PutResult putSong(String bookId, Song song);

	/**
	 * Deletes a song from the repository
	 * 
	 * @param id
	 * @return True, if the song was successfully deleted. False, if the song
	 *         with the given id was not found.
	 */
	public boolean deleteSong(String bookId, String songId);

	public class PutResult {
		private int updatedRows;
		private String insertedId;
		public PutResult(int updatedRows, String insertedId) {
			this.updatedRows = updatedRows;
			this.insertedId = insertedId;
		}
		public String getInsertedId() {
			return insertedId;
		}
		public int getUpdatedRows() {
			return updatedRows;
		}
	}
	
}
