package br.com.organizer.business.pojo;

import java.util.Collection;

import br.com.organizer.business.MoedaBO;
import br.com.organizer.dao.MoedaDAO;
import br.com.organizer.model.Moeda;
import br.com.organizer.model.dto.ParametrosDTO;

public class MoedaPOJO extends GenericoPOJO<Moeda> implements MoedaBO {

	MoedaDAO moedaDAO;

	public void setMoedaDAO(MoedaDAO moedaDAO) {
		this.moedaDAO = moedaDAO;
	}

	public ParametrosDTO carregaMoedaPorCodigo(Integer moedaCodigo) {
		ParametrosDTO parametrosDTO = new ParametrosDTO();
		Moeda moeda = moedaDAO.carregaMoedaPorCodigo(moedaCodigo);
		
		parametrosDTO.setCodigo(moeda.getCodigo());
		parametrosDTO.setMoeda(moeda.getMoeda());
		
		return parametrosDTO;
	}

	public void deletar(ParametrosDTO parametrosDTO) {
		Moeda moeda = moedaDAO.carregaMoedaPorCodigo(parametrosDTO.getCodigo());
		moedaDAO.excluir(moeda);
	}

	public Collection<Moeda> listarMoedas() {
		return moedaDAO.listarMoedas();
	}

	public void salvar(ParametrosDTO parametrosDTO) {
		Moeda moeda = new Moeda();
		
		if (parametrosDTO.getCodigo()!=null){
			moeda.setCodigo(parametrosDTO.getCodigo());
		}
		
		moeda.setMoeda(parametrosDTO.getMoeda());
		moeda.setSigla(parametrosDTO.getSigla());
				
		moedaDAO.salvar(moeda);
		
	}

}
