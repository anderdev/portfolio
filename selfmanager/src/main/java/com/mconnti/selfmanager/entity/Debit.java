package com.mconnti.selfmanager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "debit")
public class Debit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;

	@NotNull
	private Date date;

	@NotNull
	private String description;

	@NotNull
	private String group;

	private String superGroup;

	@NotNull
	private String currency;

	private String type;

	@NotNull
	@Column(length = 13, precision = 13, scale = 2)
	private Double amout;

	@Column(name = "parcel_id")
	private Integer parcelId;

	private Boolean closed;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = CreditCard.class)
	@JoinColumn(name = "creditcard_id")
	@ForeignKey(name = "FK_DEBIT_CREDIT_CARD")
	private CreditCard creditCard;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = User.class)
	@JoinColumn(name = "user_id")
	@ForeignKey(name = "FK_DEBIT_USER")
	private User user;

	public Integer getParcelId() {
		return parcelId;
	}

	public void setParcelId(Integer parcelId) {
		this.parcelId = parcelId;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public String getSuperGroup() {
		return superGroup;
	}

	public void setSuperGroup(String superGroup) {
		this.superGroup = superGroup;
	}
}
