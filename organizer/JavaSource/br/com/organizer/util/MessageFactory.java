package br.com.organizer.util;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public abstract class MessageFactory {
	private static ResourceBundle bundle;
	private static Locale locale;
	
	public static String getMessage(String key) {
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    	bundle = ResourceBundle.getBundle("br.com.organizer.web.bundle.Messages", locale);
		return bundle.getString(key);
	}
}