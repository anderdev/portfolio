package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanningGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Description description;
	
	private TypeAccount typeAccount;
	
	private Planning planning;

	private User user;
	
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

	public TypeAccount getTypeAccount() {
		return typeAccount;
	}

	public void setTypeAccount(TypeAccount typeAccount) {
		this.typeAccount = typeAccount;
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
