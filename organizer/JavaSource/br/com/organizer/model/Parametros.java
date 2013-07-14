package br.com.organizer.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="parametros")
public class Parametros implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer codigo;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Moeda.class)
	@JoinColumn(name = "moeda_codigo")
	@ForeignKey(name = "FK_P_MOEDA")
	private Moeda moeda;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = TipoFechamento.class)
	@JoinColumn(name = "tipofechamento_codigo")
	@ForeignKey(name = "FK_P_FECHAMENTO")
	private TipoFechamento tipoFechamento;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Usuario.class)
	@JoinColumn(name = "usuario_codigo")
	@ForeignKey(name = "FK_P_USUARIO")
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Parametros() {
		super();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Moeda getMoeda() {
		return moeda;
	}

	public void setMoeda(Moeda moeda) {
		this.moeda = moeda;
	}

	public TipoFechamento getTipoFechamento() {
		return tipoFechamento;
	}

	public void setTipoFechamento(TipoFechamento tipoFechamento) {
		this.tipoFechamento = tipoFechamento;
	}
}
