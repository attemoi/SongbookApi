package fi.attemoisio.songbookapi.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.repository.SongbookRepository;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryTimeoutException;

public class MockSongbookRepository implements SongbookRepository {

	@Override
	public Collection<Songbook> getSongbooks()
			throws RepositoryConnectionFailedException,
			RepositoryRequestFailedException, RepositoryTimeoutException {
		
		List<Songbook> books = new ArrayList<Songbook>();
		
		for (int i = 0; i < 5; i++) {
			Songbook book = new Songbook();
			book.setDescription("description lorem ipsum");
			book.setId("book" + i);
			book.setOtherNotes("othernotes lorem ipsum");
			book.setReleaseYear(1999 + i);
			books.add(book);
		}
		
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
	public Songbook addSongbook(Songbook book) {
		return book;
	}

}
