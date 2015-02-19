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
package fi.attemoisio.songbookapi.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.wordnik.swagger.annotations.ApiModel;

import fi.attemoisio.songbookapi.validation.Text;
import fi.attemoisio.songbookapi.validation.Title;

@XmlRootElement(name = "SongPost")
@ApiModel(description = "Song POST model")
@XmlType(name="")
public class SongPost {
	
	private String name;
	private String lyrics;
	private Integer songNumber;
	private String otherNotes;
	private Integer pageNumber;
	private String extra;
	
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
