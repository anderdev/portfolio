package br.com.organizer.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name="cartao")
public class Cartao implements Serializable {

	private static final long serialVersionUID = -3160404156422105911L;
	
	@Id
	@GeneratedValue
	private Integer codigo;
	
	@NotNull
	private String descricao;
	
	@NotNull
	@Column(name="vencimento")
	private Integer diaVencimento;
	
	@NotNull
	@Column(name="compra")
	private Integer diaCompra;
	
	@NotNull
	@Column(name="expira")
	private Date dtExpira;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Usuario.class)
	@JoinColumn(name = "usuario_codigo")
	@ForeignKey(name = "FK_CA_USUARIO")
	private Usuario usuario;
	
	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Usuario.class)
	@JoinColumn(name = "master_codigo")
	@ForeignKey(name = "FK_CA_MASTER_USUARIO")
	private Usuario masterUsuario;

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

	public Integer getDiaVencimento() {
		return diaVencimento;
	}

	public void setDiaVencimento(Integer diaVencimento) {
		this.diaVencimento = diaVencimento;
	}

	public Integer getDiaCompra() {
		return diaCompra;
	}

	public void setDiaCompra(Integer diaCompra) {
		this.diaCompra = diaCompra;
	}

	public Date getDtExpira() {
		return dtExpira;
	}

	public void setDtExpira(Date dtExpira) {
		this.dtExpira = dtExpira;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getMasterUsuario() {
		return masterUsuario;
	}

	public void setMasterUsuario(Usuario masterUsuario) {
		this.masterUsuario = masterUsuario;
	}
	
	public String toString() {
		String result = this.descricao;
		return result;
	}

}
