package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Register implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Date date;

	private Date currentDate;

	private Date strDate;

	private BigDecimal amount;

	private Integer numberParcel;

	private Description description;

	private Description group;

	private Description superGroup;

	private Currency currency;
	
	private TypeClosure typeClosure;
	
	private TypeAccount typeAccount;

	private Boolean search;

	private Boolean closed;

	private CreditCard creditCard;

	private User user;
	
	private Parcel parcel;
	
	private Boolean multipleParcel;

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

	public Date getStrDate() {
		return strDate;
	}

	public void setStrDate(Date strDate) {
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

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
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

	public Parcel getParcel() {
		return parcel;
	}

	public void setParcel(Parcel parcel) {
		this.parcel = parcel;
	}

	public Boolean getMultipleParcel() {
		return multipleParcel;
	}

	public void setMultipleParcel(Boolean multipleParcel) {
		this.multipleParcel = multipleParcel;
	}
}
