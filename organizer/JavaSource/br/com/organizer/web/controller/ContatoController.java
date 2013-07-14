package br.com.organizer.web.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.organizer.business.ContatoBO;
import br.com.organizer.model.dto.ContatoDTO;
import br.com.organizer.util.MessageFactory;
import br.com.organizer.util.ServiceFinder;

@Component("contatoController")
@Scope("request")
public class ContatoController implements Serializable {

	public Logger logger = Logger.getLogger(ContatoController.class);
	
	private static final long serialVersionUID = 1L;

	ContatoBO contatoBO = (ContatoBO) ServiceFinder.findBean("contatoBOTarget");

	ContatoDTO contatoDTO;

	public ContatoController() {
		this.contatoDTO = new ContatoDTO();
	}

	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	
	HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();

	public ContatoDTO getContatoDTO() {
		return contatoDTO;
	}

	public void setContatoDTO(ContatoDTO contatoDTO) {
		this.contatoDTO = contatoDTO;
	}

	public String enviarEmail() {
		String emailDestinatario = "ouvidoria@mconnti.com";
		String emailRemetente = contatoDTO.getEmail();
		String nomeRemetente = contatoDTO.getNome();
		String assuntoMensagem = "Contato realizado pelo Organizer.";
		String mensagem = contatoDTO.getMensagem();

		try {
			contatoBO.enviarEmail(emailDestinatario, emailRemetente, nomeRemetente, assuntoMensagem, mensagem);
			FacesMessage message = new FacesMessage(MessageFactory.getMessage("info_emailEnviado"));
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (UnsupportedEncodingException e) {
			FacesMessage message = new FacesMessage(MessageFactory.getMessage("error_emailNaoEnviado"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, message);
			logger.warn("Enviar e-mail UnsupportedEncodingException: " + e.getMessage());
		} catch (MessagingException e) {
			FacesMessage message = new FacesMessage(MessageFactory.getMessage("error_emailNaoEnviado"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, message);
			logger.warn("Enviar e-mail MessagingException: " + e.getMessage());
		}
		return "index";
	}

	public String voltarIndex() {
		return "index";
	}
}
