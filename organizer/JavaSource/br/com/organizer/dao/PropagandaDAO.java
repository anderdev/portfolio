package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.Propaganda;

public interface PropagandaDAO extends GenericoDAO<Propaganda> {
	
	public Collection<Propaganda> listar();
	
	public Propaganda carregarPorCodigo(Integer codigo);
	
}
