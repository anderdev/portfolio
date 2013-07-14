package br.com.organizer.exception;

public class OrganizerException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String mensagem;

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public OrganizerException(String msg) {
		super(msg);		
		setMensagem(msg);
	}

}
