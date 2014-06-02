package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="planninggroup")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanningGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Description.class)
	@JoinColumn(name = "description_id")
	@ForeignKey(name = "FK_PLGROUP_DESC")
	private Description description;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Planning.class)
	@JoinColumn(name = "planning_id")
	@ForeignKey(name = "FK_PLGROUP_PLANNING")
	@JsonIgnore
	private Planning planning;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = User.class)
	@JoinColumn(name = "user_id")
	@ForeignKey(name = "FK_PLGROUP_USER")
	private User user;
	
	@XmlTransient
	@Transient
	private Map<Long,PlanningItem> plannigItemMap;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Planning getPlanning() {
		return planning;
	}

	public void setPlanning(Planning planning) {
		this.planning = planning;
	}

	public Map<Long, PlanningItem> getPlannigItemMap() {
		return plannigItemMap;
	}

	public void setPlannigItemMap(Map<Long, PlanningItem> plannigItemMap) {
		this.plannigItemMap = plannigItemMap;
	}
}
