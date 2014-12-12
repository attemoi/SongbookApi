package fi.attemoisio.songbookapi.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.repository.SongbookRepository;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionTimedOutException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestTimedOutException;

public class PostgresSongbookRepository extends PostgresRepository implements
		SongbookRepository {

	@Inject
	public PostgresSongbookRepository(SQLDriverManager driver) {
		super(driver);
	}

	@Override
	public Collection<Songbook> getSongbooks()
			throws RepositoryConnectionFailedException,
			RepositoryRequestFailedException,
			RepositoryRequestTimedOutException,
			RepositoryConnectionTimedOutException {

		try {
			Connection conn = driver.getConnection();
			try {
				Statement pst = conn.createStatement();
				try {
					ResultSet rs = pst
							.executeQuery("SELECT id, title, releaseYear, description, other_notes from songbooks");
					try {

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

					} finally {
						rs.close();
					}
				} catch (SQLTimeoutException e) {
					throw new RepositoryRequestTimedOutException("Songbook get request timed out");
				} finally {
					pst.close();
				}
			} catch (SQLException e) {
				throw new RepositoryRequestFailedException("Failed to request songbook list");
			} finally {
				conn.close();
			}
		} catch (SQLTimeoutException e) {
			throw new RepositoryConnectionTimedOutException("Songbook repository connection timed out");
		} catch (SQLException e) {
			throw new RepositoryConnectionFailedException("Songbook repository connection failed");
		}

	}

	@Override
	public Songbook getSongbook(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addSongbook(Songbook book)
			throws RepositoryConnectionFailedException,
			RepositoryRequestFailedException, RepositoryRequestTimedOutException, RepositoryConnectionTimedOutException {
		
		final String sql = "INSERT INTO songbooks (id, title, releaseYear, description, other_notes) VALUES (?, ?, ?, ?, ?)";

		try {
			Connection conn = driver.getConnection();
			try {
				PreparedStatement pst = conn.prepareStatement(sql);			
				try {
					pst.setString(1, book.getId());
					pst.setString(2, book.getTitle());
					pst.setInt   (3, book.getReleaseYear());
					pst.setString(4, book.getDescription());
					pst.setString(5, book.getOtherNotes());	
				
					pst.execute();
					return true;
					
				} catch (SQLTimeoutException e) {
					throw new RepositoryRequestTimedOutException("Songbook add request timed out.");
				} finally {
					pst.close();
				}
			} catch (SQLException e) {
				if (e.getSQLState().equals("23505")) // POSTGRESQL error code for unique_violation 
					return false;
				else
					throw new RepositoryRequestFailedException("Failed to add songbook.");
			} finally {
				conn.close();
			}
		} catch (SQLTimeoutException e) {
			throw new RepositoryConnectionTimedOutException("Songbook repository connection timed out.");
		} catch (SQLException e) {
			throw new RepositoryConnectionFailedException("Failed to establish connection to songbook repository.");
		}

	}
	
	@Override
	public boolean deleteSongbook(String id)
			throws RepositoryConnectionFailedException,
			RepositoryRequestFailedException, RepositoryRequestTimedOutException, RepositoryConnectionTimedOutException {
		
		final String sql = "DELETE FROM songbooks WHERE songbooks.id = ?";

		try {
			Connection conn = driver.getConnection();
			try {
				PreparedStatement pst = conn.prepareStatement(sql);			
				try {
					pst.setString(1, id);
					int affectedRows = pst.executeUpdate();
					return affectedRows > 0;
				} catch (SQLTimeoutException e) {
					throw new RepositoryRequestTimedOutException("Songbook add request timed out.");
				} finally {
					pst.close();
				}
			} catch (SQLException e) {
				throw new RepositoryRequestFailedException("Failed to delete songbook.");
			} finally {
				conn.close();
			}
		} catch (SQLTimeoutException e) {
			throw new RepositoryConnectionTimedOutException("Songbook repository connection timed out.");
		} catch (SQLException e) {
			throw new RepositoryConnectionFailedException("Failed to establish connection to songbook repository.");
		}

	}
}
