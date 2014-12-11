package fi.attemoisio.songbookapi.postgres;

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.repository.SongbookRepository;

@Resource
public class PostgresSongbookRepository implements SongbookRepository {

	public interface DbContract {
		public static final String HOST = "jdbc:postgresql://localhost:5432/";
		public static final String DB_NAME = "songbook_api";
		public static final String USERNAME = "postgres";
		public static final String PASSWORD = "postgres";
	}

	@Override
	public Collection<Songbook> getSongbooks() {

		PostgresHelper client = new PostgresHelper();

		try {
			client.connect();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		try {
			ResultSet rs = client
					.execQuery("SELECT id, title, releaseYear, description, other_notes from songbooks");
			ArrayList<Songbook> books = new ArrayList<Songbook>();
			
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public Songbook getSongbook(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Songbook addSongbook(Songbook book) {
		// TODO Auto-generated method stub
		return null;
	}

}
