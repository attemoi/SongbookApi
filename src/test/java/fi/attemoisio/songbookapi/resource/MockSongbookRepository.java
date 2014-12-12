package fi.attemoisio.songbookapi.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.repository.SongbookRepository;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionTimedOutException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestTimedOutException;

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
	public Collection<Songbook> getSongbooks()
			throws RepositoryConnectionFailedException,
			RepositoryRequestFailedException, RepositoryRequestTimedOutException {
		return books;
	}

	@Override
	public Songbook getSongbook(String id) {
		Songbook book = new Songbook();
		book.setDescription("test-description");
		book.setId(id);
		book.setOtherNotes("test-othernotes");
		book.setReleaseYear(1999);
		return book;
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
		
		boolean removed = false;
		for (Songbook book : books) {
			if (book.getId().equals(id)) {
				books.remove(book);
				return true;
			}
		}
		return removed;
	}

}
