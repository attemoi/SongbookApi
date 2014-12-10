package fi.attemoisio.songbookapi.repository;

import java.util.Collection;

import fi.attemoisio.songbookapi.model.Songbook;

public interface SongbookRepository {
	
	public Collection<Songbook> getSongbooks();
	public Songbook getSongbook(String id);
	
	public Songbook addSongbook(Songbook book);
	
}
