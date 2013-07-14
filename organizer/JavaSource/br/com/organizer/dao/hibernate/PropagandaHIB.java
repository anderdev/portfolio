package br.com.organizer.dao.hibernate;

import java.util.Collection;

import br.com.organizer.dao.PropagandaDAO;
import br.com.organizer.model.Propaganda;

public class PropagandaHIB extends GenericoHIB<Propaganda> implements
		PropagandaDAO {

	public Propaganda carregarPorCodigo(Integer codigo) {
		return (Propaganda) getHibernateTemplate().load(Propaganda.class,
				codigo);
	}

	@SuppressWarnings("unchecked")
	public Collection<Propaganda> listar() {
		StringBuilder sql = new StringBuilder();

		sql.append(" from Propaganda ");
		sql.append(" order by codigo ");

		Collection<Propaganda> res = getHibernateTemplate().find(sql.toString());

		return res;
	}
}
