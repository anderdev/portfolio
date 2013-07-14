package br.com.organizer.business;

import java.util.Collection;

import br.com.organizer.model.Propaganda;
import br.com.organizer.model.dto.PropagandaDTO;

public interface PropagandaBO extends GenericoBO<Propaganda> {
	
	public Collection<PropagandaDTO> listar();
	
	public PropagandaDTO carregarPorCodigo(Integer codigo);
	
	public void salvar(PropagandaDTO propagandaDTO);
	
	public void excluir(PropagandaDTO propagandaDTO);
}
