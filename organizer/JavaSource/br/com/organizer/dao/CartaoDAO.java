package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.Cartao;
import br.com.organizer.model.Usuario;

public interface CartaoDAO extends GenericoDAO<Cartao> {
	
	public Collection<Cartao> listarCartao(Usuario usuario);

	public Cartao carregarPorCodigo(Integer codigo);
	
	public Cartao carregarCartaoPorDescricao(String descricao);
}
