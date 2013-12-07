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
@Table(name = "debit")
public class Debit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;

	private Date date;

	@Transient
	private String strDate;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Description.class)
	@JoinColumn(name = "description_id")
	@ForeignKey(name = "FK_DEBIT_DESCRIPTION")
	private Description description;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Description.class)
	@JoinColumn(name = "group_id")
	@ForeignKey(name = "FK_DEBIT_GROUP")
	private Description group;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Description.class)
	@JoinColumn(name = "supergroup_id")
	@ForeignKey(name = "FK_DEBIT_SGROUP")
	private Description superGroup;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Currency.class)
	@JoinColumn(name = "currency_id")
	@ForeignKey(name = "FK_DEBIT_CURRENCY")
	private Currency currency;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = TypeClosure.class)
	@JoinColumn(name = "typeclosure_id")
	@ForeignKey(name = "FK_DEBIT_TYPECLOSURE")
	private TypeClosure typeClosure;

	@Column(length = 13, precision = 13, scale = 2)
	private Double amount;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Parcel.class)
	@JoinColumn(name = "parcel_id")
	@ForeignKey(name = "FK_DEBIT_PARCEL")
	private Parcel parcel;

	private Boolean closed;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = CreditCard.class)
	@JoinColumn(name = "creditcard_id")
	@ForeignKey(name = "FK_DEBIT_CREDIT_CARD")
	private CreditCard creditCard;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = User.class)
	@JoinColumn(name = "user_id")
	@ForeignKey(name = "FK_DEBIT_USER")
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

	public String getStrDate() {
		return strDate;
	}

	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public Description getGroup() {
		return group;
	}

	public void setGroup(Description group) {
		this.group = group;
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

	public TypeClosure getTypeClosure() {
		return typeClosure;
	}

	public void setTypeClosure(TypeClosure type) {
		this.typeClosure = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Parcel getParcel() {
		return parcel;
	}

	public void setParcel(Parcel parcel) {
		this.parcel = parcel;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
