package com.mconnti.moneymanager.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.mconnti.moneymanager.utils.Utils.*;

public class Main {
	
	public static void main(String[] args) {
//		testLastDayOfMonthWithCreditCardDate();
		getRangeOfDates(stringToDate("24/03/2014", false));
	}
	
	private static void getRangeOfDates(Date date){
		HashMap<String,String> range = loadDates(date,  Calendar.DAY_OF_MONTH, -getLastDayOfMonth(date));
		System.out.println(range);
	}
	
	
	@SuppressWarnings("unused")
	private static void testLastDayOfMonthWithCreditCardDate(){
		System.out.println("Test Last day of Month ");
		String jsonString = "2/2016";
		Date today = new Date();

		String strDate = "01/" + jsonString;
		today = stringToDate(strDate, false);

		String[] tmpDate = strDate.split("/");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(tmpDate[2]), Integer.valueOf(tmpDate[1]) - 1, Integer.valueOf(tmpDate[0]), 00, 00, 00);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		Date lastDayOfMonth = calendar.getTime();

		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("Today            : " + sdf.format(today));
		System.out.println("Last Day of Month: " + sdf.format(lastDayOfMonth));
		
		Date d = getCreditCardExpiredDate(jsonString);
		System.out.println("Mine Last Day of Month: " + sdf.format(d));
		
		System.out.println("Funciton - Last day of Month: "+getLastDayOfMonth(lastDayOfMonth));
	}

}
