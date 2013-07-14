package br.com.organizer.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.organizer.business.MoedaBO;
import br.com.organizer.business.ParametrosBO;
import br.com.organizer.business.UsuarioBO;
import br.com.organizer.model.Moeda;
import br.com.organizer.model.Parametros;
import br.com.organizer.model.TipoFechamento;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.ParametrosDTO;
import br.com.organizer.util.ServiceFinder;

@Component("parametrosController")
@Scope("request")
public class ParametrosController implements Serializable {
	
	private static final long serialVersionUID = 1L;

	ParametrosBO parametrosBO = (ParametrosBO) ServiceFinder.findBean("parametrosBOTarget");
	
	MoedaBO moedaBO = (MoedaBO) ServiceFinder.findBean("moedaBOTarget");
	
	UsuarioBO usuarioBO = (UsuarioBO) ServiceFinder.findBean("usuarioBOTarget");

	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
	
	Usuario usuarioMaster = new Usuario();

	private ParametrosDTO parametrosDTO;
	
	private List<Moeda> listaDeMoedas = new ArrayList<Moeda>();
	
	private List<TipoFechamento> listaDeTiposFechamento = new ArrayList<TipoFechamento>();

	public ParametrosController() {
		this.parametrosDTO = new ParametrosDTO();
		carregarMasterUsuario(usuarioLogado);
		this.listaDeTiposFechamento = (List<TipoFechamento>) parametrosBO.listarTiposFechamentoPorIdioma(usuarioMaster.getIdioma());
		this.listaDeMoedas = (List<Moeda>) moedaBO.listarMoedas();
		
	}
	
	private void carregarMasterUsuario(Usuario usuario){
		if(usuario.getMasterCodigo()!=null){
			this.usuarioMaster = usuarioBO.carregarPorCodigo(usuario, false);
		}else{
			this.usuarioMaster = usuarioBO.carregarPorCodigo(usuario, true);
		}
	}
	
	public String carregarParametros(){
		Parametros parametros = parametrosBO.carregaParametroPorCodigo(usuarioMaster.getParametro().getCodigo());
		this.parametrosDTO.setCodigo(parametros.getCodigo());
		this.parametrosDTO.setDescricaoMoeda(parametros.getMoeda().getSigla());
		this.parametrosDTO.setDescricaoTipoFechamento(parametros.getTipoFechamento().getTipoFechamento());
		return "manterConfiguracaoParametros";
	}

	public String salvar() {
		parametrosDTO.setUsuario(usuarioMaster);
		parametrosBO.salvar(parametrosDTO);
		return "logado";
	}
	
	public String cancelar() {
		this.parametrosDTO = new ParametrosDTO();
		return "logado";
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<Moeda> getListaDeMoedas() {
		return listaDeMoedas;
	}

	public void setListaDeMoedas(List<Moeda> listaDeMoedas) {
		this.listaDeMoedas = listaDeMoedas;
	}

	public List<TipoFechamento> getListaDeTiposFechamento() {
		return listaDeTiposFechamento;
	}

	public void setListaDeTiposFechamento(
			List<TipoFechamento> listaDeTiposFechamento) {
		this.listaDeTiposFechamento = listaDeTiposFechamento;
	}
	
	public ParametrosDTO getParametrosDTO() {
		return parametrosDTO;
	}

	public void setParametrosDTO(ParametrosDTO parametrosDTO) {
		this.parametrosDTO = parametrosDTO;
	}

}
