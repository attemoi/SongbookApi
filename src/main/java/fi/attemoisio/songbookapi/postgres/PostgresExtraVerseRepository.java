package fi.attemoisio.songbookapi.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import fi.attemoisio.songbookapi.errorhandling.ApiError;
import fi.attemoisio.songbookapi.exceptions.RepositoryException;
import fi.attemoisio.songbookapi.model.ExtraVerse;
import fi.attemoisio.songbookapi.model.ExtraVersePost;
import fi.attemoisio.songbookapi.repository.ExtraVerseRepository;

public class PostgresExtraVerseRepository extends PostgresRepository implements
		ExtraVerseRepository {

	@Inject
	public PostgresExtraVerseRepository(SQLDriverManager driver) {
		super(driver);
	}

	@Override
	public Collection<ExtraVerse> getExtraVerses(String bookId, String songId) {

		String sql = "SELECT id, lyrics, song_id, book_id"
				+ " FROM extra_verses" + " WHERE song_id = ? AND book_id = ?";

		return handleConnection(conn -> {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, songId);
			pst.setString(2, bookId);
			try {
				ResultSet rs = pst.executeQuery();
				try {

					ArrayList<ExtraVerse> verses = new ArrayList<ExtraVerse>();

					while (rs.next()) {
						ExtraVerse verse = new ExtraVerse();
						verse.setId(rs.getInt("id"));
						verse.setLyrics(rs.getString("lyrics"));
						verses.add(verse);
					}

					return verses;

				} finally {
					rs.close();
				}
			} finally {
				pst.close();
			}
		});

	}

	@Override
	public ExtraVerse getExtraVerse(String bookId, String songId,
			Integer verseId) {
		
		String sql = "SELECT id, lyrics, book_id, song_id, id FROM extra_verses"
				+ " WHERE book_id = ? AND song_id = ? AND id = ?";
		
		return handleConnection(conn -> {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, bookId);
			pst.setString(2, songId);
			pst.setInt(3, verseId);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs.next()) {
						ExtraVerse verse = new ExtraVerse();
						verse.setId(rs.getInt("id"));
						verse.setLyrics(rs.getString("lyrics"));
						return verse;
					} else {
						return null;
					}
				} finally {
					rs.close();
				}
			} finally {
				pst.close();
			}
		});
	}

	@Override
	public ExtraVerse addExtraVerse(String bookId, String songId,
			ExtraVersePost verse) {

		final String sql = "INSERT INTO extra_verses (book_id, song_id, lyrics) "
				+ "VALUES (?, ?, ?) RETURNING id";

		return handleConnection(conn -> {
				PreparedStatement pst = conn.prepareStatement(sql);
				try {
					pst.setString(1, bookId);
					pst.setString(2, songId);
					pst.setString(3, verse.getLyrics());

					ResultSet rs = pst.executeQuery();
					try {
						Integer generatedId = rs.getInt("id");
						ExtraVerse newVerse = new ExtraVerse();
						newVerse.setId(generatedId);
						newVerse.setLyrics(verse.getLyrics());
						return newVerse;
					} finally {
						rs.close();
					}

				} finally {
					pst.close();
				}
		});

	}

	@Override
	public boolean deleteExtraVerse(String bookId, String songId,
			Integer verseId) {

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
					throw new RepositoryException(
							ApiError.VERSE_REPOSITORY_REQUEST_TIMEOUT, e);
				} finally {
					pst.close();
				}
			} catch (SQLException e) {
				throw new RepositoryException(
						ApiError.VERSE_REPOSITORY_REQUEST_FAIL, e);
			} finally {
				conn.close();
			}
		} catch (SQLTimeoutException e) {
			throw new RepositoryException(
					ApiError.VERSE_REPOSITORY_CONNECTION_TIMEOUT, e);
		} catch (SQLException e) {
			throw new RepositoryException(
					ApiError.VERSE_REPOSITORY_CONNECTION_FAIL, e);
		}

	}

	@Override
	public ApiError getRepositoryConnectionFailApiError() {
		return ApiError.VERSE_REPOSITORY_CONNECTION_FAIL;
	}

	@Override
	public ApiError getRepositoryConnectionTimeoutApiError() {
		return ApiError.VERSE_REPOSITORY_CONNECTION_TIMEOUT;
	}

	@Override
	public ApiError getRepositoryRequestFailApiError() {
		return ApiError.VERSE_REPOSITORY_REQUEST_FAIL;
	}

	@Override
	public ApiError getRepositoryRequestTimeoutApiError() {
		return ApiError.VERSE_REPOSITORY_REQUEST_TIMEOUT;
	}

}
