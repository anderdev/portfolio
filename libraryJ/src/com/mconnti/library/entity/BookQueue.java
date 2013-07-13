package com.mconnti.library.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookQueue {
	
	private Long id;
	
	private User user;
	
	private Book book;
	
	private Date dateIn;
	
	private Boolean renting;
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ID: "+getId()).append(" - USER: "+getUser()).append(" - BOOK: "+getBook()).append(" - Date IN: "+getDateIn());
		return sb.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Boolean getRenting() {
		return renting;
	}

	public void setRenting(Boolean renting) {
		this.renting = renting;
	}

	public Date getDateIn() {
		return dateIn;
	}

	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}
}
