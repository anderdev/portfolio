package br.com.organizer.business;

import java.util.Collection;

import br.com.organizer.model.Estado;

public interface EstadoBO extends GenericoBO<Estado> {
	
	public Collection<Estado> carregarEstadosPorPais(Integer codigoPais);
}
