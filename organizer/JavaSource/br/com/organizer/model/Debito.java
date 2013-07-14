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
@Table(name = "debito")
public class Debito implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer codigo;

	@NotNull
	private Date data;

	@NotNull
	private String descricao;

	@NotNull
	private String grupo;

	private String superGrupo;

	@NotNull
	private String moeda;

	private String tipo;

	@NotNull
	@Column(length = 13, precision = 13, scale = 2)
	private Double valor;

	@Column(name = "parcela_codigo")
	private Integer parcelaCodigo;

	private Boolean contabilizado;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Cartao.class)
	@JoinColumn(name = "cartao_codigo")
	@ForeignKey(name = "FK_DE_CARTAO")
	private Cartao cartao;

	@ManyToOne(cascade = { CascadeType.PERSIST }, targetEntity = Usuario.class)
	@JoinColumn(name = "usuario_codigo")
	@ForeignKey(name = "FK_DE_USUARIO")
	private Usuario usuario;
	
	public Debito() {
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n Codigo: " + getCodigo());
		builder.append("\n Data: " + getData());
		builder.append("\n Descrição: " + getDescricao());
		builder.append("\n Grupo: " + getGrupo());
		builder.append("\n SuperGrupo: " + getSuperGrupo());
		builder.append("\n Moeda: " + getMoeda());
		builder.append("\n Tipo: " + getTipo());
		builder.append("\n ParcelaCodigo: " + getParcelaCodigo());
		builder.append("\n Contabilizado: " + getContabilizado());
		builder.append("\n Cartão: " + getCartao());
		builder.append("\n Usuario: " + getUsuario());
		return builder.toString();
	}

	public Integer getParcelaCodigo() {
		return parcelaCodigo;
	}

	public void setParcelaCodigo(Integer parcelaCodigo) {
		this.parcelaCodigo = parcelaCodigo;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}

	public String getSuperGrupo() {
		return superGrupo;
	}

	public void setSuperGrupo(String superGrupo) {
		this.superGrupo = superGrupo;
	}
}
