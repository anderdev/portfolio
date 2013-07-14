package br.com.organizer.model.dto;

public class LocaleDTO {
	
	private Integer codigo;
	
	private String pais;
	
	private String estado;
	
	private String cidade;
	
	private Integer paisCodigo;
	
	private Integer estadoCodigo;
	
	private String Locale;
	
	private String tipoLocale;
	
	public LocaleDTO() {
		super();
	}

	public String getTipoLocale() {
		return tipoLocale;
	}

	public void setTipoLocale(String tipoLocale) {
		this.tipoLocale = tipoLocale;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Integer getPaisCodigo() {
		return paisCodigo;
	}

	public void setPaisCodigo(Integer paisCodigo) {
		this.paisCodigo = paisCodigo;
	}

	public Integer getEstadoCodigo() {
		return estadoCodigo;
	}

	public void setEstadoCodigo(Integer estadoCodigo) {
		this.estadoCodigo = estadoCodigo;
	}

	public String getLocale() {
		return Locale;
	}

	public void setLocale(String locale) {
		Locale = locale;
	}
}
