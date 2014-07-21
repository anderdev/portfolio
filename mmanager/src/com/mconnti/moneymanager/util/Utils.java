package com.mconnti.moneymanager.util;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class Utils {
	
	public static String clearString(String string){
		String retString = Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return retString;
	}
	
	public static Boolean compareEqualDates(Date today, Date register){
		SimpleDateFormat datePattern = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(today);
		String strToday = datePattern.format(calendar.getTime());
		
		calendar.setTime(register);
		String strDateRegister = datePattern.format(calendar.getTime());
		
		if(strToday.equals(strDateRegister)){
			return true;
		}
		return false;
	}
}
