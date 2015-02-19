/* 
 * The MIT License
 *
 * Copyright 2015 Atte Moisio.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fi.attemoisio.songbookapi.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import fi.attemoisio.songbookapi.errorhandling.ApiError;
import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.model.SongbookPost;
import fi.attemoisio.songbookapi.repository.SongbookRepository;
import fi.attemoisio.songbookapi.util.StringUtils;

public class PostgresSongbookRepository extends PostgresRepository implements
		SongbookRepository {

	@Inject
	public PostgresSongbookRepository(SQLDriverManager driver) {
		super(driver);
	}

	@Override
	public Collection<Songbook> getSongbooks() {
		return handleConnection(conn -> {
			Statement pst = conn.createStatement();
			try {
				ResultSet rs = pst
						.executeQuery("SELECT id, title, release_year, description, other_notes from songbooks");
				try {

					ArrayList<Songbook> books = new ArrayList<Songbook>();

					while (rs.next()) {
						Songbook book = new Songbook();
						book.setId(rs.getString("id"));
						book.setTitle(rs.getString("title"));
						book.setReleaseYear(rs.getInt("release_year"));
						book.setDescription(rs.getString("description"));
						book.setOtherNotes(rs.getString("other_notes"));
						books.add(book);
					}

					return books;

				} finally {
					rs.close();
				}
			} finally {
				pst.close();
			}
		}, ApiError.GET_SONGBOOKS_ERROR, ApiError.GET_SONGBOOKS_TIMEOUT);

	}

	@Override
	public Songbook getSongbook(String id) {

		return handleConnection(conn -> {
			String sql = "SELECT id, title, release_year, description, other_notes from songbooks WHERE id = ?";
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
						book.setReleaseYear(rs.getInt("release_year"));
						book.setDescription(rs.getString("description"));
						book.setOtherNotes(rs.getString("other_notes"));
						return book;
					} else {
						return null;
					}
				} finally {
					rs.close();
				}
			} finally {
				pst.close();
			}
		}, ApiError.GET_SONGBOOK_ERROR, ApiError.GET_SONGBOOK_TIMEOUT);
	}

	@Override
	public boolean deleteSongbook(String id) {

		return handleConnection(conn -> {
			final String sql = "DELETE FROM songbooks WHERE songbooks.id = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			try {
				pst.setString(1, id);
				int affectedRows = pst.executeUpdate();
				return affectedRows > 0;
			} finally {
				pst.close();
			}
		}, ApiError.DELETE_SONGBOOK_ERROR, ApiError.DELETE_SONGBOOK_TIMEOUT);

	}

	@Override
	public ApiError getRepositoryConnectionFailApiError() {
		return ApiError.SONGBOOK_REPOSITORY_ERROR;
	}

	@Override
	public ApiError getRepositoryConnectionTimeoutApiError() {
		return ApiError.SONGBOOK_REPOSITORY_TIMEOUT;
	}

	@Override
	public Songbook postSongbook(SongbookPost book) {

		final String sql = "INSERT INTO songbooks (id, title, release_year, description, other_notes)"
				+ " VALUES (?, ?, ?, ?, ?)";

		return handleConnection(conn -> {
			PreparedStatement pst = conn.prepareStatement(sql);
			try {

				pst.setString(2, book.getTitle());
				pst.setInt(3, book.getReleaseYear());
				pst.setString(4, book.getDescription());
				pst.setString(5, book.getOtherNotes());

				String slug = StringUtils.toSlug(book.getTitle());
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

				Songbook newBook = new Songbook();
				newBook.setId(slug);
				newBook.setTitle(book.getTitle());
				newBook.setOtherNotes(book.getOtherNotes());
				newBook.setReleaseYear(book.getReleaseYear());
				newBook.setDescription(book.getDescription());
				return newBook;
				
			} finally {
				pst.close();
			}

		}, ApiError.ADD_SONGBOOK_ERROR, ApiError.ADD_SONGBOOK_TIMEOUT);

	}

	@Override
	public PutResult putSongbook(final Songbook book) {
		
		final String sql = "UPDATE songbooks "
		+ " SET title = ?, release_year = ?, description = ?, other_notes = ? WHERE id = ?;"
		+ " INSERT INTO songbooks (id, title, release_year, description, other_notes)"
		+ " SELECT ?, ?, ?, ?, ?"
		+ " WHERE NOT EXISTS (SELECT 1 FROM songbooks WHERE id = ?) RETURNING id";
		
		return handleConnection(conn -> {
			PreparedStatement pst = conn.prepareStatement(sql);
			try {
				
				// SET
				pst.setString(1, book.getTitle());
				pst.setInt(2, book.getReleaseYear());
				pst.setString(3, book.getDescription());
				pst.setString(4, book.getOtherNotes());
				
				// WHERE
				pst.setString(5, book.getId());
				
				// SELECT
				pst.setString(6, book.getId());
				pst.setString(7, book.getTitle());
				pst.setInt(8, book.getReleaseYear());
				pst.setString(9, book.getDescription());
				pst.setString(10, book.getOtherNotes());
				
				// WHERE
				pst.setString(11, book.getId());
			
				pst.execute();
				String insertedId = null;
				int updateCount = pst.getUpdateCount();
				
				if (pst.getMoreResults()) {
					ResultSet rs = pst.getResultSet();
					if (rs.next())
						insertedId = rs.getString("id");
				}

				return new PutResult(updateCount, insertedId);
	
			} finally {
				pst.close();
			}
	
		}, ApiError.UPDATE_SONGBOOK_ERROR, ApiError.UPDATE_SONGBOOK_TIMEOUT);
	}
	
}
