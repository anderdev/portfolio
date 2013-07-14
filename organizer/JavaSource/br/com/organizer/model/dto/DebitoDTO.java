package br.com.organizer.model.dto;

import java.util.Date;

import br.com.organizer.model.Cartao;
import br.com.organizer.model.Usuario;

public class DebitoDTO {
	
	private Integer codigo;

	private Date data;
	
	private Date dataAtual;

	private String descricao;
	
	private String grupo;
	
	private String superGrupo;
	
	private String descricaoConsulta;
	
	private String grupoConsulta;
	
	private String superGrupoConsulta;
	
	private String cartaoConsulta;
	
	private String descricaoCartao;

	private String moeda;

	private String valor;
	
	private String tipo;
	
	private Integer parcelas;
	
	private Integer parcelaCodigo;
	
	private String origemTela;

	private Integer aux;
	
	private Integer usuarioCodigo;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private String tipoConsulta;
	
	private Usuario usuario;
	
	private Boolean usaData;
	
	private Cartao cartao;
	
	private Cartao cartaoAtual;
	
	private Boolean todosCartoes = false;
	
	private Boolean contabilizado;
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n Codigo: "+ getCodigo());
		builder.append("\n Data: " + getData());
		builder.append("\n Descrição: " + getDescricao());
		builder.append("\n Grupo: " + getGrupo());
		builder.append("\n SuperGrupo: " + getSuperGrupo());
		builder.append("\n Moeda: " + getMoeda());
		builder.append("\n Tipo: " + getTipo());
		builder.append("\n UsaData: "+ getUsaData());
		builder.append("\n ParcelaCodigo: "+ getParcelaCodigo());
		builder.append("\n Parcelas: "+ getParcelas());
		builder.append("\n Contabilizado: "+ getContabilizado());
		builder.append("\n Todos Cartões: "+ getTodosCartoes());
		builder.append("\n Cartão: "+ getCartao());
		builder.append("\n Usuario: "+ getUsuario());
		return builder.toString();
	}

	public Integer getParcelaCodigo() {
		return parcelaCodigo;
	}

	public void setParcelaCodigo(Integer parcelaCodigo) {
		this.parcelaCodigo = parcelaCodigo;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}

	public Boolean getUsaData() {
		return usaData;
	}

	public void setUsaData(Boolean usaData) {
		this.usaData = usaData;
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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Integer getAux() {
		return aux;
	}

	public void setAux(Integer aux) {
		this.aux = aux;
	}

	public Integer getUsuarioCodigo() {
		return usuarioCodigo;
	}

	public void setUsuarioCodigo(Integer usuarioCodigo) {
		this.usuarioCodigo = usuarioCodigo;
	}

	public String getOrigemTela() {
		return origemTela;
	}

	public void setOrigemTela(String origemTela) {
		this.origemTela = origemTela;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(String tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}

	public String getDescricaoCartao() {
		return descricaoCartao;
	}

	public void setDescricaoCartao(String descricaoCartao) {
		this.descricaoCartao = descricaoCartao;
	}

	public Date getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}

	public Cartao getCartaoAtual() {
		return cartaoAtual;
	}

	public void setCartaoAtual(Cartao cartaoAtual) {
		this.cartaoAtual = cartaoAtual;
	}

	public String getSuperGrupo() {
		return superGrupo;
	}

	public void setSuperGrupo(String superGrupo) {
		this.superGrupo = superGrupo;
	}

	public String getDescricaoConsulta() {
		return descricaoConsulta;
	}

	public void setDescricaoConsulta(String descricaoConsulta) {
		this.descricaoConsulta = descricaoConsulta;
	}

	public String getGrupoConsulta() {
		return grupoConsulta;
	}

	public void setGrupoConsulta(String grupoConsulta) {
		this.grupoConsulta = grupoConsulta;
	}

	public String getSuperGrupoConsulta() {
		return superGrupoConsulta;
	}

	public void setSuperGrupoConsulta(String superGrupoConsulta) {
		this.superGrupoConsulta = superGrupoConsulta;
	}

	public Boolean getContabilizado() {
		return contabilizado;
	}

	public void setContabilizado(Boolean contabilizado) {
		this.contabilizado = contabilizado;
	}

	public String getCartaoConsulta() {
		return cartaoConsulta;
	}

	public void setCartaoConsulta(String cartaoConsulta) {
		this.cartaoConsulta = cartaoConsulta;
	}

	public Boolean getTodosCartoes() {
		return todosCartoes;
	}

	public void setTodosCartoes(Boolean todosCartoes) {
		this.todosCartoes = todosCartoes;
	}
}
