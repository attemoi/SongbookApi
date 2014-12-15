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
import javax.xml.bind.annotation.XmlType;

import com.wordnik.swagger.annotations.ApiModel;

import fi.attemoisio.songbookapi.validation.Slug;

@XmlRootElement(name = "Songbook")
@ApiModel(description = "Songbook model")
@XmlType(name = "")
public class Songbook extends SongbookPost {

	private String id;

	@XmlElement(name = "id", nillable = true)
	@Slug()
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
