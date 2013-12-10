package com.mconnti.moneymanager.entity;

import java.io.Serializable;
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

	private String type;

	private String currency;

	private Double totalCredit;
	
	private Double totalDebit;
	
	private Double totalGeneral;

	private User user;
	
	private Collection<Credit> creditsAlreadyClosed;

	private Collection<Debit> debitsAlreadyClosed;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(Double totalCredit) {
		this.totalCredit = totalCredit;
	}

	public Double getTotalDebit() {
		return totalDebit;
	}

	public void setTotalDebit(Double totalDebit) {
		this.totalDebit = totalDebit;
	}

	public Double getTotalGeneral() {
		return totalGeneral;
	}

	public void setTotalGeneral(Double totalGeneral) {
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

	public Collection<Credit> getCreditsAlreadyClosed() {
		return creditsAlreadyClosed;
	}

	public void setCreditsAlreadyClosed(Collection<Credit> creditsAlreadyClosed) {
		this.creditsAlreadyClosed = creditsAlreadyClosed;
	}

	public Collection<Debit> getDebitsAlreadyClosed() {
		return debitsAlreadyClosed;
	}

	public void setDebitsAlreadyClosed(Collection<Debit> debitsAlreadyClosed) {
		this.debitsAlreadyClosed = debitsAlreadyClosed;
	}
}
