package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "planninggroup_id")
	@ForeignKey(name = "FK_PLGROUP_PLANITEM")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<PlanningItem> plannigItemList;

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

	public List<PlanningItem> getPlannigItemList() {
		if(plannigItemList == null){
			plannigItemList = new ArrayList<>();
		}
		return plannigItemList;
	}

	public void setPlannigItemList(List<PlanningItem> plannigItemList) {
		this.plannigItemList = plannigItemList;
	}
}
