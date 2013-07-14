package br.com.organizer.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.organizer.business.CartaoBO;
import br.com.organizer.model.Cartao;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.CartaoDTO;
import br.com.organizer.util.ServiceFinder;

@Component("cartaoController")
@Scope("request")
public class CartaoController implements Serializable{

	private static final long serialVersionUID = 1L;

	CartaoBO cartaoBO = (CartaoBO) ServiceFinder.findBean("cartaoBOTarget");

	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

	private List<Cartao> listaDeCartoes = new ArrayList<Cartao>();
	
	private Integer tamanhoLista;
	
	private CartaoDTO cartaoDTO;

	public CartaoDTO getCartaoDTO() {
		return cartaoDTO;
	}

	public void setCartaoDTO(CartaoDTO cartaoDTO) {
		this.cartaoDTO = cartaoDTO;
	}
	
	public CartaoController() {
		this.cartaoDTO = new CartaoDTO();
	}

	public void salvar() {
		cartaoDTO.setUsuario(usuarioLogado);
		cartaoBO.salvar(cartaoDTO);
		cartaoDTO = new CartaoDTO();
		listarCartoes();
	}

	public void cancelar() {
		cartaoDTO = new CartaoDTO();
	}

	public String listarCartoes() {
		this.cartaoDTO = new CartaoDTO();
		this.listaDeCartoes = (List<Cartao>) cartaoBO.listarCartoes(usuarioLogado);
		return "listarCartoes";
	}

	public void excluir() {
		cartaoBO.deletar(cartaoDTO);
		cartaoDTO = new CartaoDTO();
		listarCartoes();
	}
	
	public void editar(){
		cartaoDTO = cartaoBO.carregarPorCodigo(cartaoDTO.getCodigo());
	}
	
	public void novo(){
		cartaoDTO = new CartaoDTO();
	}
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Integer getTamanhoLista() {
		return tamanhoLista;
	}

	public void setTamanhoLista(Integer tamanhoLista) {
		this.tamanhoLista = tamanhoLista;
	}

	public List<Cartao> getListaDeCartoes() {
		return listaDeCartoes;
	}

	public void setListaDeCartoes(List<Cartao> listaDeCartoes) {
		this.listaDeCartoes = listaDeCartoes;
	}

}
