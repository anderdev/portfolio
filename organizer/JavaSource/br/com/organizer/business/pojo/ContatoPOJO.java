package br.com.organizer.business.pojo;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import br.com.organizer.business.ContatoBO;

public class ContatoPOJO implements ContatoBO {
	
	public Logger logger = Logger.getLogger(ContatoPOJO.class);

	public ContatoPOJO() {
		super();
	}

	public void enviarEmail(String emailDest, String emailRemet, String nomeRemet, String assunto, String corpo) throws UnsupportedEncodingException, MessagingException {
		try {
			Properties props = System.getProperties();
			/** Parâmetros de conexão com servidor Gmail */
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

			// Config mail Integrator
//			props.put("mail.smtp.host", "smtp.mconnti.com");
//			props.put("mail.smtp.auth", "true");

			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("ouvidoria@mconnti.com", "master");
				}
			};

			Session session = Session.getInstance(props, auth);
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(emailRemet, nomeRemet));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDest));

			message.setSubject(assunto);
			message.setContent(corpo, "text/HTML");

			Transport.send(message);
		} catch (Exception e) {
			throw new UnsupportedEncodingException(e.getMessage());
		}
	}

}
