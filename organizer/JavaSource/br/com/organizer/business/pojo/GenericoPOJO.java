package br.com.organizer.business.pojo;

import java.io.Serializable;

import br.com.organizer.business.GenericoBO;
import br.com.organizer.dao.GenericoDAO;

public class GenericoPOJO<T> implements GenericoBO<T>{
	
	public GenericoPOJO() {
		super();
	}
	
	GenericoDAO<T> genericoDAO;
	
	public void setGenericoDAO(GenericoDAO<T> genericoDAO) {
		this.genericoDAO = genericoDAO;
	}

	public Serializable excluir(Serializable obj) {
		return this.genericoDAO.excluir(obj);
	}

	public void salvar(Serializable obj) {		
		genericoDAO.salvar(obj);
	}

}
