package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="planningitem")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanningItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;
	
	@Column(length = 13, precision = 13, scale = 2)
	private BigDecimal amount;
	
	private Integer year;
	
	private Integer month;
	
	private Integer day;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = PlanningGroup.class)
	@JoinColumn(name = "planninggroup_id")
	@ForeignKey(name = "FK_PLITEM_PLGROUP")
	@JsonIgnore
	private PlanningGroup planningGroup;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = User.class)
	@JoinColumn(name = "user_id")
	@ForeignKey(name = "FK_PLITEM_USER")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public PlanningGroup getPlanningGroup() {
		return planningGroup;
	}

	public void setPlanningGroup(PlanningGroup planningGroup) {
		this.planningGroup = planningGroup;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}
}
