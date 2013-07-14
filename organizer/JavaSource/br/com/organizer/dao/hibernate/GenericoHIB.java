package br.com.organizer.dao.hibernate;

import java.io.Serializable;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.organizer.dao.GenericoDAO;

@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class GenericoHIB<T> extends HibernateDaoSupport implements
		GenericoDAO<T> {

	public Serializable salvar(Serializable obj) {
		getHibernateTemplate().saveOrUpdate(obj);
		return obj;
	}

	public Serializable excluir(Serializable obj) {
		getHibernateTemplate().delete(obj);
		return obj;
	}
}
