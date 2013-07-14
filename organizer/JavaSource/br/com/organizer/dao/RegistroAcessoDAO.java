package br.com.organizer.dao;

import java.util.Collection;

import br.com.organizer.model.RegistroAcesso;
import br.com.organizer.model.Usuario;

public interface RegistroAcessoDAO extends GenericoDAO<RegistroAcesso> {
	
	public Collection<RegistroAcesso> listarRegistros();
	
	public RegistroAcesso carregarUltimoAcesso(Usuario usuario);
	
	public RegistroAcesso carregarRigistroAtual(Integer codigo);
}
