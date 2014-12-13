package fi.attemoisio.songbookapi.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.attemoisio.songbookapi.model.Song;
import fi.attemoisio.songbookapi.repository.SongRepository;

public class MockSongRepository implements SongRepository {

	Map<String, List<Song>> songMap;
	
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
				song.setPageNum(i + 1);
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
	public boolean addSong(String bookId, Song song) {
		Collection<Song> songs = songMap.get(bookId);
		if (songs.stream().anyMatch(b -> b.getId().equals(song.getId()))) {
		    return false;
		} else {
		    songs.add(song);
		    return true;
		}
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

}
