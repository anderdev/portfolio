package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.Parametros;
import br.com.organizer.model.Usuario;



public interface ParametrosDAO extends GenericoDAO<Parametros>{
	
	public Collection<Parametros> listarParametros(Usuario usuario);

	public Parametros carregaParametroPorCodigo(Integer codigoParametro);
}
