package br.com.organizer.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.NotNull;

@Entity
@Table(name="estado")
public class Estado implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer codigo;
	
	@NotNull
	private String estado;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "estado_codigo")
	@ForeignKey(name = "FK_EST_CIDADE")
	private Set<Cidade> cidadesCodigo;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Pais.class)
	@JoinColumn(name = "pais_codigo")
	@ForeignKey(name = "FK_EST_PAIS")
	private Pais paisCodigo;
	
	public Set<Cidade> getCidadesCodigo() {
		return cidadesCodigo;
	}

	public void setCidadesCodigo(Set<Cidade> cidadesCodigo) {
		this.cidadesCodigo = cidadesCodigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Pais getPaisCodigo() {
		return paisCodigo;
	}

	public void setPaisCodigo(Pais paisCodigo) {
		this.paisCodigo = paisCodigo;
	}

	public Estado() {
		super();
	}

}
