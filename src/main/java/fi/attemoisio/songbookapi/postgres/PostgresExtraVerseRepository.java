package fi.attemoisio.songbookapi.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import fi.attemoisio.songbookapi.model.ExtraVerse;
import fi.attemoisio.songbookapi.model.ExtraVerseId;
import fi.attemoisio.songbookapi.repository.ExtraVerseRepository;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionTimedOutException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestTimedOutException;

public class PostgresExtraVerseRepository extends PostgresRepository implements
		ExtraVerseRepository {

	@Inject
	public PostgresExtraVerseRepository(SQLDriverManager driver) {
		super(driver);
	}

	@Override
	public Collection<ExtraVerseId> getExtraVerses(String bookId, String songId) {

		String sql = "SELECT id, lyrics, song_id, book_id"
				+ " FROM extra_verses" + " WHERE song_id = ? AND book_id = ?";
		try {
			Connection conn = driver.getConnection();
			try {
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, songId);
				pst.setString(2, bookId);
				try {
					ResultSet rs = pst.executeQuery();
					try {

						ArrayList<ExtraVerseId> verses = new ArrayList<ExtraVerseId>();

						while (rs.next()) {
							ExtraVerseId verse = new ExtraVerseId();
							verse.setId(rs.getInt("id"));
							verse.setLyrics(rs.getString("lyrics"));
							verses.add(verse);
						}

						return verses;

					} finally {
						rs.close();
					}
				} catch (SQLTimeoutException e) {
					throw new RepositoryRequestTimedOutException(
							"Verse list request timed out");
				} finally {
					pst.close();
				}
			} catch (SQLException e) {
				throw new RepositoryRequestFailedException(
						"Failed to request list of verses");
			} finally {
				conn.close();
			}
		} catch (SQLTimeoutException e) {
			throw new RepositoryConnectionTimedOutException(
					"Verse repository connection timed out");
		} catch (SQLException e) {
			throw new RepositoryConnectionFailedException(
					"Verse repository connection failed");
		}

	}

	@Override
	public ExtraVerseId getExtraVerse(String bookId, String songId,
			Integer verseId) {
		String sql = "SELECT id, lyrics" + " FROM extra_verses"
				+ " WHERE book_id = ? AND song_id = ? AND id = ?";
		try {
			Connection conn = driver.getConnection();
			try {
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, bookId);
				pst.setString(2, songId);
				pst.setInt(3, verseId);
				try {
					ResultSet rs = pst.executeQuery();
					try {
						if (rs.next()) {
							ExtraVerseId verse = new ExtraVerseId();
							verse.setId(rs.getInt("id"));
							verse.setLyrics(rs.getString("lyrics"));
							return verse;
						} else {
							return null;
						}
					} finally {
						rs.close();
					}
				} catch (SQLTimeoutException e) {
					throw new RepositoryRequestTimedOutException(
							"Verse request timed out");
				} finally {
					pst.close();
				}
			} catch (SQLException e) {
				throw new RepositoryRequestFailedException(
						"Failed to request verse data");
			} finally {
				conn.close();
			}
		} catch (SQLTimeoutException e) {
			throw new RepositoryConnectionTimedOutException(
					"Verse repository connection timed out");
		} catch (SQLException e) {
			throw new RepositoryConnectionFailedException(
					"Verse repository connection failed");
		}
	}

	@Override
	public ExtraVerseId addExtraVerse(String bookId, String songId,
			ExtraVerse verse) {

		final String sql = "INSERT INTO extra_verses (book_id, song_id, lyrics) "
				+ "VALUES (?, ?, ?) RETURNING id";

		try {
			Connection conn = driver.getConnection();
			try {
				PreparedStatement pst = conn.prepareStatement(sql);
				try {
					pst.setString(1, bookId);
					pst.setString(2, songId);
					pst.setString(3, verse.getLyrics());

					ResultSet rs = pst.executeQuery();
					try {
						Integer generatedId = rs.getInt("id");
						ExtraVerseId newVerse = new ExtraVerseId();
						newVerse.setId(generatedId);
						newVerse.setLyrics(verse.getLyrics());
						return newVerse;
					} finally {
						rs.close();
					}

				} catch (SQLTimeoutException e) {
					throw new RepositoryRequestTimedOutException(
							"Verse add request timed out.");
				} finally {
					pst.close();
				}
			} catch (SQLException e) {
				throw new RepositoryRequestFailedException(
						"Failed to add verse.");
			} finally {
				conn.close();
			}
		} catch (SQLTimeoutException e) {
			throw new RepositoryConnectionTimedOutException(
					"Verse repository connection timed out.");
		} catch (SQLException e) {
			throw new RepositoryConnectionFailedException(
					"Failed to establish connection to verse repository.");
		}

	}

	@Override
	public boolean deleteExtraVerse(String bookId, String songId, Integer verseId) {

		final String sql = "DELETE FROM extra_verses WHERE id = ? AND song_id = ? and book_id = ?";

		try {
			Connection conn = driver.getConnection();
			try {
				PreparedStatement pst = conn.prepareStatement(sql);
				try {
					pst.setInt(1, verseId);
					pst.setString(2, songId);
					pst.setString(3, bookId);
					int affectedRows = pst.executeUpdate();
					return affectedRows > 0;
				} catch (SQLTimeoutException e) {
					throw new RepositoryRequestTimedOutException("Verse delete request timed out.");
				} finally {
					pst.close();
				}
			} catch (SQLException e) {
				throw new RepositoryRequestFailedException(
						"Failed to delete verse.");
			} finally {
				conn.close();
			}
		} catch (SQLTimeoutException e) {
			throw new RepositoryConnectionTimedOutException(
					"Verse repository connection timed out.");
		} catch (SQLException e) {
			throw new RepositoryConnectionFailedException(
					"Failed to establish connection to verse repository.");
		}

	}

}
