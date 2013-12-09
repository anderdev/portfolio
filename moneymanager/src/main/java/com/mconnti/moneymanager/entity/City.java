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

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="city")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class City implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;

	@Column(length = 100, nullable = false)
	private String name;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = State.class)
	@JoinColumn(name = "state_id")
	@ForeignKey(name = "FK_CITY_STATE")
	private State state;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "city_id")
	@ForeignKey(name = "FK_CITY_USER")
	@XmlTransient
	@Transient
	private Set<User> userList;

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

	public State getState() {
		return state;
	}

	public void setState(State st) {
		this.state = st;
	}

	public Set<User> getUserList() {
		return userList;
	}

	public void setUserList(Set<User> userList) {
		this.userList = userList;
	}

}
