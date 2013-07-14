package br.com.organizer.dao.hibernate;

import java.util.Collection;

import br.com.organizer.dao.CidadeDAO;
import br.com.organizer.model.Cidade;


public class CidadeHIB extends GenericoHIB<Cidade> implements CidadeDAO {

	@SuppressWarnings("unchecked")
	public Collection<Cidade> carregarCidadesPorEstado(Integer codigoEstado) {
		Object[] obj = { codigoEstado };
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Cidade ");
		sql.append(" where estado_codigo = ? ");
		sql.append(" order by cidade ");
		
		Collection<Cidade> res = getHibernateTemplate().find(sql.toString(), obj);
		
		return res;
	}

	public Cidade carregaCidadePorCodigo(Integer cidadeCod) {
		return (Cidade) getHibernateTemplate().load(Cidade.class, cidadeCod);
	}
}
