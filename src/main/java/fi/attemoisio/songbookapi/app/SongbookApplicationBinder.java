/* 
 * The MIT License
 *
 * Copyright 2015 Atte Moisio.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
