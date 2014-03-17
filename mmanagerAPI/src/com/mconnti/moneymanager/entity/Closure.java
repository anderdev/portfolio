package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = TypeClosure.class)
	@JoinColumn(name = "typeclosure_id")
	@ForeignKey(name = "FK_CLOSURE_TYPECLOSURE")
	private TypeClosure typeClosure;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Currency.class)
	@JoinColumn(name = "currency_id")
	@ForeignKey(name = "FK_CLOSURE_CURRENCY")
	private Currency currency;

	@Column(length = 13, precision = 13, scale = 2)
	private BigDecimal totalCredit;
	
	@Column(length = 13, precision = 13, scale = 2)
	private BigDecimal totalDebit;
	
	@Column(length = 13, precision = 13, scale = 2)
	private BigDecimal totalGeneral;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = User.class)
	@JoinColumn(name = "user_id")
	@ForeignKey(name = "FK_CLOSURE_USER")
	private User user;
	
	@Transient
	private Collection<Register> debitsAlreadyClosed;
	
	@Transient
	private Collection<Register> creditsAlreadyClosed;

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

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
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

	public Collection<Register> getCreditsAlreadyClosed() {
		return creditsAlreadyClosed;
	}

	public void setCreditsAlreadyClosed(Collection<Register> creditsAlreadyClosed) {
		this.creditsAlreadyClosed = creditsAlreadyClosed;
	}
}
