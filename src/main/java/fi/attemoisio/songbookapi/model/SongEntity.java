package fi.attemoisio.songbookapi.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SongEntity {

	private Song song;
	private List<ExtraVerse> extraVerses = new ArrayList<ExtraVerse>();

	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}
	
	public Collection<ExtraVerse> getExtraVerses() {
		return extraVerses;
	}
	public void addExtraVerses(ExtraVerse verse) {
		this.extraVerses.add(verse);
	}
	
}
