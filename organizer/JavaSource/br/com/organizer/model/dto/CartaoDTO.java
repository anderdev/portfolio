package br.com.organizer.model.dto;

import br.com.organizer.model.Usuario;

public class CartaoDTO {

	private Integer codigo;

	private String descricao;

	private Integer diaVencimento;

	private Integer diaCompra;

	private String dtExpira;

	private Usuario usuario;

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n Codigo: " + getCodigo());
		builder.append("\n Descrição: " + getDescricao());
		builder.append("\n DiaVencimento: " + getDiaVencimento());
		builder.append("\n DiaCompra: " + getDiaCompra());
		builder.append("\n DtExpira: " + getDtExpira());
		builder.append("\n Usuario: " + getUsuario());
		return builder.toString();
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

	public String getDtExpira() {
		return dtExpira;
	}

	public void setDtExpira(String dtExpira) {
		this.dtExpira = dtExpira;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
