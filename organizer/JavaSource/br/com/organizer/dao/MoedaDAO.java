package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.Moeda;



public interface MoedaDAO extends GenericoDAO<Moeda>{
	
	public Collection<Moeda> listarMoedas();

	public Moeda carregaMoedaPorCodigo(Integer moedaCodigo);
	
	public Moeda carregarMoedaPorSigla(String sigla);
}
