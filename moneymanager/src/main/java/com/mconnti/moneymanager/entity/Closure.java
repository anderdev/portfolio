package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name="closure")
public class Closure implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;

	private Date date;
	
	private Date startDate;
	
	private Date endDate;

	private String type;

	private String currency;

	@Column(length = 13, precision = 13, scale = 2)
	private Double totalCredit;
	
	@Column(length = 13, precision = 13, scale = 2)
	private Double totalDebit;
	
	@Column(length = 13, precision = 13, scale = 2)
	private Double totalGeneral;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = User.class)
	@JoinColumn(name = "user_id")
	@ForeignKey(name = "FK_CLOSURE_USER")
	private User user;
	
	@Transient
	private Collection<String> creditsAlreadyClosed;

	@Transient
	private Collection<String> debitsAlreadyClosed;

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

	public Collection<String> getCreditsAlreadyClosed() {
		return creditsAlreadyClosed;
	}

	public void setCreditsAlreadyClosed(Collection<String> creditsAlreadyClosed) {
		this.creditsAlreadyClosed = creditsAlreadyClosed;
	}

	public Collection<String> getDebitsAlreadyClosed() {
		return debitsAlreadyClosed;
	}

	public void setDebitsAlreadyClosed(Collection<String> debitsAlreadyClosed) {
		this.debitsAlreadyClosed = debitsAlreadyClosed;
	}
}
