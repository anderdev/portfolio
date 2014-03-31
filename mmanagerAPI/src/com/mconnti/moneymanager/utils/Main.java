package com.mconnti.moneymanager.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.mconnti.moneymanager.entity.User;

import static com.mconnti.moneymanager.utils.Utils.*;

public class Main {
	
	public static void main(String[] args) throws Exception {
//		testLastDayOfMonthWithCreditCardDate();
//		getRangeOfDates(stringToDate("24/03/2014", false));
		User user = new User();
		user.setName("<b>Bruno Wunsche</b>");
		user.setSuperUser(new User());
		user.getSuperUser().setName("Anderson Santos");
		user.getSuperUser().setSecretPhrase("Vai funcionar de prima");
		user.setUsername("bruno.wunsche");
		user.setPassword("123456");
		StringBuilder body = new StringBuilder();
		body.append("Olá ").append(user.getName()).append("\n");
		body.append("<b>").append(user.getSuperUser().getName()).append("</b>, lhe cadastrou no Momey Manager como coparticipante nos cadastros de créditos e despesas de seu controle financeiro.\n");
		body.append("No seu primeiro acesso será solicitado a frase secreta registrada pelo <b>").append(user.getSuperUser().getName()).append("</b>\n");
		body.append("Frase: <b>").append(user.getSuperUser().getSecretPhrase()).append("</b>\n");
		body.append("Para fazer o primeiro acesso você terá que se logar no sistema com:").append("\n");
		body.append("Usuário: <b>").append(user.getUsername()).append("</b>\n");
		body.append("Senha: <b>").append(Crypt.decrypt(user.getPassword())).append("</b>\n");
		body.append("Link: <b>").append("www.andersantos.com/mmanager").append("</b>\n");
		body.append("Na próxima tela lhe será solitado para cadastrar nova senha e informar a frase secreta cadastrada pelo usuário <b>").append(user.getSuperUser().getName()).append("</b>\n\n");
		body.append("Obrigado pela atenção,").append("\n");
		body.append("<b>Equipe Money Manager</b>");
		
		System.out.println(body.toString());
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
