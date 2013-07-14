package br.com.organizer.util;

import java.sql.Clob;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.organizer.exception.OrganizerException;
import br.com.organizer.model.Usuario;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public abstract class Utils {

	public static Integer getDiasFevereiro(Date data) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		calendar.setTime(data);
		String strData = dataFormatada.format(calendar.getTime());

		String[] date2 = strData.split("/");

		calendar.set(Calendar.YEAR, Integer.parseInt(date2[2]));
		calendar.set(Calendar.MONTH, 1); // Fevereiro
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

	}
	
	public static Integer getDias(Date data) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		calendar.setTime(data);
		String strData = dataFormatada.format(calendar.getTime());

		String[] date2 = strData.split("/");

		if(Integer.parseInt(date2[0])==calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
			return diasMes(Integer.parseInt(date2[1]), data)-1;
		}else{
			return diasMes(Integer.parseInt(date2[1])-1, data)-1;
		}
	}

	public static String carregarConstante(Usuario usuario, String tipoConstante) {
		String retorno = "";
		if (tipoConstante.equals(Constantes.CREDITO)) {
			if (usuario.getIdioma().equals(Constantes.PORTUGUES)) {
				retorno = Constantes.TIPO_CREDITO_BR;
			} else {
				retorno = Constantes.TIPO_CREDITO_EN;
			}
		} else if (tipoConstante.equals(Constantes.DEBITO)) {
			if (usuario.getIdioma().equals(Constantes.PORTUGUES)) {
				retorno = Constantes.TIPO_DEBITO_BR;
			} else {
				retorno = Constantes.TIPO_DEBITO_EN;
			}
		} else if (tipoConstante.equals(Constantes.GRUPO)) {
			if (usuario.getIdioma().equals(Constantes.PORTUGUES)) {
				retorno = Constantes.TIPO_GRUPO_BR;
			} else {
				retorno = Constantes.TIPO_GRUPO_EN;
			}
		} else if (tipoConstante.equals(Constantes.SUPER_GRUPO)) {
			if (usuario.getIdioma().equals(Constantes.PORTUGUES)) {
				retorno = Constantes.TIPO_SUPER_GRUPO_BR;
			} else {
				retorno = Constantes.TIPO_SUPER_GRUPO_EN;
			}
		}
		return retorno;
	}

	public static String[] split(String splitPalavra, String splitPor) {
		String[] temp = null;
		temp = splitPalavra.split(splitPor);

		return temp;
	}

	public static Date stringToData(String data, Boolean mostraHora) {

		String[] date2 = data.split("/");
		Calendar calendar = new GregorianCalendar();

		if (!mostraHora) {
			calendar.set(Integer.valueOf(date2[2]),
					Integer.valueOf(date2[1]) - 1, Integer.valueOf(date2[0]),
					00, 00, 00);
		} else {
			calendar.set(Integer.valueOf(date2[2]),
					Integer.valueOf(date2[1]) - 1, Integer.valueOf(date2[0]));
		}
		return calendar.getTime();
	}

	public static String incrementaDiaNaData(String data) {
		String[] date2 = data.split("/");
		Calendar calendar = new GregorianCalendar();

		calendar.set(Integer.valueOf(date2[2]), Integer.valueOf(date2[1]) - 1,
				Integer.valueOf(date2[0]) + 1);

		SimpleDateFormat formarBRA = new SimpleDateFormat("dd/MM/yyyy");

		return formarBRA.format(calendar.getTime());
	}

	public static String dataToStringMySQL(Date dtData)
			throws java.text.ParseException {
		SimpleDateFormat formatSQL;
		SimpleDateFormat formarBRA;
		formatSQL = new SimpleDateFormat("yyyy-MM-dd");// Fomato do Banco
		formarBRA = new SimpleDateFormat("dd/MM/yyyy");

		try {
			Date dataSQL = formatSQL.parse(dtData.toString());

			return (formarBRA.format(dataSQL));
		} catch (ParseException Ex) {
			return "error_conversaoData";
		}
	}

	public static Boolean comparaData(Date dataDTO, Date dataBD) {
		java.sql.Date dataDTOSql = new java.sql.Date(dataDTO.getTime());
		java.sql.Date dataBDSql = new java.sql.Date(dataBD.getTime());
		if (dataDTOSql.equals(dataBDSql)) {
			return true;
		}
		return false;
	}

	public static Date dataToDataMySQL(Date dtData) {

		java.sql.Date dataSql = new java.sql.Date(dtData.getTime());

		return dataSql;
	}

	public static String dataToString(Date dtData) {
		SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
		return (dtData == null || dtData.equals("")) ? "" : DtFormat
				.format(dtData);
	}

	public static String dataToStringCartao(Date dtData) {
		SimpleDateFormat DtFormat = new SimpleDateFormat("MM/yyyy");
		return (dtData == null || dtData.equals("")) ? "" : DtFormat
				.format(dtData);
	}

	public static void validarEmail(String email) throws OrganizerException {
		Pattern p = Pattern
				.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
		Matcher m = p.matcher(email);
		if (!m.find()) {
			throw new OrganizerException("error_emailInvalido");
		}
	}

	public static boolean validarData(String dateStr)
			throws java.text.ParseException, OrganizerException {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = new GregorianCalendar();

		// gerando o calendar
		if (dateStr.length() == 10) {
			cal.setTime(df.parse(dateStr));
		} else {
			throw new OrganizerException("error_formatoData");
		}

		// separando os dados da string para comparacao e validacao
		String[] data = dateStr.split("/");
		String dia = data[0];
		String mes = data[1];
		String ano = data[2];

		// testando se hah discrepancia entre a data que foi
		// inserida no caledar e a data que foi passada como
		// string. se houver diferenca, a data passada sera
		// invalida
		if ((new Integer(dia)).intValue() != (new Integer(cal
				.get(Calendar.DAY_OF_MONTH))).intValue()) {
			// dia nao casou
			return (false);
		} else if ((new Integer(mes)).intValue() != (new Integer(cal
				.get(Calendar.MONTH) + 1)).intValue()) {
			// mes nao casou
			return (false);
		} else if ((new Integer(ano)).intValue() != (new Integer(cal
				.get(Calendar.YEAR))).intValue()) {
			// ano nao casou
			return (false);
		}
		return (true);
	}

	/**
	 * 1 - Valor a arredondar. 2 - Quantidade de casas depois da vírgula. 3 -
	 * Arredondar para cima ou para baixo? Para cima = 0 (ceil) Para baixo = 1
	 * ou qualquer outro inteiro (floor)
	 */
	public static Double arredondarValor(double valor, int casas,
			int ceilOrFloor) {
		double arredondado = valor;
		arredondado *= (Math.pow(10, casas));
		if (ceilOrFloor == 0) {
			arredondado = Math.ceil(arredondado);
		} else {
			arredondado = Math.floor(arredondado);
		}
		arredondado /= (Math.pow(10, casas));
		return arredondado;
	}

	/**
	 * Formata o valor informado como moeda, no formato ###,###,##0.00.
	 * 
	 * @param valor
	 *            O valor à ser formatado.
	 * @return Uma String formatada com a máscara ###,###,##0.00.
	 */

	public static String formataValor(Double valor) {
		DecimalFormatSymbols simbolosDecimais = new DecimalFormatSymbols();
		simbolosDecimais.setDecimalSeparator(',');
		simbolosDecimais.setGroupingSeparator('.');
		DecimalFormat df = new DecimalFormat("###,###,##0.00", simbolosDecimais);
		return df.format(valor);
	}
	
	public static Integer diasMes(Integer mes, Date data){
		Integer diasMes=0;
		switch (mes) {
			case 1:
				diasMes = 31;
				break;
			case 2:
				diasMes = Utils.getDiasFevereiro(data);
				break;
			case 3:
				diasMes = 31;
				break;
			case 4:
				diasMes = 30;
				break;
			case 5:
				diasMes = 31;
				break;
			case 6:
				diasMes = 30;
				break;
			case 7:
				diasMes = 31;
				break;
			case 8:
				diasMes = 31;
				break;
			case 9:
				diasMes = 30;
				break;
			case 10:
				diasMes = 31;
				break;
			case 11:
				diasMes = 30;
				break;
			case 12:
				diasMes = 31;
				break;
			}
		return diasMes;
	}
	
	public static String convertClobToString(Clob clob){
		String toRet="";
		if(clob!=null){
			try {
				long length=clob.length();
				toRet=clob.getSubString(1, (int)length);
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
		}
		return toRet;
	}
}
