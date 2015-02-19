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
package fi.attemoisio.songbookapi.resource.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import fi.attemoisio.songbookapi.model.Song;
import fi.attemoisio.songbookapi.model.SongPost;
import fi.attemoisio.songbookapi.repository.SongRepository;

public class MockSongRepository implements SongRepository {

	Map<String, List<Song>> songMap;
	
	AtomicInteger sequenceNum = new AtomicInteger(10);
	
	public MockSongRepository() {

		songMap = new HashMap<String, List<Song>>();
		for (int j = 0; j < 5; j++){
			String bookId = "book" + j;
			List<Song> songs = new ArrayList<Song>();
			for (int i = 0; i < 5; i++) {
				Song song = new Song();
				song.setExtra("extra lorem");
				song.setId("song" + i);
				song.setName("Song" + i);
				song.setLyrics("Lyrics lorem\n ipsum dolor\n sit amet");
				song.setOtherNotes("other note lorem");
				song.setPageNumber(i + 1);
				song.setSongNumber(i+1);
				songs.add(song);
				
			}
			songMap.put(bookId, songs);
		}
		
		
	}
	
	@Override
	public Collection<Song> getSongs(String bookId) {
		Collection<Song> songs = songMap.get(bookId);
		return songs;
	}

	@Override
	public Song getSong(String bookId, String songId) {
		Collection<Song> songs = songMap.get(bookId);
		for (Song song : songs) {
			if (song.getId().equals(songId))
				return song;
		}
		return null;
	}

	

	@Override
	public boolean deleteSong(String bookId, String songId) {
		Collection<Song> songs = songMap.get(bookId);
		
		for (Song song : songs) {
			if (song.getId().equals(songId)) {
				songs.remove(song);
				return true;
			}
		}
		return false;
	}

	@Override
	public Song postSong(String bookId, SongPost song) {
		
		Collection<Song> songs = songMap.get(bookId);
		
		Song newSong = new Song();
		newSong.setId("song" + sequenceNum);
		newSong.setName(song.getName());
		newSong.setLyrics(song.getLyrics());
		newSong.setOtherNotes(song.getOtherNotes());
		newSong.setPageNumber(song.getPageNumber());
		newSong.setSongNumber(song.getSongNumber());
	    songs.add(newSong);
	    
	    return newSong;
	}

	@Override
	public PutResult putSong(String bookId, Song song) {

		Song existingSong = getSong(bookId, song.getId());
			
		if (existingSong == null) {
			return new PutResult(0, song.getId());
		} else {
			existingSong.setName(song.getName());
			existingSong.setLyrics(song.getLyrics());
			existingSong.setOtherNotes(song.getOtherNotes());
			existingSong.setPageNumber(song.getPageNumber());
			existingSong.setSongNumber(song.getSongNumber());
			return new PutResult(1, null);
		}

	}

}
