package com.mconnti.moneymanager.utils;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class MessageFactory {
	private static ResourceBundle bundle;
	private static Locale locale;
	
	public static String getMessage(String key, String language) {
		if(language == null || !"pt_br".equals(language.toLowerCase())){
			language = "en";
		}
		locale = new Locale(language.toLowerCase());
    	bundle = ResourceBundle.getBundle("com.mconnti.moneymanager.bundle.Messages", locale);
		return bundle.getString(key);
	}
}