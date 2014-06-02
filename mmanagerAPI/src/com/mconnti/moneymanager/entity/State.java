package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="state")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class State implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;

	@Column(length = 100, nullable = false)
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "state_id")
	@ForeignKey(name = "FK_STATE_CITY")
	@XmlTransient
	@Transient
	private Set<City> cityList;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Country.class)
	@JoinColumn(name = "country_id")
	@ForeignKey(name = "FK_STATE_CONTRY")
	@JsonIgnore
	private Country country;

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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	public Set<City> getCityList() {
		return cityList;
	}

	public void setCityList(Set<City> cityList) {
		this.cityList = cityList;
	}
}
