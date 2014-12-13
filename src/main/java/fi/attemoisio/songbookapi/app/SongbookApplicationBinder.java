package fi.attemoisio.songbookapi.app;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import fi.attemoisio.songbookapi.postgres.PostgresDriverManager;
import fi.attemoisio.songbookapi.postgres.PostgresExtraVerseRepository;
import fi.attemoisio.songbookapi.postgres.PostgresSongRepository;
import fi.attemoisio.songbookapi.postgres.PostgresSongbookRepository;
import fi.attemoisio.songbookapi.postgres.SQLDriverManager;
import fi.attemoisio.songbookapi.repository.ExtraVerseRepository;
import fi.attemoisio.songbookapi.repository.SongRepository;
import fi.attemoisio.songbookapi.repository.SongbookRepository;

public class SongbookApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {

		bind(PostgresDriverManager.class).to(SQLDriverManager.class);
		bind(PostgresSongbookRepository.class).to(SongbookRepository.class);
		bind(PostgresSongRepository.class).to(SongRepository.class);
		bind(PostgresExtraVerseRepository.class).to(ExtraVerseRepository.class);

	}

}
