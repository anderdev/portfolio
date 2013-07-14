package br.com.organizer.web.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.organizer.business.RegistroAcessoBO;
import br.com.organizer.business.UsuarioBO;
import br.com.organizer.exception.OrganizerException;
import br.com.organizer.model.RegistroAcesso;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.LoginDTO;
import br.com.organizer.util.Constantes;
import br.com.organizer.util.Criptografa;
import br.com.organizer.util.MessageFactory;
import br.com.organizer.util.ServiceFinder;

@Component("loginController")
@Scope("request")
public class LoginController implements Serializable {

	public Logger logger = Logger.getLogger(LoginController.class);

	private static final long serialVersionUID = 1L;

	UsuarioBO usuarioBO = (UsuarioBO) ServiceFinder.findBean("usuarioBOTarget");

	RegistroAcessoBO registroAcessoBO = (RegistroAcessoBO) ServiceFinder.findBean("registroAcessoBOTarget");

	private Usuario usuario;

	private LoginDTO loginDTO;

	private String locale;

	private String calendarLocale;

	private String popUp;

	public Long contador;

	private Boolean confirmaSenha = false;

	public Integer altura = 0;

	// captura a sessão do contexto criado pelo JavaServer Faces
	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();

	public LoginController() {
		logger.warn("Acessou LoginController");
		this.loginDTO = new LoginDTO();
		this.usuario = new Usuario();
		if(session.getAttribute("usuarioLogado")!=null){
			this.usuario = (Usuario) session.getAttribute("usuarioLogado");
		}
		contador();
		String loginError = (String) session.getAttribute("loginErro");
		if (loginError != null && !"".equals(loginError)) {
			FacesMessage message = new FacesMessage(MessageFactory.getMessage(loginError));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, message);
			session.removeAttribute("loginErro");
		}
		logger.warn("Finalizou LoginController");
	}

	public void contador() {
		logger.warn("Acessou Contador");
		RegistroAcesso registroAcesso = new RegistroAcesso();
		registroAcesso = (RegistroAcesso) session.getAttribute("registroAcesso");
		logger.info("RegistroAcesso: " + registroAcesso);
		if (registroAcesso == null) {
			registroAcesso = registroAcessoBO.salvarECarregar(request);
		} else {
			if(usuario.getCodigo()!=null){
				registroAcesso.setUsuario(usuario);
				registroAcessoBO.salvar(registroAcesso);
			}
		}
		logger.info("RegistroAcesso: " + registroAcesso);
		this.contador = registroAcessoBO.contador();
		logger.info("contador: " + contador);
		session.setAttribute("registroAcesso", registroAcesso);
		logger.warn("Finalizou Contador");
	}

	public String login() {
		logger.warn("Acessou login");
		try {
			if (!this.confirmaSenha && !"".equals(loginDTO.getPassword()) && Constantes.SENHA_PADRAO.equalsIgnoreCase(loginDTO.getPassword())) {
				this.confirmaSenha = true;
				logger.info("confirmaSenha: " + this.confirmaSenha);
				session.setAttribute("newUser", loginDTO.getUser());
				session.setAttribute("newUserPassword", loginDTO.getPassword());
				logger.warn("Finalizou login");
				return "index";
			} else if (this.confirmaSenha && loginDTO.getNovaSenhaPalavraSecreta() != null && loginDTO.getNovaSenha() != null) {
				this.confirmaSenha = false;
				loginDTO.setUser((String) session.getAttribute("newUser"));
				loginDTO.setPassword((String)session.getAttribute("newUserPassword"));
				session.removeAttribute("newUser");
				session.removeAttribute("newUserPassword");
				logger.info("confirmaSenha: " + this.confirmaSenha);
				usuario = usuarioBO.alterarSenha(loginDTO);
				if (Criptografa.encrypt(loginDTO.getNovaSenhaPalavraSecreta()).equalsIgnoreCase(usuario.getPalavraSecreta())) {
					usuario.setSenha(Criptografa.decrypt(usuario.getSenha()));
				}
			} else {
				this.confirmaSenha = false;
				logger.info("confirmaSenha: " + this.confirmaSenha);
				usuario.setUsuario(loginDTO.getUser());
				usuario.setSenha(loginDTO.getPassword());
			}
			logger.info("usaurio: " + usuario);
			usuario = usuarioBO.verificaLogin(usuario);
			contador();
			session.setAttribute("usuarioLogado", usuario);
		} catch (OrganizerException e) {
			logger.info("OrganizerException: " + e.getMessage());
			FacesMessage message = new FacesMessage(MessageFactory.getMessage(e.getMessage()));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, message);
			logger.warn("Finalizou login");
			return "index";
		}
		if (usuario.getMasterCodigo() != null) {
			Usuario u = usuarioBO.carregarPorCodigo(usuario, false);
			logger.info("usaurio: " + u);
			if (u.getParametro() == null) {
				logger.warn("Finalizou login");
				return "manterConfiguracaoParametros";
			} else {
				logger.warn("Finalizou login");
				return "logado";
			}
		} else if (usuario.getParametro() == null) {
			logger.warn("Finalizou login");
			return "manterConfiguracaoParametros";
		} else {
			logger.warn("Finalizou login");
			return "logado";
		}
	}

	public String logout() {
		logger.warn("Acessou logout");
		session.removeAttribute("language");
		session.removeAttribute("usuarioLogado");
		session.removeAttribute("loginErro");
		logger.warn("Finalizou logout");
		return "index";

	}

	public String getLocale() {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		logger.info("Usuario: " + usuario);
		if (usuario.getIdioma().equals("portugues")) {
			this.locale = "pt_BR";
		} else {
			this.locale = "en";
		}
		return locale;
	}

	public String getCalendarLocale() {
		if (getLocale().equals("pt_BR")) {
			this.calendarLocale = "pt";
		} else {
			this.calendarLocale = "en";
		}
		return this.calendarLocale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getPopUp() {
		return popUp;
	}

	public void setPopUp(String popUp) {
		this.popUp = popUp;
	}

	public Long getContador() {
		return contador;
	}

	public void setContador(Long contador) {
		this.contador = contador;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LoginDTO getLoginDTO() {
		return loginDTO;
	}

	public void setLoginDTO(LoginDTO loginDTO) {
		this.loginDTO = loginDTO;
	}

	public Boolean getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(Boolean confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public Integer getAltura() {
		return altura;
	}

	public void setAltura(Integer altura) {
		this.altura = altura;
	}
}
