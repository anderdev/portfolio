package com.mconnti.selfmanager.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="country")
public class Country implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;

	@Column(length = 100, nullable = false)
	private String name;
	
	private String locale;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "country_id")
	@ForeignKey(name = "FK_COUNTRY_STATE")
	private Set<State> stateList;
	
	public Set<State> getStateList() {
		return stateList;
	}

	public void setStateList(Set<State> stateList) {
		this.stateList = stateList;
	}
	
	public Country() {
		super();
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
