package fi.attemoisio.songbookapi.resource.app;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import fi.attemoisio.songbookapi.repository.ExtraVerseRepository;
import fi.attemoisio.songbookapi.repository.SongRepository;
import fi.attemoisio.songbookapi.repository.SongbookRepository;
import fi.attemoisio.songbookapi.resource.mock.MockExtraVerseRepository;
import fi.attemoisio.songbookapi.resource.mock.MockSongRepository;
import fi.attemoisio.songbookapi.resource.mock.MockSongbookRepository;

public class SongbookTestApplicationBinder extends AbstractBinder {
	
    @Override
    protected void configure() {
        bind(MockSongbookRepository.class).to(SongbookRepository.class);
        bind(MockSongRepository.class).to(SongRepository.class);
        bind(MockExtraVerseRepository.class).to(ExtraVerseRepository.class);
    }
    
}
