package com.mconnti.moneymanager.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public abstract class Utils {

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

	public static String dateToStringCreditCard(Date dtData) {
		SimpleDateFormat DtFormat = new SimpleDateFormat("MM/yyyy");
		return (dtData == null || dtData.equals("")) ? "" : DtFormat.format(dtData);
	}

	public static Date getCreditCardExpiredDate(String jsonString) {
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

	public static Integer getLastDayOfMonth(Date date) {
		Calendar testCalendar = Calendar.getInstance();
		testCalendar.setTime(date);
		testCalendar.add(Calendar.DAY_OF_MONTH, 1);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (testCalendar.get(Calendar.DAY_OF_MONTH) == 1) {
			calendar.add(Calendar.MONTH, 1);
		} else {
			calendar.add(Calendar.MONTH, 0);
		}

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static HashMap<String, String> loadDates(Date date, Integer type, Integer days) {
		HashMap<String, String> map = new HashMap<String, String>();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, days + 1);
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		String startDate = dataFormatada.format(calendar.getTime());
		calendar.setTime(date);
		String endDate = dataFormatada.format(calendar.getTime());

		map.put(Constants.DATE_START, startDate);
		map.put(Constants.DATE_END, endDate);
		return map;
	}
}
