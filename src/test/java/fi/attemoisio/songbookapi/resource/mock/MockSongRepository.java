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
	public Song addSong(String bookId, SongPost song) {
		
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
	public UpdateResult updateSong(String bookId, Song song) {
		
		Song existingSong = getSong(bookId, song.getId());
			
		if (existingSong == null) {
			Song insertedSong = addSong(bookId, song);
			return new UpdateResult(0, insertedSong.getId());
		} else {
			existingSong.setName(song.getName());
			existingSong.setLyrics(song.getLyrics());
			existingSong.setOtherNotes(song.getOtherNotes());
			existingSong.setPageNumber(song.getPageNumber());
			existingSong.setSongNumber(song.getSongNumber());
			return new UpdateResult(1, null);
		}

	}

}
