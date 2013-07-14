package br.com.organizer.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.organizer.business.TipoFechamentoBO;
import br.com.organizer.model.TipoFechamento;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.ParametrosDTO;
import br.com.organizer.util.ServiceFinder;

@Component("tipoFechamentoController")
@Scope("request")
public class TipoFechamentoController implements Serializable{

	private static final long serialVersionUID = 1L;

	TipoFechamentoBO tipoFechamentoBO = (TipoFechamentoBO) ServiceFinder.findBean("tipoFechamentoBOTarget");

	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

	private List<TipoFechamento> listaDeTipoFechamento = new ArrayList<TipoFechamento>();
	
	private Integer tamanhoLista;
	
	private ParametrosDTO parametrosDTO;


	public ParametrosDTO getParametrosDTO() {
		return parametrosDTO;
	}

	public void setParametrosDTO(ParametrosDTO parametrosDTO) {
		this.parametrosDTO = parametrosDTO;
	}

	public TipoFechamentoController() {
		super();
		this.parametrosDTO = new ParametrosDTO();
		listarTipoFechamento();
	}

	public void salvar() {
		tipoFechamentoBO.salvar(parametrosDTO);
		listarTipoFechamento();
		this.parametrosDTO = new ParametrosDTO();
	}
	
	public void novo(){
		this.parametrosDTO = new ParametrosDTO();
	}

	public void cancelar() {
	}

	public void listarTipoFechamento() {
		this.listaDeTipoFechamento = (List<TipoFechamento>) tipoFechamentoBO.listarTiposFechamento();
	}

	public void excluir() {
		tipoFechamentoBO.deletar(parametrosDTO);
		listarTipoFechamento();
		this.parametrosDTO = new ParametrosDTO();
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

	public List<TipoFechamento> getListaDeTipoFechamento() {
		return listaDeTipoFechamento;
	}

	public void setListaDeTipoFechamento(List<TipoFechamento> listaDeTipoFechamento) {
		this.listaDeTipoFechamento = listaDeTipoFechamento;
	}

}
