package br.com.organizer.business;

import javax.servlet.http.HttpServletRequest;

import br.com.organizer.model.RegistroAcesso;
import br.com.organizer.model.Usuario;

public interface RegistroAcessoBO extends GenericoBO<RegistroAcesso>{
	
	public RegistroAcesso salvarECarregar(HttpServletRequest request);
	
	public RegistroAcesso carregarUltimoAcesso(Usuario usuario);
	
	public Long contador();

}
