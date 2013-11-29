package com.mconnti.selfmanager.business;

import java.util.List;
import java.util.Map;

@SuppressWarnings("hiding")
public interface GenericBO<T> {

	public <T> T findById(final Class<T> type, final Long id) throws Exception;

	public <T> T findByParameter(final Class<T> type, final Map<String, String> queryParans) throws Exception;

	public List<T> listPaginated(final Class<T> type, int startRow, int pageSize, Map<String, Object> queryParans, Boolean orderBy, String orderByField) throws Exception;

	public List<T> list(final Class<T> type, Map<String, String> queryParams, String orderByField) throws Exception;

	public Integer count(final Class<T> type, Map<String, String> queryParans) throws Exception;

	public T saveGeneric(T obj) throws Exception;

	public Boolean remove(T obj) throws Exception;

}
