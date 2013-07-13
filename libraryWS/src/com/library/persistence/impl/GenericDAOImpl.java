package com.library.persistence.impl;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.library.persistence.GenericDAO;

@Repository("genericDAO")
public class GenericDAOImpl<T> implements GenericDAO<T> {

	@PersistenceContext
	protected EntityManager em;

	@SuppressWarnings("hiding")
	public <T> T findById(final Class<T> type, final Long id) throws Exception {
		T obj = null;
		try {
			obj = (T) em.find(type, id);
		} catch (NoResultException nre) {
			obj = null;
		} catch (RuntimeException e) {
			throw new Exception(e.getMessage(), e);
		}
		return obj;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> T findByParameter(final Class<T> type, Map<String, String> queryParams) throws Exception {
		T obj = null;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append(" select x from ");
			sql.append(type.getSimpleName());
			sql.append(" x ");

			int x = 0;
			Iterator<Entry<String, String>> iterator = queryParams.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> element = iterator.next();
				String whereAnd = x == 0 ? " where " : " and ";
				sql.append(whereAnd).append(element.getKey()).append(element.getValue());
				x++;
			}
			Query query = em.createQuery(sql.toString());
			query.setHint("toplink.refresh", "true");
			obj = (T) query.getSingleResult();
		} catch (NoResultException nre) {
			obj = null;
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
		return obj;
	}

	public T save(T obj) throws Exception {
		try {
			obj = em.merge(obj);
			em.flush();
		} catch (Throwable e) {
			throw new Exception(e.getMessage(), e);
		}
		return obj;
	}

	public Boolean remove(T obj) throws Exception {
		try {
			Field field = obj.getClass().getDeclaredField("id");
			field.setAccessible(true);
			Object value = field.get(obj);
			em.remove(em.find(obj.getClass(), value));
			em.flush();
			return true;
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	public T merge(T obj) {
		return em.merge(obj);
	}

	/**
	 * Lista objetos da classe informada no primeiro parametro, retornando um lista paginada, caso queira esta lista ordenada, informe o segundo parametro como true e preencha os outros parametros de acordo com a ordenação desejada, caso contrario deixe o orderBy como NULL.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<T> listPaginated(final Class<T> type, int startRow, int pageSize, Map<String, Object> queryParans, Boolean orderBy, String orderByField) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" select x from ");
			sql.append(type.getSimpleName());
			sql.append(" x ");

			int x = 0;
			Iterator<Entry<String, Object>> iterator = queryParans.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> element = iterator.next();
				String whereAnd = x == 0 ? " where " : " and ";
				sql.append(whereAnd).append(element.getKey()).append(element.getValue());
				x++;
			}

			if (orderBy != null && orderBy) {
				sql.append(" order by ").append(orderByField);
			}

			Query query = em.createQuery(sql.toString());
			query.setFirstResult(startRow);
			query.setMaxResults(pageSize);

			query.setHint("toplink.refresh", "true");
			return query.getResultList();
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	/**
	 * Lista objetos da classe informada no primeiro parametro, retornando um lista n�o paginada, caso queira esta lista ordenada, informe o segundo parametro como true e preencha os outros parametros de acordo com a ordena��o desejada, caso contrario deixe o 2� e outros parametros como null.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<T> list(final Class<T> type, Map<String, String> queryParams, String orderByField) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" select x from ");
			sql.append(type.getSimpleName());
			sql.append(" x ");

			if (queryParams != null) {
				int x = 0;
				Iterator<Entry<String, String>> iterator = queryParams.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, String> element = iterator.next();
					String whereAnd = x == 0 ? " where " : " and ";
					sql.append(whereAnd).append(element.getKey()).append(" " +element.getValue());
					x++;
				}
			}

			if (orderByField != null && !orderByField.isEmpty()) {
				sql.append(" order by ").append(orderByField);
			}

			Query query = em.createQuery(sql.toString());

			query.setHint("toplink.refresh", "true");

			return query.getResultList();
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	public Integer count(final Class<T> type, Map<String, String> queryParans) throws Exception {
		Long ret = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" select count(x) from ");
			sql.append(type.getSimpleName());
			sql.append(" x ");

			int x = 0;
			Iterator<Entry<String, String>> iterator = queryParans.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> element = iterator.next();
				String whereAnd = x == 0 ? " where " : " and ";
				sql.append(whereAnd).append(element.getKey()).append(element.getValue());
				x++;
			}
			Query query = em.createQuery(sql.toString());
			ret = (Long) query.getSingleResult();
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
		return ret.intValue();
	}
}
