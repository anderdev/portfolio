package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanningGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Description description;
	
	private Planning planning;
	
	private User user;
	
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
		return plannigItemList;
	}

	public void setPlannigItemList(List<PlanningItem> plannigItemList) {
		this.plannigItemList = plannigItemList;
	}
}
