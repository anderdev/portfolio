package com.mconnti.moneymanager.util;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class MessageFactory {
	private static ResourceBundle bundle;
	private static Locale locale;
	
	public static String getMessage(String key, String language, Object params[]) {
		if(language == null || !"pt_BR".equals(language)){
			language = "en";
		}
		locale = new Locale(language);
    	bundle = ResourceBundle.getBundle("com.mconnti.moneymanager.web.bundle.messages", locale);
    	if(params == null){
    		return bundle.getString(key);
    	}else{
    		return MessageFormat.format(bundle.getString(key), params);
    	}
	}
}