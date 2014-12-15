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

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.ws.rs.DefaultValue;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wordnik.swagger.annotations.ApiModel;

import fi.attemoisio.songbookapi.validation.Slug;
import fi.attemoisio.songbookapi.validation.Text;
import fi.attemoisio.songbookapi.validation.Title;

@XmlRootElement(name = "Songbook")
@ApiModel(description = "Songbook model")
public class Songbook {
	
	private String id;
	private Integer releaseYear = null;
	private String title;

	private String description = null;
	private String otherNotes = null;
	
	@XmlElement(name = "id", nillable = true)
	@Slug()
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement(name = "release_year", nillable=true)
	@Min(1600)
	@Max(9999)
	public Integer getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}
	
	@XmlElement(name = "title")
	@DefaultValue("")
	@Title(message = "Invalid title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@XmlElement(name = "description", nillable=true)
	@Text
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlElement(name = "other_notes", nillable=true)
	@Text
	public String getOtherNotes() {
		return otherNotes;
	}
	public void setOtherNotes(String otherNotes) {
		this.otherNotes = otherNotes;
	}
	
}
