package br.com.organizer.business.pojo;

import java.util.Collection;

import br.com.organizer.business.CidadeBO;
import br.com.organizer.dao.CidadeDAO;
import br.com.organizer.model.Cidade;

public class CidadePOJO extends GenericoPOJO<Cidade> implements CidadeBO {
	
	public CidadePOJO() {
		super();
	}
	
	CidadeDAO cidadeDAO;
	
	public void setCidadeDAO(CidadeDAO cidadeDAO) {
		this.cidadeDAO = cidadeDAO;
	}

	public Collection<Cidade> carregarCidadesPorEstado(Integer codigoEstado) {
		return cidadeDAO.carregarCidadesPorEstado(codigoEstado);
	}

	

}
