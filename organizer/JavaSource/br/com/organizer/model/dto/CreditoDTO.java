package br.com.organizer.model.dto;



import java.util.Date;

import br.com.organizer.model.Usuario;

public class CreditoDTO {
	
	private Integer codigo;

	private Date data;

	private String descricao;
	
	private String grupo;
	
	private String superGrupo;
	
	private String descricaoConsulta;
	
	private String grupoConsulta;
	
	private String superGrupoConsulta;

	private String moeda;

	private String valor;

	private Integer aux;
	
	private Integer usuarioCodigo;
	
	private String origemLista;
	
	private String origemTela;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private String tipoConsulta;
	
	private Usuario usuario;
	
	private Boolean usaData;
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n Codigo: " + this.getCodigo());
		builder.append("\n Data: " + this.getData());
		builder.append("\n Descrição: " + this.getDescricao());
		builder.append("\n Grupo: " + this.getGrupo());
		builder.append("\n SuperGrupo: " + this.getSuperGrupo());
		builder.append("\n Moeda: " + this.getMoeda());
		builder.append("\n UsuarioCodigo: " + this.getUsuarioCodigo());
		builder.append("\n OrigemLista: " + this.getOrigemLista());
		builder.append("\n OrigemTela: " + this.getOrigemTela());
		builder.append("\n DataInicial: " + this.getDataInicial());
		builder.append("\n DataFinal: " + this.getDataFinal());
		builder.append("\n TipoConsulta: " + this.getTipoConsulta());
		builder.append("\n Usuario: " + this.getUsuario());
		builder.append("\n UsaData: " + this.getUsaData());
		return builder.toString();
	}

	public Boolean getUsaData() {
		return usaData;
	}

	public void setUsaData(Boolean usaData) {
		this.usaData = usaData;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataIncial) {
		this.dataInicial = dataIncial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getOrigemTela() {
		return origemTela;
	}

	public void setOrigemTela(String origemTela) {
		this.origemTela = origemTela;
	}

	public String getOrigemLista() {
		return origemLista;
	}

	public void setOrigemLista(String origemLista) {
		this.origemLista = origemLista;
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
}
