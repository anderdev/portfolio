package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.TipoFechamento;



public interface TipoFechamentoDAO extends GenericoDAO<TipoFechamento>{
	
	public Collection<TipoFechamento> listarTiposFechamento();

	public TipoFechamento carregaTipoFechamentoPorCodigo(Integer tipoFechamentoCodigo);
	
	public Collection<TipoFechamento> listarTiposFechamentoPorIdioma(String idioma);
	
	public TipoFechamento carregarTipoFechamentoPorTipo(String tipo);
}
