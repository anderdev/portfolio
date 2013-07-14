package br.com.organizer.business;

import java.util.Collection;

import br.com.organizer.exception.OrganizerException;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.LoginDTO;
import br.com.organizer.model.dto.UsuarioDTO;

public interface UsuarioBO extends GenericoBO<Usuario> {
	
	public Usuario carregarUsuarioPorUsername(String userName);
	
	public UsuarioDTO carregarUsuario(Usuario usuario) throws OrganizerException;
	
	public Usuario carregarPorCodigo(Usuario usuario, Boolean masterUsuario);
	
	public Usuario verificaLogin(Usuario usuario) throws OrganizerException;
	
	public Boolean verificaUsername(UsuarioDTO usuarioDTO);
	
	public void salvar(UsuarioDTO usuarioDTO) throws OrganizerException;
	
	public Usuario alterarSenha(LoginDTO loginDTO) throws OrganizerException ;
	
	public Collection<UsuarioDTO> listarUsuarios(Usuario usuario) throws OrganizerException;
	
	public Collection<UsuarioDTO> listarUsuariosAdm(Usuario usuario) throws OrganizerException;
	
}
