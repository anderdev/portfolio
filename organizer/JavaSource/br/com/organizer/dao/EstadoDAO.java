package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.Estado;

public interface EstadoDAO extends GenericoDAO<Estado> {
	
	public Collection<Estado> carregarEstadosPorPais(Integer codigoPais);
	
	public Estado carregarEstado(Estado estado);
}
