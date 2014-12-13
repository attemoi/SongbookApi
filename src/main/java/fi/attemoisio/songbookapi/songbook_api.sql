CREATE DOMAIN dom_description TEXT CHECK (
    LENGTH(VALUE) > 0 AND
    LENGTH(VALUE) < 300
);

CREATE DOMAIN dom_song_extra TEXT CHECK (
    LENGTH(VALUE) > 0 AND
    LENGTH(VALUE) < 50
);

CREATE DOMAIN dom_song_name TEXT CHECK (
    LENGTH(VALUE) > 0 AND
    LENGTH(VALUE) < 50
);

CREATE DOMAIN dom_other_notes TEXT CHECK (
    LENGTH(VALUE) > 0 AND
    LENGTH(VALUE) < 300
);

CREATE DOMAIN dom_lyrics TEXT CHECK (
    LENGTH(VALUE) > 0 AND
    LENGTH(VALUE) < 5000
);

CREATE DOMAIN dom_verse_lyrics TEXT CHECK (
    LENGTH(VALUE) > 0 AND
    LENGTH(VALUE) < 500
);

CREATE DOMAIN dom_title TEXT CHECK (
    LENGTH(VALUE) > 0 AND
    LENGTH(VALUE) < 300
);

CREATE DOMAIN dom_slug TEXT CHECK (
    LENGTH(VALUE) > 0 AND
    LENGTH(VALUE) < 50 AND
    VALUE ~ '^[a-z0-9-]+$'
);

CREATE TABLE songbooks (
  id              dom_slug     PRIMARY KEY,
  title           dom_title    NOT NULL,
  releaseYear     INTEGER,
  description     dom_description,
  other_notes     dom_other_notes
);

CREATE TABLE songs (
	id            dom_slug,		
	name          dom_song_name	NOT NULL,
	extra         dom_song_extra,
	lyrics        dom_lyrics,
	song_number   INTEGER,
	other_notes   dom_other_notes,
	page_num      INTEGER,
	book_id       dom_slug,
	PRIMARY KEY   (id, book_id),
	CONSTRAINT    fk_song_book   FOREIGN KEY (book_id) REFERENCES songbooks(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE extra_verses (
	id            SERIAL,	    
	lyrics        dom_verse_lyrics NOT NULL,
	book_id	      dom_slug,	    
	song_id       dom_slug,         
    PRIMARY KEY   (id, book_id, song_id),
	CONSTRAINT    fk_verse_song     FOREIGN KEY (song_id, book_id) REFERENCES songs(id, book_id) ON DELETE CASCADE ON UPDATE CASCADE
);