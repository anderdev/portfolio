package br.com.organizer.business;

import java.util.Collection;

import br.com.organizer.model.TipoFechamento;
import br.com.organizer.model.dto.ParametrosDTO;



public interface TipoFechamentoBO extends GenericoBO<TipoFechamento> {
	
	public Collection<TipoFechamento> listarTiposFechamento();

	public void salvar(ParametrosDTO parametrosDTO);

	public void deletar(ParametrosDTO parametrosDTO);

	public ParametrosDTO carregarTipoFechamentoPorCodigo(Integer tipoFechamentoCodigo);
	
	public Collection<TipoFechamento> listarTiposFechamentoPorIdioma(String idioma);
	
	public TipoFechamento carregarTipoFechamentoPorTipo(String tipo);
}
