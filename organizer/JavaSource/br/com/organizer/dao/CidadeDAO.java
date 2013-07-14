package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.Cidade;

public interface CidadeDAO extends GenericoDAO<Cidade> {
	
	public Collection<Cidade> carregarCidadesPorEstado(Integer codigoEstado);
	
	public Cidade carregaCidadePorCodigo(Integer cidadeCod);
}
