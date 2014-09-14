package fi.attemoisio.songbookapi.model;

import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Songbook")
public class Songbook {
	
	private int id;
	private int ownerId;
	private int releaseYear;
	private String title;

	private String description;
	
	@XmlElement(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@XmlElement(name = "ownerId")
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	@XmlElement(name = "releaseYear")
	public int getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseDate(int releaseYear) {
		this.releaseYear = releaseYear;
	}
	
	@XmlElement(name = "title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
