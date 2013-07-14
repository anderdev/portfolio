package br.com.organizer.business;

import java.util.Collection;

import br.com.organizer.model.Cartao;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.CartaoDTO;

public interface CartaoBO extends GenericoBO<Cartao> {
	
	public Collection<Cartao> listarCartoes(Usuario usuario);

	public void salvar(CartaoDTO cartaoDTO);

	public void deletar(CartaoDTO cartaoDTO);

	public CartaoDTO carregarPorCodigo(Integer codigo);
}
