package br.com.organizer.dao.hibernate;

import java.util.Collection;

import br.com.organizer.dao.ContaDAO;
import br.com.organizer.model.Conta;

public class ContaHIB extends GenericoHIB<Conta> implements ContaDAO {

	@SuppressWarnings("unchecked")
	public Collection<Conta> listarContas(String locale) {
		Object[] obj = { locale };
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Conta ");
		sql.append(" where locale = ? ");
		sql.append(" order by codigo ");
		
		Collection<Conta> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;
	}

	public Conta carregarContaPorCodigo(Integer codigo) {
		return (Conta) getHibernateTemplate().load(Conta.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public Conta carregarContaPorDescricao(String descricao) {
		Object[] obj = { descricao };
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Conta ");
		sql.append(" where lower(descricao) = ? ");
		
		Collection<Conta> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res.iterator().next();
	}
}
