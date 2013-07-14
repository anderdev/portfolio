package br.com.organizer.business.pojo;

import java.util.Collection;

import br.com.organizer.business.PaisBO;
import br.com.organizer.dao.PaisDAO;
import br.com.organizer.model.Pais;

public class PaisPOJO extends GenericoPOJO<Pais> implements PaisBO {
	
	public PaisPOJO() {
		super();
	}
	
	PaisDAO paisDAO;

	public void setPaisDAO(PaisDAO paisDAO) {
		this.paisDAO = paisDAO;
	}

	public Collection<Pais> carregarPaisesPorLocale(String locale) {
		return paisDAO.carregarPaisesPorLocale(locale);
	}

}
