package com.mconnti.moneymanager.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class Utils {

	public static void main(String[] args) {
		
		String jsonString = "11/2015";
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
		
		Date d = lastDayMonth(jsonString);
		System.out.println("Mine Last Day of Month: " + sdf.format(d));
	}

	public static Date stringToDate(String data, Boolean mostraHora) {

		String[] date2 = data.split("/");
		Calendar calendar = new GregorianCalendar();

		if (!mostraHora) {
			calendar.set(Integer.valueOf(date2[2]), Integer.valueOf(date2[1]) - 1, Integer.valueOf(date2[0]), 00, 00, 00);
		} else {
			calendar.set(Integer.valueOf(date2[2]), Integer.valueOf(date2[1]) - 1, Integer.valueOf(date2[0]));
		}
		return calendar.getTime();
	}

	public static String dateToString(Date dtData) {
		SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
		return (dtData == null || dtData.equals("")) ? "" : DtFormat.format(dtData);
	}

	public static Date lastDayMonth(String jsonString) {
		String strDate = "01/" + jsonString;

		String[] tmpDate = strDate.split("/");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(tmpDate[2]), Integer.valueOf(tmpDate[1]) - 1, Integer.valueOf(tmpDate[0]), 00, 00, 00);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		Date lastDayOfMonth = calendar.getTime();
		
		return lastDayOfMonth;
	}

}
