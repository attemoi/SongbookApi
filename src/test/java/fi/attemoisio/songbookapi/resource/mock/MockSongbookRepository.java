package fi.attemoisio.songbookapi.resource.mock;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.model.SongbookPost;
import fi.attemoisio.songbookapi.repository.SongbookRepository;

public class MockSongbookRepository implements SongbookRepository {

	List<Songbook> books;
	
	AtomicInteger sequenceNum = new AtomicInteger(10);
	
	public MockSongbookRepository() {
		
		books = new ArrayList<Songbook>();
		for (int i = 0; i < 5; i++) {
			Songbook book = new Songbook();
			book.setDescription("description lorem ipsum " + i);
			book.setId("book" + i);
			book.setOtherNotes("othernotes lorem ipsum " + i);
			book.setReleaseYear(1999 + i);
			books.add(book);
		}
		
	}
	
	@Override
	public Collection<Songbook> getSongbooks() {
		return books;
	}

	@Override
	public Songbook getSongbook(String id) {
		for (Songbook book : books) {
			if (book.getId().equals(id))
				return book;
		}
		return null;
	}
	
	@Override
	public Songbook postSongbook(SongbookPost book) {
		
		Songbook newSongbook = new Songbook();
		newSongbook.setId("songbook" + sequenceNum.getAndIncrement());
		newSongbook.setTitle(book.getTitle());
		newSongbook.setDescription(book.getDescription());
		newSongbook.setReleaseYear(book.getReleaseYear());
		newSongbook.setOtherNotes(book.getOtherNotes());
		
	    books.add(newSongbook);
	    
	    return newSongbook;
	}

	@Override
	public PutResult putSongbook(Songbook book) {
		
		Songbook existingBook = getSongbook(book.getId());
			
		if (existingBook == null) {
			return new PutResult(0, book.getId());
		} else {
			existingBook.setTitle(book.getTitle());
			existingBook.setOtherNotes(book.getOtherNotes());
			existingBook.setReleaseYear(book.getReleaseYear());
			existingBook.setDescription(book.getDescription());
			return new PutResult(1, null);
		}

	}

	@Override
	public boolean deleteSongbook(String id) {
		
		for (Songbook book : books) {
			if (book.getId().equals(id)) {
				books.remove(book);
				return true;
			}
		}
		return false;
	}

}
