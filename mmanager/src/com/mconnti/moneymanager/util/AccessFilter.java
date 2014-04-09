package com.mconnti.moneymanager.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mconnti.moneymanager.entity.User;

@WebFilter(
		urlPatterns = {"/common/*","/include/*"},
		filterName = "AccessFilter",
		description = "Filter to restrict access to pages without user been logged in."  
		)
public class AccessFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		User loggedUser = (User) session.getAttribute("loggedUser");

		if (loggedUser != null) {
			try {
				chain.doFilter(request, response);
			} catch (javax.faces.application.ViewExpiredException e) {
				// try {
				// // contato.enviarEmail("ander.dev@gmail.com", "organizer@gmail.com", "Organizer", "Exce��o Gerada", e.getMessage());
				// System.out.println("send email!");
				// } catch (MessagingException e1) {
				// e1.printStackTrace();
				// // logger.warn("Enviar e-mail ViewExpiredException: " + e1.getMessage());
				// }
				System.out.println("send email - EXPIRED EXCEPTION!");
				((HttpServletResponse) response).sendRedirect("../expired.jsf");
			} catch (Exception e) {
				// try {
				// // contato.enviarEmail("ander.dev@gmail.com", "organizer@gmail.com", "Organizer", "Exce��o Gerada", e.getMessage());
				// System.out.println("send email!");
				// } catch (MessagingException e1) {
				// // logger.warn("Enviar e-mail Exception: " + e1.getMessage());
				// e1.printStackTrace();
				// }
				System.out.println("send email - UNCAUGHT EXCEPTION!");
				((HttpServletResponse) response).sendRedirect("uncaughtException.jsf");
			}
		} else {
			System.out.println("necessary login");
			// envia uma mensagem caso o usu�rio
			// n�o tenha se logado
			// redireciona para a p�gina de login
			((HttpServletResponse) response).sendRedirect("../loginRequired.jsf");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// nothing todo
	}

	@Override
	public void destroy() {
		// nothing todo
	}
}
