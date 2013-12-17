package com.mconnti.moneymanager.util;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class MessageFactory {
	private static ResourceBundle bundle;
	private static Locale locale;
	
	public static String getMessage(String key, String language) {
		if(language == null || !"pt_BR".equals(language)){
			language = "en";
		}
		locale = new Locale(language);
    	bundle = ResourceBundle.getBundle("com.mconnti.moneymanager.web.bundle.Messages", locale);
		return bundle.getString(key);
	}
}