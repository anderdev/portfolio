package br.com.organizer.business;

import java.util.Collection;

import br.com.organizer.model.Descricao;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.DescricaoDTO;



public interface DescricaoBO extends GenericoBO<Descricao>{
	
	public Collection<Descricao> listarDescricao(Usuario usuario);
	
	public Collection<Descricao> listarDescricaoPorUsuarioEConta(Usuario usuario, String tipoCOnta);

	public void salvar(DescricaoDTO descricaoDTO);

	public void deletar(DescricaoDTO descricaoDTO);

	public DescricaoDTO carregarPorCodigo(Integer descricaoCodigo);
}
