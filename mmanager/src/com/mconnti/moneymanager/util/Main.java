package com.mconnti.moneymanager.util;

import java.math.BigDecimal;

public class Main {
	
	public static void main(String[] args) {
		BigDecimal test = new BigDecimal(0.00);
		System.out.println(test);
		BigDecimal result = test.add(new BigDecimal(9999999.99));
		System.out.println(result);
		result = result.add(new BigDecimal(0));
		result = result.multiply(new BigDecimal(3)).divide(new BigDecimal(10));
		System.out.println(result);
		result = result.divide(new BigDecimal(3)).multiply(new BigDecimal(10));
		System.out.println(result);
	}
}
