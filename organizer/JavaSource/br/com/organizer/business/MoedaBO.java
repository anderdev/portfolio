package br.com.organizer.business;

import java.util.Collection;

import br.com.organizer.model.Moeda;
import br.com.organizer.model.dto.ParametrosDTO;



public interface MoedaBO extends GenericoBO<Moeda>{
	
	public Collection<Moeda> listarMoedas();

	public void deletar(ParametrosDTO parametrosDTO);
	
	public ParametrosDTO carregaMoedaPorCodigo(Integer moedaCodigo);
	
	public void salvar(ParametrosDTO parametrosDTO);
}
