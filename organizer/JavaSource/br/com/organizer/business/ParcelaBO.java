package br.com.organizer.business;

import java.util.Collection;

import br.com.organizer.model.Debito;
import br.com.organizer.model.Parcela;

public interface ParcelaBO extends GenericoBO<Parcela> {
	
	public Collection<Parcela> listarParcelas(Debito debito);

	public Parcela carregarPorCodigo(Integer codigo);
	
	public Integer salvar(Parcela parcela);
}
