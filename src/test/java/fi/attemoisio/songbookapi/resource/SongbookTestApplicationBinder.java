package fi.attemoisio.songbookapi.resource;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import fi.attemoisio.songbookapi.repository.SongbookRepository;

public class SongbookTestApplicationBinder extends AbstractBinder {
	
    @Override
    protected void configure() {
        bind(MockSongbookRepository.class).to(SongbookRepository.class);
    }
    
}
