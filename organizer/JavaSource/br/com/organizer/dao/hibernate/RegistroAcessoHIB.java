package br.com.organizer.dao.hibernate;

import java.util.Collection;

import br.com.organizer.dao.RegistroAcessoDAO;
import br.com.organizer.model.RegistroAcesso;
import br.com.organizer.model.Usuario;


public class RegistroAcessoHIB extends GenericoHIB<RegistroAcesso> implements RegistroAcessoDAO {

	@SuppressWarnings("unchecked")
	public Collection<RegistroAcesso> listarRegistros() {
		StringBuilder sql = new StringBuilder();
	
		sql.append(" from RegistroAcesso ");
		sql.append(" order by codigo desc ");

		Collection<RegistroAcesso> res = getHibernateTemplate().find(sql.toString());

		return res;
	}

	@SuppressWarnings("unchecked")
	public RegistroAcesso carregarUltimoAcesso(Usuario usuario) {
		Object[] obj = { usuario };
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from RegistroAcesso r ");
		sql.append(" where r.usuario ? ");
		sql.append(" order by r.dataAcesso desc ");

		Collection<RegistroAcesso> res = getHibernateTemplate().find(sql.toString(),obj);

		if (res.size()>0){
			return res.iterator().next();
		}else{
			return null;
		}
	}

	public RegistroAcesso carregarRigistroAtual(Integer codigo) {
		return (RegistroAcesso) getHibernateTemplate().load(RegistroAcesso.class, codigo);
	}

}
