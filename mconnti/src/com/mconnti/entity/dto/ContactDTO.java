package com.mconnti.entity.dto;

import java.io.Serializable;

public class ContactDTO implements Serializable {

	private static final long serialVersionUID = 8587739960376118477L;

	private String name;

	private String email;

	private String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
