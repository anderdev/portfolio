package br.com.organizer.util;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.organizer.business.pojo.ContatoPOJO;
import br.com.organizer.model.Usuario;

public class SegurancaFiltro implements Filter {

	public Logger logger = Logger.getLogger(SegurancaFiltro.class);
	
	ContatoPOJO contato = new ContatoPOJO();

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		logger.warn("Seguran�a Filtro iniciado");
		HttpSession sessao = ((HttpServletRequest) request).getSession();

		// tenta capturar a sess�o chamada user
		Usuario logado = (Usuario) sessao.getAttribute("usuarioLogado");
		logger.warn("UsuarioLogado: " + logado);

		// verifica se a sess�o existe
		if (logado != null) {
			try {
				chain.doFilter(request, response);
			} catch (javax.faces.application.ViewExpiredException e) {
				try {
					contato.enviarEmail("ander.dev@gmail.com", "organizer@gmail.com", "Organizer", "Exce��o Gerada", e.getMessage());
				} catch (MessagingException e1) {
					logger.warn("Enviar e-mail ViewExpiredException: " + e1.getMessage());
				}
				((HttpServletResponse) response).sendRedirect("index.jsf");
			} catch (Exception e) {
				try {
					contato.enviarEmail("ander.dev@gmail.com", "organizer@gmail.com", "Organizer", "Exce��o Gerada", e.getMessage());
				} catch (MessagingException e1) {
					logger.warn("Enviar e-mail Exception: " + e1.getMessage());
				}
				((HttpServletResponse) response).sendRedirect("uncaughtException.jsf");
			}
		} else {
			
			// envia uma mensagem caso o usu�rio
			// n�o tenha se logado
			sessao.setAttribute("loginErro", "error_loginNecessario");

			// redireciona para a p�gina de login
			((HttpServletResponse) response).sendRedirect("index.jsf");
		}
		logger.warn("Seguran�a Filtro Finalizado");
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
