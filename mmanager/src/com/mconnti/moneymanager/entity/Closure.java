package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Closure implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Date date;
	
	private Date startDate;
	
	private Date endDate;

	private BigDecimal totalCredit;
	
	private BigDecimal totalDebit;
	
	private BigDecimal totalGeneral;
	
	private TypeClosure typeClosure;
	
	private Currency currency;

	private User user;
	
	private Collection<Register> debitsAlreadyClosed;
	
	private Collection<Register> creditsAlreadyClosed;
	
	private Boolean search;

	public Closure() {
		super();
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

	public BigDecimal getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(BigDecimal totalCredit) {
		this.totalCredit = totalCredit;
	}

	public BigDecimal getTotalDebit() {
		return totalDebit;
	}

	public void setTotalDebit(BigDecimal totalDebit) {
		this.totalDebit = totalDebit;
	}

	public BigDecimal getTotalGeneral() {
		return totalGeneral;
	}

	public void setTotalGeneral(BigDecimal totalGeneral) {
		this.totalGeneral = totalGeneral;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Collection<Register> getDebitsAlreadyClosed() {
		return debitsAlreadyClosed;
	}

	public void setDebitsAlreadyClosed(Collection<Register> debitsAlreadyClosed) {
		this.debitsAlreadyClosed = debitsAlreadyClosed;
	}

	public TypeClosure getTypeClosure() {
		return typeClosure;
	}

	public void setTypeClosure(TypeClosure typeClosure) {
		this.typeClosure = typeClosure;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Collection<Register> getCreditsAlreadyClosed() {
		return creditsAlreadyClosed;
	}

	public void setCreditsAlreadyClosed(Collection<Register> creditsAlreadyClosed) {
		this.creditsAlreadyClosed = creditsAlreadyClosed;
	}

	public Boolean getSearch() {
		return search;
	}

	public void setSearch(Boolean search) {
		this.search = search;
	}
}
