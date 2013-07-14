package br.com.organizer.dao.hibernate;

import java.util.Collection;

import br.com.organizer.dao.PaisDAO;
import br.com.organizer.model.Pais;

public class PaisHIB extends GenericoHIB<Pais> implements PaisDAO {

	@SuppressWarnings("unchecked")
	public Collection<Pais> carregarPaisesPorLocale(String locale) {
		Object[] obj = { locale };
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Pais p");
		sql.append(" where p.locale = ? ");
		sql.append(" order by p.pais ");
		
		Collection<Pais> res = getHibernateTemplate().find(sql.toString(), obj);
		
		return res;
	}

	public Pais carregarPais(Pais pais) {
		return (Pais) getHibernateTemplate().load(Pais.class, pais.getCodigo());
	}
}
