package com.mconnti.moneymanager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreditCard implements Serializable {

	private static final long serialVersionUID = -3160404156422105911L;
	
	private Long id;
	
	private String name;
	
	private Integer payday;
	
	private Integer lastDayToBuy;
	
	private Date expireDate;
	
	private String expire;
	
	private User user;
	
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
