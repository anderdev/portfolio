package br.com.organizer.model.dto;

import br.com.organizer.model.Conta;
import br.com.organizer.model.Usuario;

public class DescricaoDTO {
	
	private Integer codigo;
	
	private String descricao;
	
	private Integer usuarioCodigo;
	
	private Integer codigoTipoConta;
	
	private String descricaoTipoConta;
	
	private Conta tipoConta;
	
	private String aux;
	
	private Usuario usuario;
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n Codigo: "+ getCodigo());
		builder.append("\n Descricao: "+ getDescricao());
		builder.append("\n CodigoTipoConta: "+ getCodigoTipoConta());
		builder.append("\n DescricaoTipoConta: "+ getDescricaoTipoConta());
		builder.append("\n Usuario: "+ getUsuario());
		return builder.toString();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	public Integer getUsuarioCodigo() {
		return usuarioCodigo;
	}

	public void setUsuarioCodigo(Integer usuarioCodigo) {
		this.usuarioCodigo = usuarioCodigo;
	}

	public String getAux() {
		return aux;
	}

	public void setAux(String aux) {
		this.aux = aux;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Conta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(Conta tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Integer getCodigoTipoConta() {
		return codigoTipoConta;
	}

	public void setCodigoTipoConta(Integer codigoTipoConta) {
		this.codigoTipoConta = codigoTipoConta;
	}

	public String getDescricaoTipoConta() {
		return descricaoTipoConta;
	}

	public void setDescricaoTipoConta(String descricaoTipoConta) {
		this.descricaoTipoConta = descricaoTipoConta;
	}


}
