package com.mconnti.moneymanager.util;

import java.text.Normalizer;

public abstract class Utils {
	
	public static String clearString(String string){
		String retString = Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return retString;
	}
}
