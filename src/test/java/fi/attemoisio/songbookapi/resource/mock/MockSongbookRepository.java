package fi.attemoisio.songbookapi.resource.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.repository.SongbookRepository;

public class MockSongbookRepository implements SongbookRepository {

	List<Songbook> books;
	
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
	public boolean addSongbook(Songbook book) {
		if (books.stream().anyMatch(b -> b.getId().equals(book.getId()))) {
		    return false;
		} else {
		    books.add(book);
		    return true;
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
