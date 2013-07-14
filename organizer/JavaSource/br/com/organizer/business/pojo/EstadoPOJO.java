package br.com.organizer.business.pojo;

import java.util.Collection;

import br.com.organizer.business.EstadoBO;
import br.com.organizer.dao.EstadoDAO;
import br.com.organizer.model.Estado;

public class EstadoPOJO extends GenericoPOJO<Estado> implements EstadoBO {
	
	public EstadoPOJO() {
		super();
	}
	
	EstadoDAO estadoDAO;
	
	public void setEstadoDAO(EstadoDAO estadoDAO) {
		this.estadoDAO = estadoDAO;
	}

	public Collection<Estado> carregarEstadosPorPais(Integer codigoPais) {
		return estadoDAO.carregarEstadosPorPais(codigoPais);
	}

	

}
