package com.mconnti.selfmanager.entity;

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
@Table(name = "credito")
public class Credito implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer codigo;

	@NotNull
	private Date data;

	@NotNull
	private String descricao;

	private String grupo;

	@NotNull
	private String superGrupo;

	@NotNull
	private String moeda;

	@NotNull
	@Column(length = 13, precision = 13, scale = 2)
	private Double valor;

	private Integer aux;

	private Boolean contabilizado;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Usuario.class)
	@JoinColumn(name = "usuario_codigo")
	@ForeignKey(name = "FK_CRE_USUARIO")
	private Usuario usuario;
	
	public Credito() {
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n Codigo: " + getCodigo());
		builder.append("\n Data: " + getData());
		builder.append("\n Descrição: " + getDescricao());
		builder.append("\n Grupo: " + getGrupo());
		builder.append("\n SuperGrupo: " + getSuperGrupo());
		builder.append("\n Moeda: " + getMoeda());
		builder.append("\n Contabilizado: " + getContabilizado());
		builder.append("\n Usuario: " + getUsuario());
		return builder.toString();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMoeda() {
		return moeda;
	}

	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getAux() {
		return aux;
	}

	public void setAux(Integer aux) {
		this.aux = aux;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Boolean getContabilizado() {
		return contabilizado;
	}

	public void setContabilizado(Boolean contabilizado) {
		this.contabilizado = contabilizado;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getSuperGrupo() {
		return superGrupo;
	}

	public void setSuperGrupo(String superGrupo) {
		this.superGrupo = superGrupo;
	}
}
