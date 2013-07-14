package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.Conta;
import br.com.organizer.model.Descricao;
import br.com.organizer.model.Usuario;



public interface DescricaoDAO extends GenericoDAO<Descricao>{
	
	public Collection<Descricao> listarDescricao(Usuario usuario);

	public Collection<Descricao> listarDescricaoPorUsuarioEConta(Usuario usuario, Conta conta);
	
	public Descricao carregaDescricaoPorCodigo(Integer descricaoCodigo);
}
