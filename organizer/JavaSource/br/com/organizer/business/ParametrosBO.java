package br.com.organizer.business;

import java.util.Collection;

import br.com.organizer.model.Moeda;
import br.com.organizer.model.Parametros;
import br.com.organizer.model.TipoFechamento;
import br.com.organizer.model.dto.ParametrosDTO;



public interface ParametrosBO extends GenericoBO<Parametros>{
	
	public Collection<Moeda> listarMoedas();
	
	public Collection<TipoFechamento> listarTiposFechamento();
	
	public Collection<TipoFechamento> listarTiposFechamentoPorIdioma(String idioma);

	public void salvar(ParametrosDTO parametrosDTO);

	public void deletar(ParametrosDTO parametrosDTO);

	public Parametros carregaParametroPorCodigo(Integer codigoParametro);
}
