package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Publicity implements Serializable {

	private static final long serialVersionUID = -3160404156422105911L;
	
	private Long id;
	
	private String description;
	
	private Clob text;
	
	private String type;
	
	private Boolean active;
	
	private String language;
	
	private String language_user;
	
	private Blob image;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Clob getText() {
		return text;
	}

	public void setText(Clob text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguage_user() {
		return language_user;
	}

	public void setLanguage_user(String language_user) {
		this.language_user = language_user;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

}
