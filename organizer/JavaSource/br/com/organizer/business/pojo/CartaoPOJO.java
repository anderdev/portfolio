package br.com.organizer.business.pojo;

import java.util.Collection;

import br.com.organizer.business.CartaoBO;
import br.com.organizer.dao.CartaoDAO;
import br.com.organizer.dao.UsuarioDAO;
import br.com.organizer.model.Cartao;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.CartaoDTO;
import br.com.organizer.util.Utils;

public class CartaoPOJO extends GenericoPOJO<Cartao> implements CartaoBO {
	
	public CartaoPOJO() {
		super();
	}
	
	CartaoDAO cartaoDAO;
	
	UsuarioDAO usuarioDAO;
	
	public void setCartaoDAO(CartaoDAO cartaoDAO) {
		this.cartaoDAO = cartaoDAO;
	}
	
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public CartaoDTO carregarPorCodigo(Integer codigo) {
		CartaoDTO cartaoDTO = new CartaoDTO();
		
		Cartao cartao = cartaoDAO.carregarPorCodigo(codigo);
		
		cartaoDTO.setCodigo(cartao.getCodigo());
		cartaoDTO.setDescricao(cartao.getDescricao());
		cartaoDTO.setDiaCompra(cartao.getDiaCompra());
		cartaoDTO.setDiaVencimento(cartao.getDiaVencimento());
		cartaoDTO.setDtExpira(Utils.dataToStringCartao(cartao.getDtExpira()));
		cartaoDTO.setUsuario(cartao.getUsuario());
		return cartaoDTO;
	}

	public void deletar(CartaoDTO cartaoDTO) {
		Cartao cartao = new Cartao();
		cartao = cartaoDAO.carregarPorCodigo(cartaoDTO.getCodigo());
		cartaoDAO.excluir(cartao);
	}

	public Collection<Cartao> listarCartoes(Usuario usuario) {
		return cartaoDAO.listarCartao(usuario);
	}

	public void salvar(CartaoDTO cartaoDTO) {
		Cartao cartao = new Cartao();
		
		if (cartaoDTO.getCodigo()!=null){
			cartao.setCodigo(cartaoDTO.getCodigo());
		}
		
		cartao.setDescricao(cartaoDTO.getDescricao());		
		
		cartao.setUsuario(cartaoDTO.getUsuario());
		
		if(cartaoDTO.getUsuario().getMasterCodigo()!=null){
			cartao.setMasterUsuario(usuarioDAO.carregarPorCodigo(cartaoDTO.getUsuario().getMasterCodigo()));
		}else{
			cartao.setMasterUsuario(usuarioDAO.carregarPorCodigo(cartaoDTO.getUsuario().getCodigo()));
		}
		
		cartao.setDiaCompra(cartaoDTO.getDiaCompra());
		
		cartao.setDiaVencimento(cartaoDTO.getDiaVencimento());
		cartao.setDtExpira(Utils.stringToData("01/"+cartaoDTO.getDtExpira(),true));
		
		cartaoDAO.salvar(cartao);		
	}
}
