package com.mconnti.moneymanager.util;

import java.math.BigDecimal;
import java.text.Normalizer;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("STARTING");
		BigDecimal value = new BigDecimal(0.00);
		System.out.println("value: "+value);
		
		value = value.add(new BigDecimal(500.50));
		System.out.println("value = 500.50: "+value);
		
		BigDecimal minusValue = new BigDecimal(0.00);
		minusValue = minusValue.add(new BigDecimal(550.50));
		System.out.println("minusValue = 550.50: "+minusValue);
		
		BigDecimal result = value.subtract(minusValue);
		System.out.println("Result: "+new BigDecimal(result.toString().split("-")[1]));
		
		System.out.println(Normalizer.normalize("חצאטי", Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
	}
}
