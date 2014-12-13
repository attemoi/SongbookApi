package fi.attemoisio.songbookapi.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SongEntity {

	private Song song;
	private List<ExtraVerseId> extraVerses = new ArrayList<ExtraVerseId>();

	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}
	
	public Collection<ExtraVerseId> getExtraVerses() {
		return extraVerses;
	}
	public void addExtraVerses(ExtraVerseId verse) {
		this.extraVerses.add(verse);
	}
	
}
