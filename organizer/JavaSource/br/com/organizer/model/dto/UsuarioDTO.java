package br.com.organizer.model.dto;

import java.util.Date;


public class UsuarioDTO {

	private Integer codigo;

	private String nome;

	private String email;

	private Date dtnasc;
	
	private Date dtCadastro;
	
	private String administrador; 

	private String usuario;
	
	private String usuarioMaster;

	private String senha;
	
	private String confirmacao;
	
	private Integer estadoCodigo;
	
	private Integer cidadeCodigo;
	
	private Integer paisCodigo;
	
	private Integer masterCodigo;
	
	private String idioma;
	
	private String palavraSecreta;
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n Codigo: "+ getCodigo());
		builder.append("\n Idioma: "+ getIdioma());
		builder.append("\n MasterCodigo: "+ getMasterCodigo());
		builder.append("\n Usuario: "+ getUsuario());
		builder.append("\n Administrador: "+ getAdministrador());
		return builder.toString();
	}

	public String getPalavraSecreta() {
		return palavraSecreta;
	}

	public void setPalavraSecreta(String palavraSecreta) {
		this.palavraSecreta = palavraSecreta;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public Integer getMasterCodigo() {
		return masterCodigo;
	}

	public void setMasterCodigo(Integer masterCodigo) {
		this.masterCodigo = masterCodigo;
	}

	public Integer getPaisCodigo() {
		return paisCodigo;
	}

	public void setPaisCodigo(Integer paisCodigo) {
		this.paisCodigo = paisCodigo;
	}

	public String getAdministrador() {
		return administrador;
	}

	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}

	public Integer getEstadoCodigo() {
		return estadoCodigo;
	}

	public void setEstadoCodigo(Integer estadoCodigo) {
		this.estadoCodigo = estadoCodigo;
	}

	public Integer getCidadeCodigo() {
		return cidadeCodigo;
	}

	public void setCidadeCodigo(Integer cidadeCodigo) {
		this.cidadeCodigo = cidadeCodigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDtnasc() {
		return dtnasc;
	}

	public void setDtnasc(Date dtnasc) {
		this.dtnasc = dtnasc;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacao() {
		return confirmacao;
	}

	public void setConfirmacao(String confirmacao) {
		this.confirmacao = confirmacao;
	}

	public String getUsuarioMaster() {
		return usuarioMaster;
	}

	public void setUsuarioMaster(String usuarioMaster) {
		this.usuarioMaster = usuarioMaster;
	}

}
