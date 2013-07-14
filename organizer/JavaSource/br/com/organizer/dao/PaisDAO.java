package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.Pais;

public interface PaisDAO extends GenericoDAO<Pais> {
	
	public Collection<Pais> carregarPaisesPorLocale(String locale);
	
	public Pais carregarPais(Pais pais);
}
