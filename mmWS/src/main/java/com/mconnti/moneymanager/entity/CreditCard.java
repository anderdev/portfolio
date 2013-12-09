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
@Table(name="creditCard")
public class CreditCard implements Serializable {

	private static final long serialVersionUID = -3160404156422105911L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, insertable = true, updatable = false)
	private Long id;
	
	private String name;
	
	@Column(name="payday")
	private Integer payday;
	
	@Column(name="lastDayToBuy")
	private Integer lastDayToBuy;
	
	@Column(name="expireDate")
	private Date expireDate;
	
	@Transient
	private String expire;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = User.class)
	@JoinColumn(name = "user_id")
	@ForeignKey(name = "FK_CREDITCARD_USER")
	private User user;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = User.class)
	@JoinColumn(name = "master_id")
	@ForeignKey(name = "FK_CREDITCARD_MASTER_USER")
	private User masterUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPayday() {
		return payday;
	}

	public void setPayday(Integer payday) {
		this.payday = payday;
	}

	public Integer getLastDayToBuy() {
		return lastDayToBuy;
	}

	public void setLastDayToBuy(Integer lastDayToBuy) {
		this.lastDayToBuy = lastDayToBuy;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getMasterUser() {
		return masterUser;
	}

	public void setMasterUser(User masterUser) {
		this.masterUser = masterUser;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

}
