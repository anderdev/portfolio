package br.com.organizer.dao.hibernate;

import java.util.Collection;

import br.com.organizer.dao.DescricaoDAO;
import br.com.organizer.model.Conta;
import br.com.organizer.model.Descricao;
import br.com.organizer.model.Usuario;

public class DescricaoHIB extends GenericoHIB<Descricao> implements
		DescricaoDAO {

	public Descricao carregaDescricaoPorCodigo(
			Integer descricaoCodigo) {
		return (Descricao) getHibernateTemplate().load(Descricao.class, descricaoCodigo);
	}

	@SuppressWarnings("unchecked")
	public Collection<Descricao> listarDescricao(Usuario usuario) {
		Object[] obj = { usuario.getCodigo() };
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Descricao ");
		sql.append(" where usuario_codigo = ? ");
		sql.append(" order by descricao ");
		
		Collection<Descricao> res = getHibernateTemplate().find(sql.toString(), obj);
		
		return res;
	}

	@SuppressWarnings("unchecked")
	public Collection<Descricao> listarDescricaoPorUsuarioEConta(Usuario usuario, Conta conta) {
		Object[] obj = { usuario.getCodigo(),conta };
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Descricao ");
		sql.append(" where usuario_codigo = ? ");
		sql.append(" and tipoConta = ? ");
		sql.append(" order by descricao ");
		
		Collection<Descricao> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res;
	}

}
