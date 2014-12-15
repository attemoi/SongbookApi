package fi.attemoisio.songbookapi.resource.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import fi.attemoisio.songbookapi.errorhandling.ApiError;
import fi.attemoisio.songbookapi.exceptions.ApiException;
import fi.attemoisio.songbookapi.model.ExtraVerse;
import fi.attemoisio.songbookapi.model.ExtraVersePost;
import fi.attemoisio.songbookapi.repository.ExtraVerseRepository;

public class MockExtraVerseRepository implements ExtraVerseRepository {
	
	Map<String, HashMap<String, List<ExtraVerse>>> bookMap;
	
	private final AtomicInteger idSequence = new AtomicInteger(5);
	
	public MockExtraVerseRepository() {

			bookMap = new HashMap<String, HashMap<String, List<ExtraVerse>>>();
			
			for (int j = 0; j < 5; j++){
				String bookId = "book" + j;
				HashMap<String, List<ExtraVerse>> songMap = new HashMap<String, List<ExtraVerse>>();
				for (int i = 0; i < 5; i++) {
					String songId = "song" + i;
					
					List<ExtraVerse> verses = new ArrayList<ExtraVerse>();
					for (int k = 0; k < 5; k++) {
						ExtraVerse verse = new ExtraVerse();
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
	public Collection<ExtraVerse> getExtraVerses(String bookId, String songId) {
		Collection<ExtraVerse> verses = bookMap.get(bookId).get(songId);
		return verses;
	}

	@Override
	public ExtraVerse getExtraVerse(String bookId, String songId, Integer verseId) {
		Collection<ExtraVerse> verses = bookMap.get(bookId).get(songId);
		for (ExtraVerse verse : verses) {
			if (verse.getId().equals(verseId))
				return verse;
		}
		return null;
	}

	@Override
	public ExtraVerse postExtraVerse(String bookId, String songId, ExtraVersePost verse) {
		Collection<ExtraVerse> verses = bookMap.get(bookId).get(songId);
		ExtraVerse newVerse = new ExtraVerse();
		newVerse.setId(idSequence.getAndIncrement());
		newVerse.setLyrics(verse.getLyrics());
		verses.add(newVerse);
		return newVerse;
	}

	@Override
	public boolean deleteExtraVerse(String bookId, String songId, Integer verseId) {
		Collection<ExtraVerse> verses = bookMap.get(bookId).get(songId);
		
		for (ExtraVerse verse : verses) {
			if (verse.getId().equals(verseId)) {
				verses.remove(verse);
				return true;
			}
		}
		return false;
	}

	@Override
	public void patchExtraVerse(String bookId, String songId,
			ExtraVerse verse) {
		
		ExtraVerse existingVerse = getExtraVerse(bookId, songId, verse.getId());
		
		if (existingVerse == null) {
			throw new ApiException(ApiError.UPDATE_VERSE_NOT_FOUND);
		}
		
		existingVerse.setLyrics(verse.getLyrics());

	}

}
