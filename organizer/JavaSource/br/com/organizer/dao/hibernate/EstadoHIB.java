package br.com.organizer.dao.hibernate;

import java.util.Collection;

import br.com.organizer.dao.EstadoDAO;
import br.com.organizer.model.Estado;


public class EstadoHIB extends GenericoHIB<Estado> implements EstadoDAO {

	@SuppressWarnings("unchecked")
	public Collection<Estado> carregarEstadosPorPais(Integer codigoPais) {
		Object[] obj = { codigoPais };
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Estado ");
		sql.append(" where pais_codigo = ? ");
		sql.append(" order by estado ");
		
		Collection<Estado> res = getHibernateTemplate().find(sql.toString(), obj);
		
		return res;
	}

	public Estado carregarEstado(Estado estado) {
		return (Estado) getHibernateTemplate().load(Estado.class, estado.getCodigo());
	}
}
