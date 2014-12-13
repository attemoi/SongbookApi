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
import fi.attemoisio.songbookapi.model.Song;
import fi.attemoisio.songbookapi.repository.ExtraVerseRepository;
import fi.attemoisio.songbookapi.repository.SongRepository;
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

//	@Override
//	public Collection<Song> getSongs(String bookId) {
//
//		String sql = "SELECT id, name, extra, lyrics, song_number, other_notes, page_num, book_id"
//				+ " FROM songs"
//				+ " WHERE book_id = ?";
//		try {
//			Connection conn = driver.getConnection();
//			try {
//				PreparedStatement pst = conn.prepareStatement(sql);
//				pst.setString(1, bookId);
//				try {
//					ResultSet rs = pst.executeQuery();
//					try {
//
//						ArrayList<Song> songs = new ArrayList<Song>();
//
//						while (rs.next()) {
//							Song song = new Song();
//							song.setId(rs.getString("id"));
//							song.setName(rs.getString("name"));
//							song.setLyrics(rs.getString("lyrics"));
//							song.setPageNum(rs.getInt("page_num"));
//							song.setExtra(rs.getString("extra"));
//							song.setSongNumber(rs.getInt("song_number"));
//							song.setOtherNotes(rs.getString("other_notes"));
//							songs.add(song);
//						}
//
//						return songs;
//
//					} finally {
//						rs.close();
//					}
//				} catch (SQLTimeoutException e) {
//					throw new RepositoryRequestTimedOutException(
//							"Song list request timed out");
//				} finally {
//					pst.close();
//				}
//			} catch (SQLException e) {
//				throw new RepositoryRequestFailedException(
//						"Failed to request song list");
//			} finally {
//				conn.close();
//			}
//		} catch (SQLTimeoutException e) {
//			throw new RepositoryConnectionTimedOutException(
//					"Song repository connection timed out");
//		} catch (SQLException e) {
//			throw new RepositoryConnectionFailedException(
//					"Song repository connection failed");
//		}
//
//	}
//
//	@Override
//	public Song getSong(String bookId, String songId) {
//		String sql = "SELECT id, name, extra, lyrics, song_number, other_notes, page_num, book_id"
//				+ " FROM songs"
//				+ " WHERE id = ? AND book_id = ?";
//		try {
//			Connection conn = driver.getConnection();
//			try {
//				PreparedStatement pst = conn.prepareStatement(sql);
//				pst.setString(1, songId);
//				pst.setString(2, bookId);
//				try {
//					ResultSet rs = pst.executeQuery();
//					try {
//
//						ArrayList<Song> songs = new ArrayList<Song>();
//
//						if (rs.next()) {
//							Song song = new Song();
//							song.setId(rs.getString("id"));
//							song.setName(rs.getString("name"));
//							song.setLyrics(rs.getString("lyrics"));
//							song.setPageNum(rs.getInt("page_num"));
//							song.setExtra(rs.getString("extra"));
//							song.setSongNumber(rs.getInt("song_number"));
//							song.setOtherNotes(rs.getString("other_notes"));
//							songs.add(song);
//							return song;
//						}
//
//						return null;
//
//					} finally {
//						rs.close();
//					}
//				} catch (SQLTimeoutException e) {
//					throw new RepositoryRequestTimedOutException(
//							"Song request timed out");
//				} finally {
//					pst.close();
//				}
//			} catch (SQLException e) {
//				throw new RepositoryRequestFailedException(
//						"Failed to request song");
//			} finally {
//				conn.close();
//			}
//		} catch (SQLTimeoutException e) {
//			throw new RepositoryConnectionTimedOutException(
//					"Song repository connection timed out");
//		} catch (SQLException e) {
//			throw new RepositoryConnectionFailedException(
//					"Song repository connection failed");
//		}
//	}
//
//	@Override
//	public boolean addSong(String bookId, Song book) {
//
//		final String sql = 
//				"INSERT INTO songs (id, name, extra, lyrics, song_number, other_notes, page_num, book_id) " + 
//				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//
//		try {
//			Connection conn = driver.getConnection();
//			try {
//				PreparedStatement pst = conn.prepareStatement(sql);
//				try {
//					pst.setString(1, book.getId());
//					pst.setString(2, book.getName());
//					pst.setString(3, book.getExtra());
//					pst.setString(4, book.getLyrics());
//					pst.setInt(5, book.getSongNumber());
//					pst.setString(6, book.getOtherNotes());
//					pst.setInt(7, book.getPageNum());
//					pst.setString(8, bookId);
//
//					pst.execute();
//					return true;
//
//				} catch (SQLTimeoutException e) {
//					throw new RepositoryRequestTimedOutException(
//							"Songbook add request timed out.");
//				} finally {
//					pst.close();
//				}
//			} catch (SQLException e) {
//				if (e.getSQLState().equals("23505")) // POSTGRESQL error code
//														// for unique_violation
//					return false;
//				else
//					throw new RepositoryRequestFailedException(
//							"Failed to add songbook.");
//			} finally {
//				conn.close();
//			}
//		} catch (SQLTimeoutException e) {
//			throw new RepositoryConnectionTimedOutException(
//					"Songbook repository connection timed out.");
//		} catch (SQLException e) {
//			throw new RepositoryConnectionFailedException(
//					"Failed to establish connection to songbook repository.");
//		}
//
//	}
//
//	@Override
//	public boolean deleteSong(String bookId, String songId) {
//
//		final String sql = "DELETE FROM songs WHERE id = ? AND book_id = ?";
//
//		try {
//			Connection conn = driver.getConnection();
//			try {
//				PreparedStatement pst = conn.prepareStatement(sql);
//				try {
//					pst.setString(1, songId);
//					pst.setString(2, bookId);
//					int affectedRows = pst.executeUpdate();
//					return affectedRows > 0;
//				} catch (SQLTimeoutException e) {
//					throw new RepositoryRequestTimedOutException(
//							"Song delete request timed out.");
//				} finally {
//					pst.close();
//				}
//			} catch (SQLException e) {
//				throw new RepositoryRequestFailedException(
//						"Failed to delete song.");
//			} finally {
//				conn.close();
//			}
//		} catch (SQLTimeoutException e) {
//			throw new RepositoryConnectionTimedOutException(
//					"Song repository connection timed out.");
//		} catch (SQLException e) {
//			throw new RepositoryConnectionFailedException(
//					"Failed to establish connection to song repository.");
//		}
//
//	}

	@Override
	public Collection<ExtraVerseId> getExtraVerses(String bookId, String songId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtraVerseId getExtraVerse(String bookId, String songId,
			Integer extraVerseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtraVerseId addExtraVerse(String bookId, String songId,
			ExtraVerse verse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteExtraVerse(String bookId, String songId,
			Integer verseId) {
		// TODO Auto-generated method stub
		return false;
	}
}
