package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.util.GregorianCalendar;
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
@Table(name="planning")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Planning implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;
	
	@Column(nullable = false, insertable = true, updatable = true)
	private String description;
	
	private GregorianCalendar date;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "planning_id")
	@ForeignKey(name = "FK_PLANNING_PLITEM")
	@XmlTransient
	@Transient
	private Set<PlanningItem> plannigItemList;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = User.class)
	@JoinColumn(name = "user_id")
	@ForeignKey(name = "FK_PLANNING_USER")
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public GregorianCalendar getDate() {
		return date;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

	public Set<PlanningItem> getPlannigItemList() {
		return plannigItemList;
	}

	public void setPlannigItemList(Set<PlanningItem> plannigItemList) {
		this.plannigItemList = plannigItemList;
	}
}
