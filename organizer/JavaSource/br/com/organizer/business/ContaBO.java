package br.com.organizer.business;

import java.util.Collection;

import br.com.organizer.model.Conta;

public interface ContaBO extends GenericoBO<Conta> {
	
	public Collection<Conta> listarContas(String locale);
	
	public Conta carregarContaporCodigo(Integer codigo);
	
	public Conta carregarContaPorDescricao(String descricao);
}
