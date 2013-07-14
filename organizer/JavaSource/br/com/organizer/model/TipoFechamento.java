package br.com.organizer.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

@Entity
@Table(name="tipofechamento")
public class TipoFechamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer codigo;
	
	@NotNull
	private String tipoFechamento;
	
	@NotNull
	private String idioma;
	
	@NotNull
	private Integer periodo;
	
	public String getTipoFechamento() {
		return tipoFechamento;
	}

	public void setTipoFechamento(String tipoFechamento) {
		this.tipoFechamento = tipoFechamento;
	}

	public TipoFechamento() {
		super();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
	
	public String toString() {
		String result = this.tipoFechamento;
		return result;
	}

}
