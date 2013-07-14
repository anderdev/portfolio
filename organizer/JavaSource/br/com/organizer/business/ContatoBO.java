package br.com.organizer.business;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

public interface ContatoBO {

	public void enviarEmail(String emailDest, String emailRemet,
			String nomeRemet, String assunto, String corpo)
			throws UnsupportedEncodingException, MessagingException;

}
