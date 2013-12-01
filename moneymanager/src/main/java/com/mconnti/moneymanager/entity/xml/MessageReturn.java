package com.mconnti.moneymanager.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.mconnti.moneymanager.entity.Country;
import com.mconnti.moneymanager.entity.User;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement
public class MessageReturn {
	
	private String message;
	
	private User user;
	
	private Country country;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
