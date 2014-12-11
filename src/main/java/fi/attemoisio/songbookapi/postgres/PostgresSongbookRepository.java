package fi.attemoisio.songbookapi.postgres;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.sql.rowset.CachedRowSet;

import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.repository.SongbookRepository;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryTimeoutException;

public class PostgresSongbookRepository implements SongbookRepository {

	SQLDriverManager driver;
	
    @Inject
	public PostgresSongbookRepository(SQLDriverManager driver) {
    	this.driver = driver;
    }

	@Override
	public Collection<Songbook> getSongbooks()
			throws RepositoryConnectionFailedException,
			RepositoryRequestFailedException, RepositoryTimeoutException {

		ArrayList<Songbook> books;
		try {
			CachedRowSet rs = driver
					.execQuery("SELECT id, title, releaseYear, description, other_notes from songbooks");
			try {
				books = new ArrayList<Songbook>();
				while (rs.next()) {
					Songbook book = new Songbook();
					book.setId(rs.getString("id"));
					book.setTitle(rs.getString("title"));
					book.setReleaseYear(rs.getInt("releaseYear"));
					book.setDescription(rs.getString("description"));
					book.setOtherNotes(rs.getString("other_notes"));
					books.add(book);
				}
				return books;
			} finally {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RepositoryRequestFailedException(e.getMessage());
		}

	}

	@Override
	public Songbook getSongbook(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addSongbook(Songbook book) {
		// TODO Auto-generated method stub
		return true;
	}
}
