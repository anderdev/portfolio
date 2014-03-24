package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Planning implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String description;
	
	private GregorianCalendar date;
	
	private Set<PlanningItem> plannigItemList;

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
