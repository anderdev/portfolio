package br.com.organizer.business.pojo;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import br.com.organizer.business.RegistroAcessoBO;
import br.com.organizer.dao.RegistroAcessoDAO;
import br.com.organizer.model.RegistroAcesso;
import br.com.organizer.model.Usuario;

public class RegistroAcessoPOJO extends GenericoPOJO<RegistroAcesso> implements RegistroAcessoBO {
	
	RegistroAcessoDAO registroAcessoDAO;
	
	public void setRegistroAcessoDAO(RegistroAcessoDAO registroAcessoDAO) {
		this.registroAcessoDAO = registroAcessoDAO;
	}

	public RegistroAcesso carregarUltimoAcesso(Usuario usuario) {
		return registroAcessoDAO.carregarUltimoAcesso(usuario);
	}

	public Long contador() {
		Long contador = 0L;
		
		Collection<RegistroAcesso> listaRegistros = registroAcessoDAO.listarRegistros();
		
		for (int x = 0; x< listaRegistros.size(); x++) {
			contador++;
		}
		
		return contador;
	}

	public RegistroAcesso salvarECarregar(HttpServletRequest request) {
		RegistroAcesso registroAcesso = new RegistroAcesso();
		registroAcesso.setLocale(request.getLocale().toString());
		registroAcesso.setLocalName(request.getRemoteHost());
		registroAcesso.setDataLogin(new Date());
		registroAcesso.setIpUsuario(request.getRemoteAddr());		
		registroAcessoDAO.salvar(registroAcesso);
		
		return registroAcessoDAO.carregarRigistroAtual(registroAcesso.getCodigo());
	}
	

}
