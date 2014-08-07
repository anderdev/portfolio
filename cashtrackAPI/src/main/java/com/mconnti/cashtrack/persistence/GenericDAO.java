package com.mconnti.cashtrack.persistence;

import java.util.List;
import java.util.Map;

@SuppressWarnings("hiding")
public interface GenericDAO<T> {

	public <T> T findById(final Class<T> type, final Long id) throws Exception;
	
	public <T> T findByParameter(final Class<T> type, final Map<String, String> queryParans) throws Exception;

	public T save(T obj) throws Exception;

	public T merge(T obj) throws Exception;

	public Boolean remove(T obj) throws Exception;
	
	public List<T> listPaginated(final Class<T> type, int startRow, int pageSize, Map<String, Object> queryParans, Boolean orderBy, String orderByField) throws Exception;

	public List<T> list(final Class<T> type,  Map<String, String> queryParams, String orderByField) throws Exception;

	public Integer count(final Class<T> type, Map<String, String> queryParans) throws Exception;

}
