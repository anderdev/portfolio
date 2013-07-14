package br.com.organizer.model.dto;

import br.com.organizer.model.Usuario;

public class ParametrosDTO {

	private Integer codigo;
	
	private Integer usuarioCodigo;
	
	private String sigla;

	private String moeda;

	private String tipoFechamento;
	
	private String idioma;
	
	private Integer periodo;
	
	private String tipoConfig;
	
	private String descricaoMoeda;
	
	private String descricaoTipoFechamento;
	
	private Usuario usuario;
	
	public String getTipoConfig() {
		return tipoConfig;
	}

	public void setTipoConfig(String tipoConfig) {
		this.tipoConfig = tipoConfig;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getMoeda() {
		return moeda;
	}

	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}

	public String getTipoFechamento() {
		return tipoFechamento;
	}

	public void setTipoFechamento(String tipoFechamento) {
		this.tipoFechamento = tipoFechamento;
	}

	public Integer getUsuarioCodigo() {
		return usuarioCodigo;
	}

	public void setUsuarioCodigo(Integer usuarioCodigo) {
		this.usuarioCodigo = usuarioCodigo;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricaoMoeda() {
		return descricaoMoeda;
	}

	public void setDescricaoMoeda(String descricaoMoeda) {
		this.descricaoMoeda = descricaoMoeda;
	}

	public String getDescricaoTipoFechamento() {
		return descricaoTipoFechamento;
	}

	public void setDescricaoTipoFechamento(String descricaoTipoFechamento) {
		this.descricaoTipoFechamento = descricaoTipoFechamento;
	}
}
