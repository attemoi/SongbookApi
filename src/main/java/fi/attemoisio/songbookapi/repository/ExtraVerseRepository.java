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
