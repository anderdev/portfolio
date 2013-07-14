package br.com.organizer.dao.hibernate;

import java.util.Collection;

import br.com.organizer.dao.MoedaDAO;
import br.com.organizer.model.Moeda;

public class MoedaHIB extends GenericoHIB<Moeda> implements MoedaDAO {

	public Moeda carregaMoedaPorCodigo(
			Integer moedaCodigo) {
		return (Moeda) getHibernateTemplate().load(Moeda.class, moedaCodigo);
	}

	@SuppressWarnings("unchecked")
	public Collection<Moeda> listarMoedas() {
		StringBuilder sql = new StringBuilder();
		sql.append(" from Moeda m ");
		sql.append(" order by m.moeda ");
		
		Collection<Moeda> res = getHibernateTemplate().find(sql.toString());
		
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Moeda carregarMoedaPorSigla(String sigla) {
		Object[] obj = { sigla };
		StringBuilder sql = new StringBuilder();
		sql.append(" from Moeda m ");
		sql.append(" where sigla = ? ");
		
		Collection<Moeda> res = getHibernateTemplate().find(sql.toString(), obj);
		
		return res.iterator().next();
	}


}
