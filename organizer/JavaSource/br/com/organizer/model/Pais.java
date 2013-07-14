package br.com.organizer.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.NotNull;

@Entity
@Table(name="pais")
public class Pais implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer codigo;
	
	@NotNull
	private String pais;
	
	private String locale;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "pais_codigo")
	@ForeignKey(name = "FK_PAIS_ESTADO")
	private Set<Estado> estados;
	
	public Set<Estado> getEstados() {
		return estados;
	}

	public void setEstados(Set<Estado> estados) {
		this.estados = estados;
	}
	
	public Pais() {
		super();
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
	
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

}
