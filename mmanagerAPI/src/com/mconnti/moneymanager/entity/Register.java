package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

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
@Table(name = "register")
public class Register implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;

	private Calendar date;

	private Calendar currentDate;

	@Transient
	private Calendar strDate;

	@Column(length = 13, precision = 13, scale = 2)
	private BigDecimal amount;

	private Integer numberParcel;

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
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = TypeAccount.class)
	@JoinColumn(name = "typeaccount_id")
	@ForeignKey(name = "FK_DEBIT_TYPEACCOUNT")
	private TypeAccount typeAccount;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = TypeClosure.class)
	@JoinColumn(name = "typeclosure_id")
	@ForeignKey(name = "FK_DEBIT_TYPECLOSURE")
	private TypeClosure typeClosure;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Parcel.class)
	@JoinColumn(name = "parcel_id")
	@ForeignKey(name = "FK_DEBIT_PARCEL")
	private Parcel parcel;
	
	@Transient
	private Boolean search;

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

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Calendar getStrDate() {
		return strDate;
	}

	public void setStrDate(Calendar strDate) {
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
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

	public Integer getNumberParcel() {
		return numberParcel;
	}

	public void setNumberParcel(Integer numberParcel) {
		this.numberParcel = numberParcel;
	}

	public Calendar getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Calendar currentDate) {
		this.currentDate = currentDate;
	}

	public TypeAccount getTypeAccount() {
		return typeAccount;
	}

	public void setTypeAccount(TypeAccount typeAccount) {
		this.typeAccount = typeAccount;
	}

	public Boolean getSearch() {
		return search;
	}

	public void setSearch(Boolean search) {
		this.search = search;
	}
}
