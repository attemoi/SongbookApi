package fi.attemoisio.songbookapi.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.wordnik.swagger.annotations.ApiModel;

import fi.attemoisio.songbookapi.validation.Text;

@XmlRootElement(name = "ExtraVerse")
@ApiModel(description = "Extra verse model")
@XmlType(name = "")
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
