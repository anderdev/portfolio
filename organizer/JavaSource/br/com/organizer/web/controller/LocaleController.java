package br.com.organizer.web.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.organizer.model.Usuario;

@Component("localeController")
@Scope("session")
public class LocaleController implements Serializable{
	
	private static final long serialVersionUID = 1L;

	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	
	private String locale;
	
	private String calendarLocale;
	
	Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); 
	
	public LocaleController() {
		setLocale(configuraLanguage()); 
	}
	
	public String configuraLanguage() {
		String language = null;
		
		if(usuarioLogado!=null){
			language = usuarioLogado.getIdioma().equals("portugues")?"pt_BR":"en";
		}else{
			language = (String) session.getAttribute("language");
		}
		
		if(language==null){
			language = fc.getExternalContext().getRequestLocale().toString();
		}
		
		if(!language.equals("pt_BR")){
			language = "en";
		}
		
		session.setAttribute("language", language);
		
		return language;
	}
	
	public String mudaPortugues() {
		setLocale("pt_BR");
		session.setAttribute("language", getLocale());
		new UsuarioController().getListaPaises(getLocale());
		return "index";
	}
	
	public String mudaIngles() {
		setLocale("en");
		session.setAttribute("language", getLocale());
		new UsuarioController().getListaPaises(getLocale());
		return "index";
	}
	
	public String getCalendarLocale(){
		if(getLocale().equals("pt_BR")){
			this.calendarLocale = "pt";
		}else{
			this.calendarLocale = "en";
		}
		return this.calendarLocale;
	}

	
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
}
