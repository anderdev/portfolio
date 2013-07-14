package br.com.organizer.business;

import java.util.Collection;

import br.com.organizer.model.Cidade;

public interface CidadeBO extends GenericoBO<Cidade> {
	
	public Collection<Cidade> carregarCidadesPorEstado(Integer codigoEstado);
}
