package com.mconnti.web.mbean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.business.PageViewBO;
import com.mconnti.entity.PageView;
import com.mconnti.entity.dto.ContactDTO;
import com.mconnti.web.util.FacesUtil;

@Scope("session")
@Controller
public class ContactMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// captura a sessão do contexto criado pelo JavaServer Faces
	FacesContext fc = FacesContext.getCurrentInstance();
	
	HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();

	@Autowired
	private PageViewBO pageViewBO;

	@ManagedProperty(value = "#{pageView}")
	private PageView pageView;
	
	private ContactDTO contactDTO;
	
	public Integer counter;
	
	@Transactional
	public Integer getCounter() {
		contactDTO = new ContactDTO();
		if (pageView == null) {
			pageView = new PageView();
			pageView.setLocale(request.getLocale().toString());
			pageView.setLocalName(request.getRemoteHost());
			pageView.setDateIn(new Date());
			pageView.setIp(request.getRemoteAddr());
			try {
				pageViewBO.saveGeneric(pageView);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<PageView> list = new ArrayList<PageView>();
		try {
			list = pageViewBO.list(PageView.class, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size();
	}
	
	public void checkEmail() throws ValidatorException {
		String email = this.contactDTO.getEmail();
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		boolean matchFound = m.matches();

		if (!matchFound) {
			FacesMessage message = new FacesMessage("Invalid email address, please check!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", message);
		}
	}
	
	public void cancel(){
		RequestContext.getCurrentInstance().reset("contactForm:messagePanel");  
	}
	
	public String send() {
		String emailDestinatario = "ander.dev@gmail.com";
		String emailRemetente = contactDTO.getEmail();
		String nomeRemetente = contactDTO.getName();
		String assuntoMensagem = "Contato realizado pelo Site.";
		String mensagem = contactDTO.getMessage();
		try {
			sendEmail(emailDestinatario, emailRemetente, nomeRemetente, assuntoMensagem, mensagem);
			FacesUtil.showSuccessMessage("Message sent successfully, ASAP I will get in contact.");
		} catch (UnsupportedEncodingException e) {
			FacesUtil.showSuccessMessage("UnsupportedEncodingException");
		} catch (MessagingException e) {
			FacesUtil.showSuccessMessage("MessagingException");
		}
		return "";
	}
	
	private void sendEmail(final String emailTo, final String emailFrom, final String nameFrom, final String subject, final String body) throws UnsupportedEncodingException, MessagingException {
		try {
			Properties props = System.getProperties();
			/** Parâmetros de conexão com servidor Gmail */
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("ander.dev@gmail.com", "hzvpkssayrajixxu");
				}
			};

			Session session = Session.getInstance(props, auth);
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(emailFrom,"Site Contact"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			StringBuilder sb = new StringBuilder();
			
			sb.append("Email sent by: ").append(nameFrom+" &lt;"+emailFrom+"&gt;");
			sb.append("<br/><br/> "+body);
			
			message.setSubject(subject);
			message.setContent(sb.toString(), "text/HTML");

			
			Transport.send(message);
		} catch (Exception e) {
			throw new UnsupportedEncodingException(e.getMessage());
		}
	}


	public PageView getPageView() {
		return pageView;
	}

	public void setPageView(PageView pageView) {
		this.pageView = pageView;
	}

	public PageViewBO getPageViewBO() {
		return pageViewBO;
	}

	public void setPageViewBO(PageViewBO pageViewBO) {
		this.pageViewBO = pageViewBO;
	}

	public ContactDTO getContactDTO() {
		return contactDTO;
	}

	public void setContactDTO(ContactDTO contactDTO) {
		this.contactDTO = contactDTO;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}


}
