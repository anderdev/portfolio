package br.com.organizer.model.dto;



public class LoginDTO {

	private String user;

	private String password;
	
	private String novaSenha;
	
	private String novaSenhaConfirmacao;
	
	private String novaSenhaPalavraSecreta;

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getNovaSenhaConfirmacao() {
		return novaSenhaConfirmacao;
	}

	public void setNovaSenhaConfirmacao(String novaSenhaConfirmacao) {
		this.novaSenhaConfirmacao = novaSenhaConfirmacao;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNovaSenhaPalavraSecreta() {
		return novaSenhaPalavraSecreta;
	}

	public void setNovaSenhaPalavraSecreta(String novaSenhaPalavraSecreta) {
		this.novaSenhaPalavraSecreta = novaSenhaPalavraSecreta;
	}
}
