package br.com.organizer.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.NotNull;

@Entity
@Table(name="parcela")
public class Parcela implements Serializable {

	private static final long serialVersionUID = -3160404156422105911L;
	
	@Id
	@GeneratedValue
	private Integer codigo;
	
	@NotNull
	@Column(name="parcelas")
	private Integer parcelas;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Usuario.class)
	@JoinColumn(name = "usuario_codigo")
	@ForeignKey(name = "FK_PA_USUARIO")
	private Usuario usuario;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}

}
