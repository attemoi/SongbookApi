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
