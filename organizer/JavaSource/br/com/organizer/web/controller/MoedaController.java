package br.com.organizer.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.organizer.business.MoedaBO;
import br.com.organizer.model.Moeda;
import br.com.organizer.model.Usuario;
import br.com.organizer.model.dto.ParametrosDTO;
import br.com.organizer.util.ServiceFinder;

@Component("moedaController")
@Scope("request")
public class MoedaController implements Serializable{

	private static final long serialVersionUID = 1L;

	MoedaBO moedaBO = (MoedaBO) ServiceFinder.findBean("moedaBOTarget");

	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

	private List<Moeda> listaDeMoedas = new ArrayList<Moeda>();
	
	private Integer tamanhoLista;
	
	private ParametrosDTO parametrosDTO;


	public ParametrosDTO getParametrosDTO() {
		return parametrosDTO;
	}

	public void setParametrosDTO(ParametrosDTO parametrosDTO) {
		this.parametrosDTO = parametrosDTO;
	}

	public MoedaController() {
		super();
		this.parametrosDTO = new ParametrosDTO();
		listarMoedas();
	}

	public void salvar() {
		moedaBO.salvar(parametrosDTO);
		listarMoedas();
		this.parametrosDTO = new ParametrosDTO();
	}

	public void cancelar() {
	}

	public void listarMoedas() {
		this.listaDeMoedas = (List<Moeda>) moedaBO.listarMoedas();
	}

	public void excluir() {
		moedaBO.deletar(parametrosDTO);
		listarMoedas();
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

	public List<Moeda> getListaDeMoedas() {
		return listaDeMoedas;
	}

	public void setListaDeMoedas(List<Moeda> listaDeMoedas) {
		this.listaDeMoedas = listaDeMoedas;
	}

}
