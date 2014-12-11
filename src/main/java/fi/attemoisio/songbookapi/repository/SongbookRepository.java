package fi.attemoisio.songbookapi.repository;

import java.util.Collection;

import fi.attemoisio.songbookapi.model.Songbook;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryConnectionFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryRequestFailedException;
import fi.attemoisio.songbookapi.repository.exceptions.RepositoryTimeoutException;

public interface SongbookRepository {
	
	public Collection<Songbook> getSongbooks() throws RepositoryConnectionFailedException, RepositoryRequestFailedException, RepositoryTimeoutException;
	
	public Songbook getSongbook(String id);
	
	public Songbook addSongbook(Songbook book);
	
}
