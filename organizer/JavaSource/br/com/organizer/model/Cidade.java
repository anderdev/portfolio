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
@Table(name="cidade")
public class Cidade implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer codigo;
	
	@NotNull
	private String cidade;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Estado.class)
	@JoinColumn(name = "estado_codigo")
	@ForeignKey(name = "FK_CI_ESTADO")
	private Estado estadoCodigo;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "cidade_codigo")
	@ForeignKey(name = "FK_CI_USUARIO")
	private Set<Usuario> usuariosCodigo;

	public Cidade() {
		super();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Estado getEstadoCodigo() {
		return estadoCodigo;
	}

	public void setEstadoCodigo(Estado estadoCodigo) {
		this.estadoCodigo = estadoCodigo;
	}

	public Set<Usuario> getUsuariosCodigo() {
		return usuariosCodigo;
	}

	public void setUsuariosCodigo(Set<Usuario> usuariosCodigo) {
		this.usuariosCodigo = usuariosCodigo;
	}

}
