package br.com.organizer.dao.hibernate;

import java.sql.SQLException;
import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.organizer.dao.UsuarioDAO;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.UsuarioDTO;

@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UsuarioHIB extends GenericoHIB<Usuario> implements UsuarioDAO {

	public Usuario carregaUsuarioPorUsername(final String userName) {
		return (Usuario) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createQuery(
								"from Usuario as user where "
										+ "user.usuario = :userName and user.excluido = false")
								.setString("userName", userName).uniqueResult();
					}
				});
	}

	@SuppressWarnings("unchecked")
	public Boolean verificaUsername(UsuarioDTO usuarioDTO) {
		Object[] obj = { usuarioDTO.getUsuario() };
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Usuario user");
		sql.append(" where excluido = false ");
		sql.append(" and user.usuario = ? ");
		
		Collection<Usuario> res = getHibernateTemplate().find(sql.toString(), obj);
		
		if (res.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public Usuario verificaUsuarioESenha(String userName, String password) {
		Object[] obj = { userName,password };
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Usuario user");
		sql.append(" where user.usuario = ? ");
		sql.append(" and user.senha = ? ");
		
		Collection<Usuario> res = getHibernateTemplate().find(sql.toString(), obj);
		
		if (res.size()>0){
			return res.iterator().next();
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Usuario carregarPorCodigo(Integer codigo) {
		Object[] obj = { codigo };
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Usuario user");
		sql.append(" where user.codigo = ? ");
		
		Collection<Usuario> res = getHibernateTemplate().find(sql.toString(), obj);
		
		return res.iterator().next();
	}

	@SuppressWarnings("unchecked")
	public Collection<Usuario> listarUsuarios(Usuario usuario) {
		Object[] obj = { usuario.getCodigo() };
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Usuario u ");
		sql.append(" where u.masterCodigo = ? ");
		sql.append(" and u.excluido = false ");
		sql.append(" order by u.nome ");

		Collection<Usuario> res = getHibernateTemplate().find(sql.toString(), obj);

		return res;
	}

	@SuppressWarnings("unchecked")
	public Collection<Usuario> listarUsuariosAdm(Usuario usuario) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from Usuario u ");
		sql.append(" where u.excluido = false ");
		sql.append(" order by u.codigo,u,masterCodigo,u.nome ");

		Collection<Usuario> res = getHibernateTemplate().find(sql.toString());

		return res;
	}
}
