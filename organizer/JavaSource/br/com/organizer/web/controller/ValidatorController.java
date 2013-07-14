package br.com.organizer.web.controller;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.organizer.business.UsuarioBO;
import br.com.organizer.model.dto.UsuarioDTO;
import br.com.organizer.util.MessageFactory;
import br.com.organizer.util.ServiceFinder;

@Component("validatorController")
@Scope("session")
public class ValidatorController implements Serializable{
	
	private static final long serialVersionUID = 1L;

	UsuarioBO usuarioBO = (UsuarioBO) ServiceFinder.findBean("usuarioBOTarget");
	
	UsuarioDTO usuario;
	
	public ValidatorController() {
		this.usuario = new UsuarioDTO();
	}
	
	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(
			false);

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	// valida a entrada de e-mails no cadastro e área de login
	public void validaEmail(FacesContext context, UIComponent componente,
			Object objeto) throws ValidatorException {
		String digitado = (String) objeto;
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(digitado);
		boolean matchFound = m.matches();

		if (!matchFound) {
			((UIInput) componente).setValid(false);
			FacesMessage message = new FacesMessage(MessageFactory.getMessage("error_emailInvalido"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(componente.getClientId(context), message);
		}
	}
	
	public void verificaUsername(FacesContext context, UIComponent componente,
			Object objeto) throws ValidatorException {
		String usuario = (String) objeto;
		this.usuario.setUsuario(usuario);
		if(this.usuario.getCodigo()==null && usuarioBO.verificaUsername(this.usuario)){
			((UIInput) componente).setValid(false);
			FacesMessage message = new FacesMessage(MessageFactory.getMessage("error_usuarioJaExiste"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(componente.getClientId(context), message);
		}
	}
	
	public void getSenha(FacesContext context, UIComponent componente,
			Object objeto){
		String senha = (String) objeto;
		this.usuario.setSenha(senha);
	}
	
	public void getUsuarioCodigo(FacesContext context, UIComponent componente,
			Object objeto){
		Integer codigo = (Integer) objeto;
		this.usuario.setCodigo(codigo);
	}
	
	public void confirmaSenha(FacesContext context, UIComponent componente,
			Object objeto) throws ValidatorException {
		String confirmacao = (String) objeto;
		if (!usuario.getSenha().equals(confirmacao)) {
			((UIInput) componente).setValid(false);
			FacesMessage message = new FacesMessage(MessageFactory.getMessage("error_senhaDifConfirmacao"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(componente.getClientId(context), message);
		}
	}
	
	public void verificaPais(FacesContext context, UIComponent componente,
			Object objeto) throws ValidatorException {
		Integer codigo = (Integer) objeto;
		if (codigo == 0) {
			((UIInput) componente).setValid(false);
			FacesMessage message = new FacesMessage(MessageFactory.getMessage("error_pais"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(componente.getClientId(context), message);
		}
	}
	
	public void verificaEstado(FacesContext context, UIComponent componente,
			Object objeto) throws ValidatorException {
		Integer codigo = (Integer) objeto;
		if (codigo == 0) {
			((UIInput) componente).setValid(false);
			FacesMessage message = new FacesMessage(MessageFactory.getMessage("error_estado"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(componente.getClientId(context), message);
		}
	}
	
	public void verificaCidade(FacesContext context, UIComponent componente,
			Object objeto) throws ValidatorException {
		Integer codigo = (Integer) objeto;
		if (codigo == 0) {
			((UIInput) componente).setValid(false);
			FacesMessage message = new FacesMessage(MessageFactory.getMessage("error_cidade"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(componente.getClientId(context), message);
		}
	}
}
