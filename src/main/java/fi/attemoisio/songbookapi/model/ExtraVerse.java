package fi.attemoisio.songbookapi.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wordnik.swagger.annotations.ApiModel;

import fi.attemoisio.songbookapi.validation.constraints.Text;

@XmlRootElement(name = "ExtraVerse")
@ApiModel(description = "Extra verse model")
public class ExtraVerse extends ExtraVersePost {

	private Integer id;

	@XmlElement(name = "id", nillable = false)
	@Text
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
