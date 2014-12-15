package fi.attemoisio.songbookapi.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import fi.attemoisio.songbookapi.errorhandling.ApiError;
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
		}, ApiError.GET_VERSES_ERROR, ApiError.GET_VERSES_TIMEOUT);

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
		}, ApiError.GET_VERSE_ERROR, ApiError.GET_VERSE_TIMEOUT);
	}

	@Override
	public ExtraVerse postExtraVerse(String bookId, String songId,
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
					if (rs.next()) {
						Integer generatedId = rs.getInt("id");
						ExtraVerse newVerse = new ExtraVerse();
						newVerse.setId(generatedId);
						newVerse.setLyrics(verse.getLyrics());
						return newVerse;
					} else {
						return null;
					}
				} finally {
					rs.close();
				}

			} finally {
				pst.close();
			}
		}, ApiError.ADD_VERSE_ERROR, ApiError.ADD_VERSE_TIMEOUT);

	}

	@Override
	public boolean deleteExtraVerse(String bookId, String songId,
			Integer verseId) {

		final String sql = "DELETE FROM extra_verses WHERE id = ? AND song_id = ? and book_id = ?";

		return handleConnection(conn -> {
			PreparedStatement pst = conn.prepareStatement(sql);
			try {
				pst.setInt(1, verseId);
				pst.setString(2, songId);
				pst.setString(3, bookId);
				int affectedRows = pst.executeUpdate();
				return affectedRows > 0;
			} finally {
				pst.close();
			}
		}, ApiError.DELETE_VERSE_ERROR, ApiError.DELETE_VERSE_TIMEOUT);

	}

	@Override
	public void patchExtraVerse(String bookId, String songId,
			ExtraVerse verse) {
		
		final String sql = "UPDATE extra_verses "
				+ " SET lyrics = ? WHERE id = ? AND book_id = ? AND song_id = ?";

		handleConnection(conn -> {
			
			PreparedStatement pst = conn.prepareStatement(sql);
			
			try {

				// SET
				pst.setString(1, verse.getLyrics());
				pst.setInt(2, verse.getId());
				pst.setString(3, bookId);
				pst.setString(4, songId);

				int updatedRows = pst.executeUpdate();
				
				if (updatedRows == 0) {
					throw new fi.attemoisio.songbookapi.exceptions.ApiException(ApiError.UPDATE_VERSE_NOT_FOUND);
				}

			} finally {
				pst.close();
			}
			
			return null;

		}, ApiError.UPDATE_VERSE_ERROR, ApiError.UPDATE_VERSE_TIMEOUT);
	}

	@Override
	public ApiError getRepositoryConnectionFailApiError() {
		return ApiError.VERSE_REPOSITORY_ERROR;
	}

	@Override
	public ApiError getRepositoryConnectionTimeoutApiError() {
		return ApiError.VERSE_REPOSITORY_TIMEOUT;
	}

}
