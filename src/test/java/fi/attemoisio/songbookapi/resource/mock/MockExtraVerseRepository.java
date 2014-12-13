package fi.attemoisio.songbookapi.resource.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import fi.attemoisio.songbookapi.model.ExtraVerse;
import fi.attemoisio.songbookapi.model.ExtraVerseId;
import fi.attemoisio.songbookapi.repository.ExtraVerseRepository;

public class MockExtraVerseRepository implements ExtraVerseRepository {
	
	Map<String, HashMap<String, List<ExtraVerseId>>> bookMap;
	
	private final AtomicInteger idSequence = new AtomicInteger(5);
	
	public MockExtraVerseRepository() {

			bookMap = new HashMap<String, HashMap<String, List<ExtraVerseId>>>();
			
			for (int j = 0; j < 5; j++){
				String bookId = "book" + j;
				HashMap<String, List<ExtraVerseId>> songMap = new HashMap<String, List<ExtraVerseId>>();
				for (int i = 0; i < 5; i++) {
					String songId = "song" + i;
					
					List<ExtraVerseId> verses = new ArrayList<ExtraVerseId>();
					for (int k = 0; k < 5; k++) {
						ExtraVerseId verse = new ExtraVerseId();
						verse.setId(i);
						verse.setLyrics("lorem ipsum " + i);
						verses.add(verse);
					}
					songMap.put(songId, verses);
				}
				bookMap.put(bookId, songMap);
			}

	}
	
	@Override
	public Collection<ExtraVerseId> getExtraVerses(String bookId, String songId) {
		Collection<ExtraVerseId> verses = bookMap.get(bookId).get(songId);
		return verses;
	}

	@Override
	public ExtraVerseId getExtraVerse(String bookId, String songId, Integer verseId) {
		Collection<ExtraVerseId> verses = bookMap.get(bookId).get(songId);
		for (ExtraVerseId verse : verses) {
			if (verse.getId().equals(verseId))
				return verse;
		}
		return null;
	}

	@Override
	public ExtraVerseId addExtraVerse(String bookId, String songId, ExtraVerse verse) {
		Collection<ExtraVerseId> verses = bookMap.get(bookId).get(songId);
		ExtraVerseId newVerse = new ExtraVerseId();
		newVerse.setId(idSequence.getAndIncrement());
		newVerse.setLyrics(verse.getLyrics());
		verses.add(newVerse);
		return newVerse;
	}

	@Override
	public boolean deleteExtraVerse(String bookId, String songId, Integer verseId) {
		Collection<ExtraVerseId> verses = bookMap.get(bookId).get(songId);
		
		for (ExtraVerseId verse : verses) {
			if (verse.getId().equals(verseId)) {
				verses.remove(verse);
				return true;
			}
		}
		return false;
	}

}
