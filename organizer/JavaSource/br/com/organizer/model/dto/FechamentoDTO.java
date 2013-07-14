package br.com.organizer.model.dto;

import java.util.Collection;
import java.util.Date;

import br.com.organizer.model.Usuario;

public class FechamentoDTO {
	
	private Integer codigo;

	private Date data;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private String tipo;

	private String moeda;
	
	private String origemTela;

	private Double totalCredito;
	
	private Double totalDebito;
	
	private Double totalGeral;
	
	private String tipoConsulta;
	
	private Usuario usuario;
	
	private Collection<String> datasCreditosJaContabilizados;
	
	private Collection<String> datasDebitosJaContabilizados;
	
	public FechamentoDTO() {
		super();
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMoeda() {
		return moeda;
	}

	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}

	public Double getTotalCredito() {
		return totalCredito;
	}

	public void setTotalCredito(Double totalCredito) {
		this.totalCredito = totalCredito;
	}

	public Double getTotalDebito() {
		return totalDebito;
	}

	public void setTotalDebito(Double totalDebito) {
		this.totalDebito = totalDebito;
	}

	public Double getTotalGeral() {
		return totalGeral;
	}

	public void setTotalGeral(Double totalGeral) {
		this.totalGeral = totalGeral;
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

	public Collection<String> getDatasCreditosJaContabilizados() {
		return datasCreditosJaContabilizados;
	}

	public void setDatasCreditosJaContabilizados(
			Collection<String> datasCreditosJaContabilizados) {
		this.datasCreditosJaContabilizados = datasCreditosJaContabilizados;
	}

	public Collection<String> getDatasDebitosJaContabilizados() {
		return datasDebitosJaContabilizados;
	}

	public void setDatasDebitosJaContabilizados(
			Collection<String> datasDebitosJaContabilizados) {
		this.datasDebitosJaContabilizados = datasDebitosJaContabilizados;
	}

	public String getOrigemTela() {
		return origemTela;
	}

	public void setOrigemTela(String origemTela) {
		this.origemTela = origemTela;
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
}
