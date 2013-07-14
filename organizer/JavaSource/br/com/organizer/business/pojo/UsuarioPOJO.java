package br.com.organizer.business.pojo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import br.com.organizer.business.UsuarioBO;
import br.com.organizer.dao.CidadeDAO;
import br.com.organizer.dao.EstadoDAO;
import br.com.organizer.dao.PaisDAO;
import br.com.organizer.dao.UsuarioDAO;
import br.com.organizer.exception.OrganizerException;
import br.com.organizer.model.Estado;
import br.com.organizer.model.Pais;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.LoginDTO;
import br.com.organizer.model.dto.UsuarioDTO;
import br.com.organizer.util.Constantes;
import br.com.organizer.util.Criptografa;
import br.com.organizer.util.Utils;

public class UsuarioPOJO extends GenericoPOJO<Usuario> implements UsuarioBO {
	
	public Logger logger = Logger.getLogger(UsuarioPOJO.class);

	public UsuarioPOJO() {
	}

	private UsuarioDAO usuarioDAO;

	private CidadeDAO cidadeDAO;

	private EstadoDAO estadoDAO;

	private PaisDAO paisDAO;
	
	ContatoPOJO contato = new ContatoPOJO();

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public void setCidadeDAO(CidadeDAO cidadeDAO) {
		this.cidadeDAO = cidadeDAO;
	}

	public void setEstadoDAO(EstadoDAO estadoDAO) {
		this.estadoDAO = estadoDAO;
	}

	public void setPaisDAO(PaisDAO paisDAO) {
		this.paisDAO = paisDAO;
	}

	public Usuario carregarUsuarioPorUsername(String userName) {
		return usuarioDAO.carregaUsuarioPorUsername(userName);
	}

	public Usuario verificaLogin(Usuario usuario) throws OrganizerException {

		Usuario usuarioTemp = null;
		if ("".equals(usuario.getUsuario()) || usuario.getUsuario() == null) {
			throw new OrganizerException("error_emptyUser");
		} else {
			usuarioTemp = usuarioDAO.carregaUsuarioPorUsername(usuario.getUsuario());
		}

		if (usuarioTemp == null) {
			throw new OrganizerException("error_usuarioIncorreto");
		} else if (usuarioDAO.verificaUsuarioESenha(usuario.getUsuario(), Criptografa.encrypt(usuario.getSenha())) == null) {
			throw new OrganizerException("error_senhaIncorreta");
		} else {
			return usuarioTemp;
		}
	}

	public Boolean verificaUsername(UsuarioDTO usuarioDTO) {
		return usuarioDAO.verificaUsername(usuarioDTO);
	}

	public void salvar(UsuarioDTO usuarioDTO) throws OrganizerException {
		Usuario usuario = new Usuario();

		if (usuarioDTO.getCodigo() != null && usuarioDTO.getCodigo() > 0) {
			usuario = usuarioDAO.carregarPorCodigo(usuarioDTO.getCodigo());
		} else {
			usuario.setDtCadastro(new Date());
			if (usuarioDTO.getUsuario().equals("ander.santos")) {
				usuario.setAdministrador(true);
			} else {
				usuario.setAdministrador(false);
			}
		}

		if (usuarioDTO.getMasterCodigo() != null) {
			usuario.setMasterCodigo(usuarioDTO.getMasterCodigo());
			if (usuarioDTO.getCodigo() == 0) {
				usuarioDTO.setSenha(Constantes.SENHA_PADRAO);
			}
		}
		usuario.setExcluido(false);
		usuario.setPalavraSecreta(Criptografa.encrypt(usuarioDTO.getPalavraSecreta()));
		usuario.setUsuario(usuarioDTO.getUsuario());
		usuario.setSenha(Criptografa.encrypt(usuarioDTO.getSenha()));
		usuario.setNome(usuarioDTO.getNome());
		usuario.setEmail(Criptografa.encrypt(usuarioDTO.getEmail()));
		usuario.setDtNasc(Utils.dataToString(usuarioDTO.getDtnasc()));
		usuario.setIdioma(usuarioDTO.getIdioma());
		usuario.setCidade(cidadeDAO.carregaCidadePorCodigo(usuarioDTO.getCidadeCodigo()));

		usuarioDAO.salvar(usuario);
	}

