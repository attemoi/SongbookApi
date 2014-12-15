package fi.attemoisio.songbookapi.model;

/*
 * ###################################################################=
 * Laulukirja API
 * %%
 * Copyright (C) 2014 Atte Moisio
 * %%
 * DO WHAT YOU WANT TO PUBLIC LICENSE
 * 
 *  Copyright (C) 2014 Atte Moisio
 * 
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 * 
 *  DO WHAT YOU WANT TO PUBLIC LICENSE
 *  TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 * 
 *  0. You just DO WHAT YOU WANT TO.
 * ###################################################################-
 */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wordnik.swagger.annotations.ApiModel;

import fi.attemoisio.songbookapi.validation.Slug;
import fi.attemoisio.songbookapi.validation.Text;
import fi.attemoisio.songbookapi.validation.Title;

@XmlRootElement(name = "Song")
@ApiModel(description = "Song model")
public class Song {
	
	private String id;
	private String name;
	private String lyrics;
	private Integer songNumber;
	private String otherNotes;
	private Integer pageNumber;
	private String extra;
	
	@XmlElement(name = "id", nillable = true)
	@Slug()
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement(name = "name", nillable = false)
	@Title
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "lyrics", nillable = false)
	@Text
	public String getLyrics() {
		return lyrics;
	}
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}
	
	@XmlElement(name = "song_number")
	public Integer getSongNumber() {
		return songNumber;
	}
	public void setSongNumber(Integer songNumber) {
		this.songNumber = songNumber;
	}
	
	@XmlElement(name = "other_notes", nillable = true)
	@Text
	public String getOtherNotes() {
		return otherNotes;
	}
	public void setOtherNotes(String otherNotes) {
		this.otherNotes = otherNotes;
	}
	
	@XmlElement(name = "page_number")
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNum) {
		this.pageNumber = pageNum;
	}
	
	@XmlElement(name = "extra", nillable = true)
	@Text
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	
	
}
