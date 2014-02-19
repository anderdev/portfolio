package com.mconnti.moneymanager.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TypeAccount implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String description;
	
	private String locale;
	
	private Boolean showType;
	
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

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Boolean getShowType() {
		return showType;
	}

	public void setShowType(Boolean showType) {
		this.showType = showType;
	}

}
