package fi.attemoisio.songbookapi.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@XmlRootElement(name = "Songbook")
@ApiModel(description = "Songbook model")
public class Songbook {
	
	private int id;
	private int releaseYear;
	private String title;

	private String description;
	
	@XmlElement(name = "id")
	@ApiModelProperty(position = 0, hidden = true)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@XmlElement(name = "releaseYear")
	@ApiModelProperty(position = 2, hidden = true)
	@Min(1600)
	@Max(9999)
	public int getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}
	
	@NotNull
	@Size(min = 1, max = 255)
	@XmlElement(name = "title")
	@ApiModelProperty(position = 1, hidden = true)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@XmlElement(name = "description")
	@Size(min = 1, max = 10000)
	@ApiModelProperty(position = 3, hidden = true)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
