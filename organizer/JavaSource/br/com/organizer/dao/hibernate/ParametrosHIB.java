package br.com.organizer.dao.hibernate;

import java.util.Collection;

import br.com.organizer.dao.ParametrosDAO;
import br.com.organizer.model.Parametros;
import br.com.organizer.model.Usuario;

public class ParametrosHIB extends GenericoHIB<Parametros> implements ParametrosDAO {

	@SuppressWarnings("unchecked")
	public Parametros carregaParametroPorCodigo(Integer codigoParametro) {
		Object[] obj = {codigoParametro };
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Parametros p");
		sql.append(" where p.codigo = ? ");
//		sql.append(" where p.usuario = ? ");
		
		Collection<Parametros> res = getHibernateTemplate().find(sql.toString(), obj);
		
		if (res.size()>0){
			return res.iterator().next();
		}else{
			return null;
		}
	}

	public Collection<Parametros> listarParametros(Usuario usuario) {
		return null;
	}

}
