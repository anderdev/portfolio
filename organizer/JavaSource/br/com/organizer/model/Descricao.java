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
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "descricao")
public class Descricao implements Serializable {

	private static final long serialVersionUID = 7999831669893410987L;

	@Id
	@GeneratedValue
	private Integer codigo;

	@NotNull
	private String descricao;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Conta.class)
	@JoinColumn(name = "tipo_conta")
	@ForeignKey(name = "FK_TP_CONTA")
	private Conta tipoConta;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Usuario.class)
	@JoinColumn(name = "usuario_codigo")
	@ForeignKey(name = "FK_TP_USUARIO")
	private Usuario usuario;

	public Conta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(Conta tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

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

	public String toString() {
		String result = this.descricao;
		return result;
	}

}
