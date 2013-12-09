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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "credit")
public class Credit implements Serializable {

	private static final long serialVersionUID = 2013860239536512589L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;

	private Date date;

	@Transient
	private String strDate;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Description.class)
	@JoinColumn(name = "description_id")
	@ForeignKey(name = "FK_CREDIT_DESCRIPTION")
	private Description description;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Description.class)
	@JoinColumn(name = "supergroup_id")
	@ForeignKey(name = "FK_CREDIT_TYPEACCOUNT")
	private Description superGroup;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Currency.class)
	@JoinColumn(name = "currency_id")
	@ForeignKey(name = "FK_CREDIT_CURRENCY")
	private Currency currency;

	@Column(length = 13, precision = 13, scale = 2)
	private Double amount;

	private Boolean closed;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = User.class)
	@JoinColumn(name = "user_id")
	@ForeignKey(name = "FK_CREDIT_USER")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public Description getSuperGroup() {
		return superGroup;
	}

	public void setSuperGroup(Description superGroup) {
		this.superGroup = superGroup;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStrDate() {
		return strDate;
	}

	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

}
