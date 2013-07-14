package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.Debito;
import br.com.organizer.model.Parcela;

public interface ParcelaDAO extends GenericoDAO<Parcela> {
	
	public Collection<Parcela> listarParcelas(Debito debito);

	public Parcela carregarPorCodigo(Integer codigo);
	
	public Integer salvar(Parcela parcela);	
}
