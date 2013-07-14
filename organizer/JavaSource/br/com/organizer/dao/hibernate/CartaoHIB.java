package br.com.organizer.dao.hibernate;

import java.util.Collection;

import br.com.organizer.dao.CartaoDAO;
import br.com.organizer.model.Cartao;
import br.com.organizer.model.Usuario;


public class CartaoHIB extends GenericoHIB<Cartao> implements CartaoDAO {

	public Cartao carregarPorCodigo(Integer codigo) {
		return (Cartao) getHibernateTemplate().load(Cartao.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public Collection<Cartao> listarCartao(Usuario usuario) {
				
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Cartao ");
		if(usuario.getMasterCodigo()==null){
			sql.append(" where master_codigo = "+usuario.getCodigo());
		}else{
			sql.append(" where master_codigo = "+usuario.getMasterCodigo());
		}
		sql.append(" order by codigo ");
		
		Collection<Cartao> res = getHibernateTemplate().find(sql.toString());
		
		return res;
	}

	@SuppressWarnings("unchecked")
	public Cartao carregarCartaoPorDescricao(String descricao) {
		Object[] obj = { descricao };
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Cartao ");
		sql.append(" where descricao = ? ");
		
		Collection<Cartao> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return res.iterator().next();
	}
}
