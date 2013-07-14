package br.com.organizer.model.dto;

import java.sql.Blob;

public class PropagandaDTO {

	private Integer codigo;
	
	private String descricao;

	private String texto;
	
	private String tipo;
	
	private String idioma;
	
	private String idioma_usuario;
	
	private String ativa;
	
	private Blob imagem;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getAtiva() {
		return ativa;
	}

	public void setAtiva(String ativa) {
		this.ativa = ativa;
	}

	public Blob getImagem() {
		return imagem;
	}

	public void setImagem(Blob imagem) {
		this.imagem = imagem;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getIdioma_usuario() {
		return idioma_usuario;
	}

	public void setIdioma_usuario(String idiomaUsuario) {
		idioma_usuario = idiomaUsuario;
	}
}
