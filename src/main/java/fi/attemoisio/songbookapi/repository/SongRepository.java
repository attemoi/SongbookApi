package fi.attemoisio.songbookapi.repository;

/*
 * ###################################################################=
 * Songbook API
 * %%
 * Copyright (C) 2014 Atte Moisio
 * %%
 * The MIT License (MIT)
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 * ###################################################################-
 */

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
