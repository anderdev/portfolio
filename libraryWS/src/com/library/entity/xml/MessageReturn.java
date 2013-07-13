package com.library.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookQueue;
import com.library.entity.Category;
import com.library.entity.User;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement
public class MessageReturn {
	
	public String message;
	
	public User user;
	
	public Book book;
	
	public BookQueue bookQueue;
	
	public Category category;
	
	public Author author;

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

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BookQueue getBookQueue() {
		return bookQueue;
	}

	public void setBookQueue(BookQueue bookQueue) {
		this.bookQueue = bookQueue;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
}
