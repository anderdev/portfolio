package br.com.organizer.business.pojo;

import java.util.Collection;

import br.com.organizer.business.ContaBO;
import br.com.organizer.dao.ContaDAO;
import br.com.organizer.model.Conta;

public class ContaPOJO extends GenericoPOJO<Conta> implements ContaBO {
	
	public ContaPOJO() {
		super();
	}
	
	private ContaDAO contaDAO;
	
	public void setContaDAO(ContaDAO contaDAO) {
		this.contaDAO = contaDAO;
	}

	public Collection<Conta> listarContas(String locale) {
		return contaDAO.listarContas(locale);
	}

	public Conta carregarContaporCodigo(Integer codigo) {
		return contaDAO.carregarContaPorCodigo(codigo);
	}

	@Override
	public Conta carregarContaPorDescricao(String descricao) {
		return contaDAO.carregarContaPorDescricao(descricao);
	}	

}
