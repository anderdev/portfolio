package br.com.organizer.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class FacesUtils {


    public static void mensInfo(String message) {
        mensagem(message, FacesMessage.SEVERITY_INFO);
    }


    public static void mensErro(String message) {
        mensagem(message, FacesMessage.SEVERITY_ERROR);
    }

    public static void mensagem(String message, 
    		FacesMessage.Severity severity) {
    	
        FacesContext.getCurrentInstance().
        	addMessage(null, new FacesMessage(severity, message, null));
    }
    
    public static String get(String param) {
    	
    	return (String) FacesContext.getCurrentInstance().
			getExternalContext().
			getRequestParameterMap().get(param);
    }   
    
    public static ServletContext getServletContext() {
		return (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
	}

}