	public UsuarioDTO carregarUsuario(Usuario usuario) throws OrganizerException {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setCodigo(usuario.getCodigo());
		usuarioDTO.setCidadeCodigo(usuario.getCidade().getCodigo());
		usuarioDTO.setDtnasc(Utils.stringToData(usuario.getDtNasc(), false));
		usuarioDTO.setEmail(Criptografa.decrypt(usuario.getEmail()));

		Estado estado = estadoDAO.carregarEstado(usuario.getCidade().getEstadoCodigo());
		usuarioDTO.setEstadoCodigo(estado.getCodigo());
		usuarioDTO.setIdioma(usuario.getIdioma());
		usuarioDTO.setMasterCodigo(usuario.getMasterCodigo());
		usuarioDTO.setNome(usuario.getNome());

		Pais pais = paisDAO.carregarPais(estado.getPaisCodigo());
		usuarioDTO.setPaisCodigo(pais.getCodigo());
		usuarioDTO.setUsuario(usuario.getUsuario());

		if (usuario.getMasterCodigo() == null) {
			usuarioDTO.setUsuarioMaster(usuario.getNome());
		} else {
			Usuario usu = new Usuario();
			usu = usuarioDAO.carregarPorCodigo(usuario.getMasterCodigo());
			usuarioDTO.setUsuarioMaster(usu.getNome());
		}

		usuarioDTO.setSenha(Criptografa.decrypt(usuario.getSenha()));
		usuarioDTO.setConfirmacao(Criptografa.decrypt(usuario.getSenha()));
		usuarioDTO.setDtCadastro(usuario.getDtCadastro());
		usuarioDTO.setPalavraSecreta(Criptografa.decrypt(usuario.getPalavraSecreta()));
		return usuarioDTO;
	}

	public Usuario carregarPorCodigo(Usuario usuario, Boolean masterUsuario) {
		if (masterUsuario) {
			return usuarioDAO.carregarPorCodigo(usuario.getCodigo());
		} else {
			return usuarioDAO.carregarPorCodigo(usuario.getMasterCodigo());
		}

	}

	public Usuario alterarSenha(LoginDTO loginDTO) throws OrganizerException {
		Usuario usuario = new Usuario();
		usuario = carregarUsuarioPorUsername(loginDTO.getUser());
		try {
			if(!Criptografa.decrypt(usuario.getPalavraSecreta()).equals(loginDTO.getNovaSenhaPalavraSecreta())){
				throw new OrganizerException("error_palavraSecreta");
			}
			if (Criptografa.decrypt(usuario.getSenha()).equalsIgnoreCase(loginDTO.getPassword())) {
				usuario.setSenha(Criptografa.encrypt(loginDTO.getNovaSenha()));
				usuarioDAO.salvar(usuario);
				return usuario;
			}else{
				throw new OrganizerException("error_dadosIncorretos");
			}
		}catch (OrganizerException e) {
			throw new OrganizerException(e.getMessage());
		}catch (Exception e) {
			try {
				contato.enviarEmail("ander.dev@gmail.com", "organizer@gmail.com", "Organizer", "Exceção Gerada", e.getMessage());
			}catch (UnsupportedEncodingException e1) {
				logger.warn("Enviar e-mail UnsupportedEncodingException: " + e1.getMessage());
			}catch (MessagingException e1) {
				logger.warn("Enviar e-mail MessagingException: " + e1.getMessage());
			} 
			throw new OrganizerException("error_inesperado");
		}
	}

	public Collection<UsuarioDTO> listarUsuarios(Usuario usuario) throws OrganizerException {
		Collection<Usuario> usuarios = usuarioDAO.listarUsuarios(usuario);
		Collection<UsuarioDTO> listaDeUsuarios = new ArrayList<UsuarioDTO>();
		UsuarioDTO usuarioDTO = new UsuarioDTO();

		for (Usuario usu : usuarios) {
			usuarioDTO = carregarUsuario(usu);
			listaDeUsuarios.add(usuarioDTO);
		}
		return listaDeUsuarios;
	}

	public Collection<UsuarioDTO> listarUsuariosAdm(Usuario usuario) throws OrganizerException {
		Collection<Usuario> usuarios = usuarioDAO.listarUsuariosAdm(usuario);
		Collection<UsuarioDTO> listaDeUsuarios = new ArrayList<UsuarioDTO>();
		UsuarioDTO usuarioDTO = new UsuarioDTO();

		for (Usuario usu : usuarios) {
			usuarioDTO = carregarUsuario(usu);
			listaDeUsuarios.add(usuarioDTO);
		}
		return listaDeUsuarios;
	}
}
