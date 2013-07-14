package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.UsuarioDTO;

public interface UsuarioDAO extends GenericoDAO<Usuario> {

	public Usuario carregaUsuarioPorUsername(String userName);
	
	public Usuario carregarPorCodigo(Integer codigo);
	
	public Usuario verificaUsuarioESenha(String userName, String password);
	
	public Boolean verificaUsername(UsuarioDTO usuarioDTO);
	
	public Collection<Usuario> listarUsuarios(Usuario usuario);
	
	public Collection<Usuario> listarUsuariosAdm(Usuario usuario);
}
