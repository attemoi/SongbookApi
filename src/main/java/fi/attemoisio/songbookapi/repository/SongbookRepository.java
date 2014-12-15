package fi.attemoisio.songbookapi.repository;

import java.util.Collection;

import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.model.SongbookPost;

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
	 * Creates new songbook. Id is generated automatically from title.
	 * 
	 * @param book
	 * @return Created songbook.
	 */
	public Songbook postSongbook(SongbookPost book);
	
	/**
	 * Adds or updates a songbook.
	 * 
	 * @param book
	 * @return PostResult with information about the operation.
	 */
	public PutResult putSongbook(Songbook book);

	/**
	 * Deletes a songbook from the repository
	 * 
	 * @param id
	 * @return True, if the book was successfully deleted. False, if the book
	 *         with the given id was not found.
	 */
	public boolean deleteSongbook(String id);
	
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
