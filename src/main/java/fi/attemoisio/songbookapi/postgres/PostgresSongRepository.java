package fi.attemoisio.songbookapi.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import fi.attemoisio.songbookapi.errorhandling.ApiError;
import fi.attemoisio.songbookapi.model.Song;
import fi.attemoisio.songbookapi.model.SongPost;
import fi.attemoisio.songbookapi.repository.SongRepository;
import fi.attemoisio.songbookapi.util.StringUtils;

public class PostgresSongRepository extends PostgresRepository implements
		SongRepository {

	@Inject
	public PostgresSongRepository(SQLDriverManager driver) {
		super(driver);
	}

	@Override
	public Collection<Song> getSongs(String bookId) {

		String sql = "SELECT id, name, extra, lyrics, song_number, other_notes, page_number, book_id"
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
						song.setPageNumber(rs.getInt("page_number"));
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
		}, ApiError.GET_SONGS_ERROR, ApiError.GET_SONGS_TIMEOUT);

	}

	@Override
	public Song getSong(String bookId, String songId) {

		String sql = "SELECT id, name, extra, lyrics, song_number, other_notes, page_number, book_id"
				+ " FROM songs WHERE id = ? AND book_id = ?";

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
						song.setPageNumber(rs.getInt("page_number"));
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
		}, ApiError.GET_SONG_ERROR, ApiError.GET_SONG_TIMEOUT);
	}

	@Override
	public Song addSong(String bookId, SongPost song) {

		final String sql = "INSERT INTO songs (id, name, extra, lyrics, song_number, other_notes, page_number, book_id)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		return handleConnection(conn -> {
			PreparedStatement pst = conn.prepareStatement(sql);
			try {

				pst.setString(2, song.getName());
				pst.setString(3, song.getExtra());
				pst.setString(4, song.getLyrics());
				pst.setInt(5, song.getSongNumber());
				pst.setString(6, song.getOtherNotes());
				pst.setInt(7, song.getPageNumber());
				pst.setString(8, bookId);

				String slug = StringUtils.toSlug(song.getName());
				int slugIndex = 1;
				
				pst.setString(1, slug);

				// This mess is for finding out the next valid slug for the song.
				// On each loop execution, a sequence number is grown and added
				// to the end of the slug until no unique violation happens.
				boolean inserted = false;
				while (!inserted) {
					String s = slug;
					if (slugIndex > 1)
						s += "-" + slugIndex;
					pst.setString(1, s);
					try {
						pst.executeUpdate();
						inserted = true;
						slug = s;
					} catch (SQLException sqle) {
						if (sqle.getSQLState().equals(ERROR_CODE_UNIQUE_VIOLATION)) {
							inserted = false; // on conflict, continue looping.
							slugIndex++;		
						} else {
							throw sqle; // pass unexpected exceptions to caller.
						}
					}

				}

				Song newSong = new Song();
				newSong.setId(slug);
				newSong.setExtra(song.getExtra());
				newSong.setLyrics(song.getLyrics());
				newSong.setName(song.getName());
				newSong.setOtherNotes(song.getOtherNotes());
				newSong.setPageNumber(song.getPageNumber());
				newSong.setSongNumber(song.getSongNumber());
				return newSong;
				
			} finally {
				pst.close();
			}

		}, ApiError.ADD_SONG_ERROR, ApiError.ADD_SONG_TIMEOUT);

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
			} finally {
				pst.close();
			}
		}, ApiError.DELETE_SONG_ERROR, ApiError.DELETE_SONG_TIMEOUT);

	}

	@Override
	public ApiError getRepositoryConnectionFailApiError() {
		return ApiError.SONG_REPOSITORY_ERROR;
	}

	@Override
	public ApiError getRepositoryConnectionTimeoutApiError() {
		return ApiError.SONG_REPOSITORY_TIMEOUT;
	}

	@Override
	public UpdateResult updateSong(final String bookId, final Song song) {
		
		final String sql = "UPDATE songs "
		+ " SET name = ?, extra = ?, lyrics = ?, song_number = ?, other_notes = ?, page_number = ? WHERE id = ? AND book_id = ?;"
		+ " INSERT INTO songs (id, name, extra, lyrics, song_number, other_notes, page_number, book_id)"
		+ " SELECT ?, ?, ?, ?, ?, ?, ?, ?"
		+ " WHERE NOT EXISTS (SELECT 1 FROM songs WHERE id = ? AND book_id = ?) RETURNING id";
		
		return handleConnection(conn -> {
			PreparedStatement pst = conn.prepareStatement(sql);
			try {
				
				// SET
				pst.setString(1, song.getName());
				pst.setString(2, song.getExtra());
				pst.setString(3, song.getLyrics());
				pst.setInt(4, song.getSongNumber());
				pst.setString(5, song.getOtherNotes());
				pst.setInt(6, song.getPageNumber());
				
				// WHERE
				pst.setString(7, song.getId());
				pst.setString(8, bookId);
				
				// SELECT
				pst.setString(9, song.getId());
				pst.setString(10, song.getName());
				pst.setString(11, song.getExtra());
				pst.setString(12, song.getLyrics());
				pst.setInt(13, song.getSongNumber());
				pst.setString(14, song.getOtherNotes());
				pst.setInt(15, song.getPageNumber());
				pst.setString(16, bookId);
		
				// WHERE
				pst.setString(17, song.getId());
				pst.setString(18, bookId);
			
				pst.execute();
				String insertedId = null;
				int updateCount = pst.getUpdateCount();
				
				if (pst.getMoreResults()) {
					ResultSet rs = pst.getResultSet();
					if (rs.next())
						insertedId = rs.getString("id");
				}

				return new UpdateResult(updateCount, insertedId);
	
			} finally {
				pst.close();
			}
	
		}, ApiError.UPDATE_SONG_ERROR, ApiError.UPDATE_SONG_TIMEOUT);
	}

}
