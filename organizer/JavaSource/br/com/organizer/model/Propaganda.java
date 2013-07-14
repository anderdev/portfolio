package br.com.organizer.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

@Entity
@Table(name="propaganda")
public class Propaganda implements Serializable {

	private static final long serialVersionUID = -3160404156422105911L;
	
	@Id
	@GeneratedValue
	private Integer codigo;
	
	@NotNull
	private String descricao;
	
	@NotNull
	@Lob
	private Clob texto;
	
	@NotNull
	private String tipo;
	
	@NotNull
	private Boolean ativa;
	
	@NotNull
	private String idioma;
	
	@NotNull
	private String idioma_usuario;
	
	@Lob
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

	public Clob getTexto() {
		return texto;
	}

	public void setTexto(Clob texto) {
		this.texto = texto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getAtiva() {
		return ativa;
	}

	public void setAtiva(Boolean ativa) {
		this.ativa = ativa;
	}

	public Blob getImagem() {
		return imagem;
	}

	public void setImagem(Blob imagem) {
		this.imagem = imagem;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Código: "+this.codigo);
		str.append(" - Descrição: "+this.descricao);
		str.append(" - Texto: "+this.texto);
		str.append(" - Tipo: "+this.tipo);
		str.append(" - Ativa: "+this.ativa);
		return str.toString();
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
