package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Country implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String name;
	
	private String locale;
	
	@XmlTransient
	@Transient
	private Set<State> stateList;
	
	public Set<State> getStateList() {
		return stateList;
	}

	public void setStateList(Set<State> stateList) {
		this.stateList = stateList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

}
