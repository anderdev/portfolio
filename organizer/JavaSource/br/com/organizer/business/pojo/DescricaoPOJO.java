package br.com.organizer.business.pojo;

import java.util.Collection;

import br.com.organizer.business.DescricaoBO;
import br.com.organizer.dao.ContaDAO;
import br.com.organizer.dao.DescricaoDAO;
import br.com.organizer.dao.UsuarioDAO;
import br.com.organizer.model.Conta;
import br.com.organizer.model.Descricao;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.DescricaoDTO;

public class DescricaoPOJO extends GenericoPOJO<Descricao>implements DescricaoBO {

	DescricaoDAO descricaoDAO;
	
	UsuarioDAO usuarioDAO;
	
	ContaDAO contaDAO;
	
	public DescricaoPOJO() {
		super();
	}
	
	public void setDescricaoDAO(DescricaoDAO descricaoDAO) {
		this.descricaoDAO = descricaoDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
	
	public void setContaDAO(ContaDAO contaDAO) {
		this.contaDAO = contaDAO;
	}

	public DescricaoDTO carregarPorCodigo(
			Integer descricaoCodigo) {
		DescricaoDTO descricaoDTO = new DescricaoDTO();
		
		Descricao descricao = descricaoDAO.carregaDescricaoPorCodigo(descricaoCodigo);
		
		descricaoDTO.setCodigo(descricao.getCodigo());
		
		descricaoDTO.setDescricaoTipoConta(descricao.getTipoConta().getDescricao());
		
		descricaoDTO.setDescricao(descricao.getDescricao());	
		
		return descricaoDTO;
	}

	public void deletar(DescricaoDTO descricaoDTO) {
		Descricao descricao = descricaoDAO.carregaDescricaoPorCodigo(descricaoDTO.getCodigo());
		descricaoDAO.excluir(descricao);
	}

	public Collection<Descricao> listarDescricao(Usuario usuario) {
		return descricaoDAO.listarDescricao(usuario);
	}

	public void salvar(DescricaoDTO descricaoDTO){
		Descricao descricao = new Descricao();
		
		if (descricaoDTO.getCodigo()!=null){
			descricao.setCodigo(descricaoDTO.getCodigo());
		}
		
		descricao.setDescricao(descricaoDTO.getDescricao());
		
		descricao.setUsuario(descricaoDTO.getUsuario());
		
		descricao.setTipoConta(contaDAO.carregarContaPorDescricao(descricaoDTO.getDescricaoTipoConta()));
		
		descricaoDAO.salvar(descricao);
	}

	public Collection<Descricao> listarDescricaoPorUsuarioEConta(Usuario usuario, String tipoConta) {
		Conta conta = contaDAO.carregarContaPorDescricao(tipoConta); 
		return descricaoDAO.listarDescricaoPorUsuarioEConta(usuario,conta);
	}

}
