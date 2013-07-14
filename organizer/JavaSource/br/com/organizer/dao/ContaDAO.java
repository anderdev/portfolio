package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.Conta;

public interface ContaDAO extends GenericoDAO<Conta> {
	
	public Collection<Conta> listarContas(String locale);
	
	public Conta carregarContaPorCodigo(Integer codigo);
	
	public Conta carregarContaPorDescricao(String descricao);
}
