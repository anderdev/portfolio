package com.mconnti.moneymanager.utils;

import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public abstract class Utils {
	
	public static int setBooleanValue(Boolean value){
		if(value){
			return 1;
		}else {
			return 0;
		}
	}
	
	public static String clearString(String string){
		String retString = Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return retString;
	}

	public static Calendar stringToDate(String data, Boolean mostraHora) {

		String[] date2 = data.split("/");
		Calendar calendar = new GregorianCalendar();

		if (!mostraHora) {
			calendar.set(Integer.valueOf(date2[2]), Integer.valueOf(date2[1]) - 1, Integer.valueOf(date2[0]), 00, 00, 00);
		} else {
			calendar.set(Integer.valueOf(date2[2]), Integer.valueOf(date2[1]) - 1, Integer.valueOf(date2[0]));
		}
		return calendar;
	}

	public static String dateToString(Calendar dtData) {
		
		Calendar calendar = dtData;
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    String ret = sdf.format(calendar.getTime());
		
//		SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
//		return (dtData == null || dtData.equals("")) ? "" : DtFormat.format(dtData);
	    return ret;
	}

	public static String dateToStringCreditCard(Date dtData) {
		SimpleDateFormat DtFormat = new SimpleDateFormat("MM/yyyy");
		return (dtData == null || dtData.equals("")) ? "" : DtFormat.format(dtData);
	}

	public static Calendar getCreditCardExpiredDate(String jsonString) {
		String strDate = "01/" + jsonString;

		String[] tmpDate = strDate.split("/");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(tmpDate[2]), Integer.valueOf(tmpDate[1]) - 1, Integer.valueOf(tmpDate[0]), 00, 00, 00);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		return calendar;
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
	
	public static void sendEmail(final String emailTo, final String emailFrom, final String nameFrom, final String subject, final String body) throws UnsupportedEncodingException, MessagingException {
		try {
			Properties props = System.getProperties();
			/** Par�metros de conex�o com servidor Gmail */
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("ander.dev@gmail.com", "hzvpkssayrajixxu");
				}
			};

			Session session = Session.getInstance(props, auth);
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(emailFrom,"Site Contact"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			StringBuilder sb = new StringBuilder();
			
			sb.append("Email sent by: ").append(nameFrom+" &lt;"+emailFrom+"&gt;");
			sb.append("<br/><br/> "+body);
			
			message.setSubject(subject, "UTF-8");
			message.setContent(sb.toString(), "text/HTML; charset=UTF-8");

			
			Transport.send(message);
		} catch (Exception e) {
			throw new UnsupportedEncodingException(e.getMessage());
		}
	}
}
