package com.library.entity.xml;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.library.entity.User;

@XmlRootElement
public class SearchObject {
	
	private String orderBy;
	
	private Map<String,String> queryParams;
	
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
	}
	
	
}
