package fi.attemoisio.songbookapi.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wordnik.swagger.annotations.ApiModel;

import fi.attemoisio.songbookapi.validation.constraints.Text;

@XmlRootElement(name = "ExtraVerse")
@ApiModel(description = "Extra verse model")
public class ExtraVerse {
	
	private String lyrics;

	@XmlElement(name = "lyrics", nillable = false)
	@Text
	public String getLyrics() {
		return lyrics;
	}
	
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}	
	
}
