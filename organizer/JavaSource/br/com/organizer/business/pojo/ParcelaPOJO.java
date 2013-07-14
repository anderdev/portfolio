package br.com.organizer.business.pojo;

import java.util.Collection;

import br.com.organizer.business.ParcelaBO;
import br.com.organizer.dao.ParcelaDAO;
import br.com.organizer.model.Debito;
import br.com.organizer.model.Parcela;

public class ParcelaPOJO extends GenericoPOJO<Parcela> implements ParcelaBO {
	
	public ParcelaPOJO() {
		super();
	}
	
	ParcelaDAO parcelaDAO;
	
	public void setParcelaDAO(ParcelaDAO parcelaDAO) {
		this.parcelaDAO = parcelaDAO;
	}
	
	public Parcela carregarPorCodigo(Integer codigo) {
		return parcelaDAO.carregarPorCodigo(codigo);
	}

	

	public Collection<Parcela> listarParcelas(Debito debito) {
		return parcelaDAO.listarParcelas(debito);
	}

	public Integer salvar(Parcela parcela) {				
		return parcelaDAO.salvar(parcela);		
	}
}
