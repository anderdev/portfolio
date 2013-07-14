package br.com.organizer.business;

import java.util.Collection;

import br.com.organizer.model.Pais;

public interface PaisBO extends GenericoBO<Pais> {
	
	public Collection<Pais> carregarPaisesPorLocale(String locale);
}
