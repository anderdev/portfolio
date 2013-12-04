package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "user")
@NamedQueries({@NamedQuery(name = "user.findByEmail", query = "SELECT us FROM User us WHERE us.email = :email")})
@XmlRootElement
public class User implements Serializable{

	private static final long serialVersionUID = -5645425703632609531L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;
	
	@Column(name="master_id")
	private Integer masterId;
	
	private String name;
	
	private String email;
	
	@Column(name="born")
	private Date born;
	
	@Column(name="register")
	private Date register;
	
	private String language;
	
	private Boolean administrator;
	
	@Column(name="palavra_secreta")
	private String secretPhrase;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = City.class)
	@JoinColumn(name = "city_id")
	@ForeignKey(name = "FK_USER_CITY")
	private City city;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Config.class)
	@JoinColumn(name = "parameter_id")
	@ForeignKey(name = "FK_USER_PARAMETER")
	private Config config;
	
	private String username;
	
	private String password;
	
	private Boolean excluded;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMasterId() {
		return masterId;
	}

	public void setMasterId(Integer masterId) {
		this.masterId = masterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBorn() {
		return born;
	}

	public void setBorn(Date born) {
		this.born = born;
	}

	public Date getRegister() {
		return register;
	}

	public void setRegister(Date register) {
		this.register = register;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Boolean getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Boolean administrator) {
		this.administrator = administrator;
	}

	public String getSecretPhrase() {
		return secretPhrase;
	}

	public void setSecretPhrase(String secretPhrase) {
		this.secretPhrase = secretPhrase;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getExcluded() {
		return excluded;
	}

	public void setExcluded(Boolean excluded) {
		this.excluded = excluded;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}
	
}
