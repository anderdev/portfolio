package br.com.organizer.util;

import java.util.Date;

public class ClasseDeTeste {
	
	public static void main(String[] args) {
		Date data = new Date();
		System.out.println(Utils.getDias(data));
	}

	// public static void main(String[] args) throws InvalidKeyException,
	// BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException,
	// NoSuchAlgorithmException, InvalidAlgorithmParameterException {
	//		
	// UsuarioController controller = new UsuarioController();
	//		
	// Usuario usuario = new Usuario();
	// usuario.setNome("Anderson Corr a dos Santos");
	// usuario.setAdministrador(true);
	//		
	// controller.salvar();
	//		
	// Calendar calendar = Calendar.getInstance();
	//		
	// Date data = new Date(0);
	//		
	// System.out.println("Calendar Date: "+calendar.getTime());
	//		
	// System.out.println("SQL Date: "+data);
	//		
	// data.setTime(calendar.getTimeInMillis());
	//		
	// System.out.println("Calendar to SQL Date: "+data);
	//		
	//
	// }
}
