package fi.attemoisio.songbookapi.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import fi.attemoisio.songbookapi.errorhandling.ApiError;
import fi.attemoisio.songbookapi.exceptions.RepositoryException;
import fi.attemoisio.songbookapi.model.Song;
import fi.attemoisio.songbookapi.repository.SongRepository;

public class PostgresSongRepository extends PostgresRepository implements
		SongRepository {

	@Inject
	public PostgresSongRepository(SQLDriverManager driver) {
		super(driver);
	}

	@Override
	public Collection<Song> getSongs(String bookId) {

		String sql = "SELECT id, name, extra, lyrics, song_number, other_notes, page_num, book_id"
				+ " FROM songs" + " WHERE book_id = ?";
		
		return handleConnection(conn -> {
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, bookId);
				try {
					ResultSet rs = pst.executeQuery();
					try {

						ArrayList<Song> songs = new ArrayList<Song>();

						while (rs.next()) {
							Song song = new Song();
							song.setId(rs.getString("id"));
							song.setName(rs.getString("name"));
							song.setLyrics(rs.getString("lyrics"));
							song.setPageNum(rs.getInt("page_num"));
							song.setExtra(rs.getString("extra"));
							song.setSongNumber(rs.getInt("song_number"));
							song.setOtherNotes(rs.getString("other_notes"));
							songs.add(song);
						}

						return songs;

					} finally {
						rs.close();
					}
				} finally {
					pst.close();
				}
		});

	}

	@Override
	public Song getSong(String bookId, String songId) {
		
		String sql = "SELECT id, name, extra, lyrics, song_number, other_notes, page_num, book_id"
				+ " FROM songs" + " WHERE id = ? AND book_id = ?";
		
		return handleConnection(conn -> {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, songId);
			pst.setString(2, bookId);
			try {
				ResultSet rs = pst.executeQuery();
				try {

					if (rs.next()) {
						Song song = new Song();
						song.setId(rs.getString("id"));
						song.setName(rs.getString("name"));
						song.setLyrics(rs.getString("lyrics"));
						song.setPageNum(rs.getInt("page_num"));
						song.setExtra(rs.getString("extra"));
						song.setSongNumber(rs.getInt("song_number"));
						song.setOtherNotes(rs.getString("other_notes"));
						return song;
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
	public boolean addSong(String bookId, Song book) {
		final String sql = "INSERT INTO songs (id, name, extra, lyrics, song_number, other_notes, page_num, book_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			return handleConnection(conn -> {
				PreparedStatement pst = conn.prepareStatement(sql);
				try {
					pst.setString(1, book.getId());
					pst.setString(2, book.getName());
					pst.setString(3, book.getExtra());
					pst.setString(4, book.getLyrics());
					pst.setInt(5, book.getSongNumber());
					pst.setString(6, book.getOtherNotes());
					pst.setInt(7, book.getPageNum());
					pst.setString(8, bookId);

					pst.execute();
					return true;

				} finally {
					pst.close();
				}
			});
		} catch (RepositoryException e) {
			if (e.getCause() instanceof SQLException
					&& ((SQLException) e.getCause()).getSQLState().equals(
							ERROR_CODE_UNIQUE_VIOLATION)) {
				return false;
			} else {
				throw e;
			}
		}

	}

	@Override
	public boolean deleteSong(String bookId, String songId) {

		final String sql = "DELETE FROM songs WHERE id = ? AND book_id = ?";

		return handleConnection(conn -> {
			PreparedStatement pst = conn.prepareStatement(sql);
			try {
				pst.setString(1, songId);
				pst.setString(2, bookId);
				int affectedRows = pst.executeUpdate();
				return affectedRows > 0;
			} catch (SQLTimeoutException e) {
				throw new RepositoryException(
						ApiError.SONG_REPOSITORY_REQUEST_TIMEOUT, e);
			} finally {
				pst.close();
			}
		});

	}

	@Override
	public ApiError getRepositoryConnectionFailApiError() {
		return ApiError.SONG_REPOSITORY_CONNECTION_FAIL;
	}

	@Override
	public ApiError getRepositoryConnectionTimeoutApiError() {
		return ApiError.SONG_REPOSITORY_CONNECTION_TIMEOUT;
	}

	@Override
	public ApiError getRepositoryRequestFailApiError() {
		return ApiError.SONG_REPOSITORY_REQUEST_FAIL;
	}

	@Override
	public ApiError getRepositoryRequestTimeoutApiError() {
		return ApiError.SONG_REPOSITORY_REQUEST_TIMEOUT;
	}
}
