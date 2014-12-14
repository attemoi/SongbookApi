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

import fi.attemoisio.songbookapi.errorhandling.ApiError;
import fi.attemoisio.songbookapi.exceptions.RepositoryException;
import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.repository.SongbookRepository;

public class PostgresSongbookRepository extends PostgresRepository implements
		SongbookRepository {

	@Inject
	public PostgresSongbookRepository(SQLDriverManager driver) {
		super(driver);
	}

	@Override
	public Collection<Songbook> getSongbooks() {

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
					throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_REQUEST_TIMEOUT,e);
				} finally {
					pst.close();
				}
			} catch (SQLException e) {
				throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_REQUEST_FAIL, e);
			} finally {
				conn.close();
			}
		} catch (SQLTimeoutException e) {
			throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_CONNECTION_TIMEOUT, e);
		} catch (SQLException e) {
			throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_CONNECTION_FAIL, e);
		}

	}

	@Override
	public Songbook getSongbook(String id) {
		String sql = "SELECT id, title, releaseYear, description, other_notes from songbooks WHERE id = ?";
		try {
			Connection conn = driver.getConnection();
			try {
				PreparedStatement pst = conn.prepareStatement(sql);
				try {
					pst.setMaxRows(1);
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					try {
						if (rs.next()) {
							Songbook book = new Songbook();
							book.setId(rs.getString("id"));
							book.setTitle(rs.getString("title"));
							book.setReleaseYear(rs.getInt("releaseYear"));
							book.setDescription(rs.getString("description"));
							book.setOtherNotes(rs.getString("other_notes"));
							return book;
						} else {
							return null;
						}
					} finally {
						rs.close();
					}
				} catch (SQLTimeoutException e) {
					throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_REQUEST_TIMEOUT, e);
				} finally {
					pst.close();
				}
			} catch (SQLException e) {
				throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_REQUEST_FAIL, e);
			} finally {
				conn.close();
			}
		} catch (SQLTimeoutException e) {
			throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_CONNECTION_TIMEOUT, e);
		} catch (SQLException e) {
			throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_CONNECTION_FAIL, e);
		}
	}

	@Override
	public boolean addSongbook(Songbook book) {

		final String sql = "INSERT INTO songbooks (id, title, releaseYear, description, other_notes) VALUES (?, ?, ?, ?, ?)";

		try {
			Connection conn = driver.getConnection();
			try {
				PreparedStatement pst = conn.prepareStatement(sql);
				try {
					pst.setString(1, book.getId());
					pst.setString(2, book.getTitle());
					pst.setInt(3, book.getReleaseYear());
					pst.setString(4, book.getDescription());
					pst.setString(5, book.getOtherNotes());

					pst.execute();
					return true;

				} catch (SQLTimeoutException e) {
					throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_REQUEST_TIMEOUT, e);
				} finally {
					pst.close();
				}
			} catch (SQLException e) {
				if (e.getSQLState().equals("23505")) // POSTGRESQL error code for unique_violation
					return false;
				else
					throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_REQUEST_FAIL, e);
			} finally {
				conn.close();
			}
		} catch (SQLTimeoutException e) {
			throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_CONNECTION_TIMEOUT, e);
		} catch (SQLException e) {
			throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_CONNECTION_FAIL, e);
		}

	}

	@Override
	public boolean deleteSongbook(String id) {

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
					throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_REQUEST_TIMEOUT, e);
				} finally {
					pst.close();
				}
			} catch (SQLException e) {
				throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_REQUEST_FAIL, e);
			} finally {
				conn.close();
			}
		} catch (SQLTimeoutException e) {
			throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_CONNECTION_TIMEOUT, e);
		} catch (SQLException e) {
			throw new RepositoryException(ApiError.SONGBOOK_REPOSITORY_CONNECTION_FAIL, e);
		}

	}
}
