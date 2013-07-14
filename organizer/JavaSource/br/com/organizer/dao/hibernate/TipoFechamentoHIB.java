package br.com.organizer.dao.hibernate;

import java.util.Collection;

import br.com.organizer.dao.TipoFechamentoDAO;
import br.com.organizer.model.TipoFechamento;

public class TipoFechamentoHIB extends GenericoHIB<TipoFechamento> implements
		TipoFechamentoDAO {

	public TipoFechamento carregaTipoFechamentoPorCodigo(
			Integer tipoFechamentoCodigo) {
		return (TipoFechamento) getHibernateTemplate().load(TipoFechamento.class, tipoFechamentoCodigo);
	}

	@SuppressWarnings("unchecked")
	public Collection<TipoFechamento> listarTiposFechamento() {
		StringBuilder sql = new StringBuilder();
		sql.append(" from TipoFechamento ");
		sql.append(" order by tipoFechamento ");
		
		Collection<TipoFechamento> res = getHibernateTemplate().find(sql.toString());
		
		return res;
	}

	@SuppressWarnings("unchecked")
	public Collection<TipoFechamento> listarTiposFechamentoPorIdioma(
			String idioma) {
		Object[] obj = { idioma };
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from TipoFechamento");
		sql.append(" where idioma = ? ");
		
		Collection<TipoFechamento> res = getHibernateTemplate().find(sql.toString(), obj);
		
		return res;
	}

	@SuppressWarnings("unchecked")
	public TipoFechamento carregarTipoFechamentoPorTipo(String tipo) {
		Object[] obj = { tipo };
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from TipoFechamento ");
		sql.append(" where tipoFechamento = ? ");
		
		Collection<TipoFechamento> res = getHibernateTemplate().find(sql.toString(), obj);
		
		if (res.size()>0){
			return res.iterator().next();
		}else{
			return null;
		}
	}

}
