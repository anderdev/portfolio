package com.mconnti.selfmanager.business.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mconnti.selfmanager.business.GenericBO;
import com.mconnti.selfmanager.persistence.impl.GenericDAOImpl;

public class GenericBOImpl<T> implements GenericBO<T>{

	@Autowired
	private GenericDAOImpl<T> genericDAO;

	@SuppressWarnings("hiding")
	public <T> T findById(final Class<T> type, Long id) throws Exception {
		return genericDAO.findById(type, id);
	}

	@SuppressWarnings("hiding")
	public <T> T findByParameter(final Class<T> type, Map<String, String> queryParams) throws Exception {
		return genericDAO.findByParameter(type, queryParams);
	}

	public List<T> listPaginated(final Class<T> type, int startRow, int pageSize, Map<String, Object> queryParans, Boolean orderBy, String orderByField) throws Exception {
		return genericDAO.listPaginated(type, startRow, pageSize, queryParans, orderBy, orderByField);
	}

	public List<T> list(final Class<T> type, Map<String, String> queryParams, String orderByField) throws Exception {
		return genericDAO.list(type, queryParams, orderByField);
	}

	public Integer count(Class<T> type, Map<String, String> queryParans) throws Exception {
		return genericDAO.count(type, queryParans);
	}

	public T saveGeneric(T obj) throws Exception {
		return genericDAO.save(obj);
	}

	public Boolean remove(T obj) throws Exception {
		return genericDAO.remove(obj);
	}
}
